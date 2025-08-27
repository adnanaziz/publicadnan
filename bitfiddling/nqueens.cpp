// nqueens_compare.cpp (C++11)
// Build: g++ -O3 -std=c++11 -march=native nqueens_compare.cpp -o nqueens
// Usage: ./nqueens 14    (defaults to 14 if omitted)

#include <bits/stdc++.h>
using namespace std;

// ---------- 1) Plain backtracking (no bit-twiddling) ----------
static uint64_t count_nqueens_plain(int n) {
    if (n <= 0) return 1; // empty board has one "solution"

    // columns, diag1 (r-c), diag2 (r+c) occupancy flags
    // diag1 index shift by (n-1) to be non-negative if you prefer; using 2n-1 sized arrays avoids that.
    vector<char> col(n, 0), d1(2*n-1, 0), d2(2*n-1, 0);

    uint64_t ways = 0;
    function<void(int)> dfs = [&](int r) {
        if (r == n) { ++ways; return; }
        for (int c = 0; c < n; ++c) {
            int id1 = r - c + (n - 1);
            int id2 = r + c;
            if (col[c] || d1[id1] || d2[id2]) continue;
            col[c] = d1[id1] = d2[id2] = 1;
            dfs(r + 1);
            col[c] = d1[id1] = d2[id2] = 0;
        }
    };
    dfs(0);
    return ways;
}

// ---------- 2) Bit-mask backtracking (bit-twiddling) ----------
// Uses up to 64-bit lanes; supports n in [1, 32] safely (wider requires __int128 or a segmented approach).
static uint64_t count_nqueens_bitmask(int n) {
    if (n <= 0) return 1;
    if (n > 32) throw runtime_error("bitmask version limited to n <= 32");

    const uint64_t FULL = (n == 64) ? ~0ULL : ((1ULL << n) - 1ULL);
    uint64_t ways = 0;

    function<void(uint64_t,uint64_t,uint64_t)> dfs =
        [&](uint64_t cols, uint64_t diagL, uint64_t diagR) {
            if (cols == FULL) { ++ways; return; }
            // Free positions for current row:
            uint64_t free = FULL & ~(cols | diagL | diagR);
            while (free) {
                uint64_t p = free & -free;      // lowest-set bit
                free -= p;                       // remove it
                dfs(cols | p,
                    ( (diagL | p) << 1 ) & FULL,
                    ( (diagR | p) >> 1 ));
            }
        };

    dfs(0ULL, 0ULL, 0ULL);
    return ways;
}

// ---------- tiny timing helper ----------
static double sec_now() {
    using clock = chrono::high_resolution_clock;
    return chrono::duration<double>(clock::now().time_since_epoch()).count();
}

int main(int argc, char** argv) {
    int n = (argc >= 2) ? atoi(argv[1]) : 14;

    // Warm-up
    (void)count_nqueens_plain(min(n, 10));
    (void)count_nqueens_bitmask(min(n, 14));

    // Plain
    double t0 = sec_now();
    uint64_t plain = count_nqueens_plain(n);
    double t1 = sec_now();

    // Bitmask
    double t2 = sec_now();
    uint64_t bm = count_nqueens_bitmask(min(n, 32));
    double t3 = sec_now();

    cout << "n = " << n << "\n";
    cout << "plain:   " << plain << " solutions, " << (t1 - t0) << " s\n";
    cout << "bitmask: " << bm    << " solutions, " << (t3 - t2) << " s\n";

    if (plain != bm) {
        cerr << "[note] Counts differ only if n>32 (bitmask limited) or if an error occurred.\n";
    }
    return 0;
}
