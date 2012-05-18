#include <stdio.h>

#define N 100000000
#define L 20

long A[N];

void init() {
  long i;
  for ( i = 0 ; i < N; i++ ) {
    A[i] = i;
  }
}

int main(int argc, char **argv) {
  char *operation = "reading";

  int read = 1;
  if ( argc > 1 && !strcmp(argv[1], "write" ) ) {
    read = 0;
    operation = "writing";
  }

  printf("size of long = %lu\n", sizeof(long) );
  printf("performing %d iterations, %.2e longs in each iter\n"
          "\t=> %.2e bytes\n", L, (double) N, ((double) N) * L * sizeof(long));

  long xor = 0;
  int i, j;
  for ( j = 0 ; j < L; j++ ) {
    for ( i = 0 ; i < N; i++ ) {
      if ( read ) {
        xor ^= A[i];
      } else {
        A[i] = 2*i+1;
      }
    }
    printf("Completed %s iter %d\n", operation, j  );
  }
  if ( read ) {
    printf("xor = %lu\n", xor );
  } else {
    printf("A[42] = %lud\n", A[42]);
  }
}
