#include <algorithm>
#include <cassert>
#include <chrono>
#include <cstdint>
#include <cstdio>
#include <cstring>
#include <iostream>
#include <random>
#include <vector>

using word_t = uint64_t;
static const size_t BITS_PER_WORD = 8 * sizeof(word_t);


// ---------------- BitVector ----------------
struct BitVector {

    size_t U, W;
    std::vector<word_t> d;

    explicit BitVector(size_t universe=0) { reset(universe); }

    void reset(size_t universe) {
        U = universe;
        W = (U + BITS_PER_WORD - 1) / BITS_PER_WORD;
        d.assign(W, 0);
    }

    static inline size_t widx(size_t i){ return i / BITS_PER_WORD; }
    static inline size_t boff(size_t i){ return i % BITS_PER_WORD; }
    static inline word_t full() { return ~word_t(0); }
    inline word_t last_mask() const {
        size_t rem = U % BITS_PER_WORD;
        return rem ? ((word_t(1) << rem) - 1) : full();
    }
    inline void mask_tail(){ if (W) d[W-1] &= last_mask(); }

    inline void clear(){ std::fill(d.begin(), d.end(), 0); }
    inline void fill(){ std::fill(d.begin(), d.end(), full()); mask_tail(); }

    inline void add(size_t x){ d[widx(x)] |= (word_t(1) << boff(x)); }
    inline bool contains(size_t x) const {
        return (d[widx(x)] >> boff(x)) & 1u;
    }
    inline void intersection(const BitVector& a, const BitVector& b){
	std::cout << n << std::endl;
        for (size_t i=0;i<n;++i) {
		d[i] = a.d[i] & b.d[i];
	}
    }
};

// --------------- Array helpers ----------------
struct ArraySet {
    std::vector<size_t> v; // unsorted by design
};


static bool arr_contains_unsorted(const ArraySet& A, size_t x){
    for (size_t y : A.v) if (y==x) return true;
    return false;
}

// --------------- Random data generation ----------------
static void make_random_unique(ArraySet& out, size_t k, size_t U, uint32_t seed){
    std::vector<uint8_t> used(U, 0);
    out.v.clear(); out.v.reserve(k);
    uint32_t x = seed ? seed : 1u;
    while (out.v.size() < k){
        x ^= x << 13; x ^= x >> 17; x ^= x << 5;
        size_t val = size_t(x) % U;
        if (!used[val]){ used[val]=1; out.v.push_back(val); }
    }
}

static void build_bitvector(BitVector& S, const ArraySet& A){
    S.clear();
    for (size_t x : A.v) S.add(x);
}

// --------------- Benchmark utils ----------------
using clk = std::chrono::high_resolution_clock;

template <class F>
static uint64_t time_ns(F&& f){
    auto t1 = clk::now();
    f();
    auto t2 = clk::now();
    return std::chrono::duration_cast<std::chrono::nanoseconds>(t2 - t1).count();
}

// Construct Q queries with ~50% hits
static void build_membership_queries(std::vector<size_t>& queries,
                                     const ArraySet& A, size_t U, size_t Q, uint32_t seed){
    queries.clear(); queries.reserve(Q);
    std::vector<uint8_t> used(U, 0);
    for (size_t x : A.v) used[x] = 1;

    uint32_t x = seed ? seed : 1u;
    // half hits sampled from A (wrap if A smaller than Q/2)
    for (size_t i=0;i<Q/2;++i) queries.push_back(A.v[i % A.v.size()]);
    // half misses sampled from outside A
    while (queries.size() < Q){
        x ^= x << 13; x ^= x >> 17; x ^= x << 5;
        size_t val = size_t(x) % U;
        if (!used[val]) queries.push_back(val);
    }
}


static size_t arr_unsorted_intersection_count(const ArraySet& A, const ArraySet& B){
    size_t c=0;
    for (size_t x : A.v){
        for (size_t y : B.v){
            if (x==y){ ++c; break; }
        }
    }
    return c;
}


// ------------------- MAIN -------------------
int main(int argc, char** argv){
    // Defaults (tweak via CLI)
    size_t U = (1u << 20);   // universe = 1,048,576
    int repeats = 5;
    size_t Q = 200000;       // membership queries per trial

    if (argc >= 2) U = std::strtoull(argv[1], nullptr, 10);
    if (argc >= 3) repeats = std::atoi(argv[2]);
    if (argc >= 4) Q = std::strtoull(argv[3], nullptr, 10);

    std::cerr << "Universe=" << U << " repeats=" << repeats << " queries=" << Q << "\n";

    // ks to test (sizes of A and B)
    const size_t ks[] = {100, 200, 500, 1000, 2000, 5000, 10000, 20000, 50000, 100000};
    const size_t numk = sizeof(ks)/sizeof(ks[0]);

    std::cout << "k,"
                 "m_bv_ns,m_arr_ns,"
                 "i_bv_ns,i_arr_uns_ns\n";

    for (size_t idx=0; idx<numk; ++idx){
        size_t k = ks[idx];
        if (k > U) break;

        // Build random sets A and B
        ArraySet A, B;
        make_random_unique(A, k, U, 0xC0FFEEu + (uint32_t)k);
        make_random_unique(B, k, U, 0xBADC0DEu + (uint32_t)k);

        // Bitvectors
        BitVector Abv(U), Bbv(U), Ibv(U);
        build_bitvector(Abv, A);
        build_bitvector(Bbv, B);

        // Membership queries (~50% hits)
        std::vector<size_t> queries;
        build_membership_queries(queries, A, U, Q, 0xDEADBEEFu + (uint32_t)k);

        // --- warmup (avoid cold-start bias) ---
        volatile size_t sink = 0;
        for (size_t i=0;i<1000 && i<queries.size(); ++i){
            sink += Abv.contains(queries[i]);
            sink += arr_contains_unsorted(A, queries[i]);
        }

        // --- Bench membership ---
        uint64_t m_bv_ns = 0, m_arr_ns = 0;
        size_t m_bv_hits=0, m_arr_hits=0;

        for (int r=0;r<repeats;++r){
            m_bv_ns += time_ns([&](){
                size_t cnt=0;
                for (size_t x : queries) cnt += Abv.contains(x);
                m_bv_hits = cnt;
            });
            m_arr_ns += time_ns([&](){
                size_t cnt=0;
                for (size_t x : queries) cnt += arr_contains_unsorted(A, x);
                m_arr_hits = cnt;
            });
        }
        m_bv_ns /= repeats;
        m_arr_ns /= repeats;

        // --- Bench intersection (counts only) ---
        uint64_t i_bv_ns=0, i_arr_uns_ns=0, i_arr_sorted_ns=0;
        size_t i_bv_c=0, i_arrU_c=0, i_arrS_c=0;

        for (int r=0;r<repeats;++r){
            i_bv_ns += time_ns([&](){
                Ibv.intersection(Abv, Bbv);
            });
            i_arr_uns_ns += time_ns([&](){
                i_arrU_c = arr_unsorted_intersection_count(A, B);
            });
        }

        std::cout << k << ","
                  << m_bv_ns << "," << m_arr_ns << ","
                  << i_bv_ns << "," << i_arr_uns_ns << "\n";
    }

    return 0;
}

