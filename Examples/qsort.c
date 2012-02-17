#include <stdio.h>
#include <stdlib.h>
#include <limits.h>

#define LARGEARRAYSIZE 1000000
#define VERYLARGEARRAYSIZE 100000000

extern void quicksort( int [], int );
extern void swap( int [], int, int );
extern void print( int [], int );
extern void stressTest0();
extern int cmpInts( int *, int *);
extern int arrayCompare( int [], int [], int );
extern int *arrayDup( int [], int );
extern void randomTest( int, int );
extern void autoCheck( int [], int, char * );


int main() {
  // simple usage
  int A[] = {3,2,1,5,4};
  int N = sizeof(A)/sizeof(A[0]);
  quicksort( A, N );
  print(A, N);

  // stress test on random values with automated checker
  randomTest(LARGEARRAYSIZE, INT_MAX);
  randomTest(VERYLARGEARRAYSIZE, INT_MAX);

  // stress test on array with only two values
  randomTest(LARGEARRAYSIZE, 2);
  randomTest(VERYLARGEARRAYSIZE, 2);

  return 0;
}

void randomTest(int N, int range) {
  int *A = malloc( N * sizeof(int) );
  int i;
  for ( i = 0 ; i < N; i++ ) {
    A[i] = rand() % range;
  }
  autoCheck( A, N, "randomTest" );
}
 

void autoCheck( int A[], int N, char *name ) {
  int *Adup = arrayDup( A, N);
  quicksort(A, N);
  qsort( Adup, N, sizeof(int), cmpInts );
  if ( 0 == arrayCompare( Adup, A, N ) ) {
    printf("%s failed\n", name );
  } else {
    printf("%s passed\n", name );
  }
}

int * 
arrayDup( int A[], int N ) {
  int i;
  int *dup = malloc( N * sizeof( int ) );
  for ( i = 0 ; i < N; i++ ) {
    dup[i] = A[i];
  }
  return dup;
}

int arrayCompare( int *A, int *B, int N ) {
  int i;
  for ( i = 0 ; i < N; i++ ) {
    if ( A[i] != B[i] ) {
      return 0;
    } 
  }
  return 1;
}


int cmpInts( int *x, int *y ) {
  if (  *x < *y ) {
    return -1;
  } else if ( *x > *y ) {
    return 1;
  } else {
    return 0;
  }
}



/* quicksort: sort v[0]...v[n-1] into increasing order */
void quicksort(int v[], int n) {
  int i, last;
  if (n <= 1) /* nothing to do */
  return;
  swap(v, 0, rand() % n) ; /* move pivot elem to v[0] */
  last = 0;
  for (i=1; i<n; i++) { /* partition a */
    if (v[i] < v[0]) { 
      swap(v, ++last, i);
    }
  }
  swap(v, 0, last); /* restore pivot */
  quicksort(v, last); /* recursively sort */
  quicksort(v+last+1, n-last-1); /* each part */
}

/* swap: interchange v [i] and v[j] */
void swap(int v[], int i, int j) {
  int temp;
  temp = v[i];
  v[i] = v[j];
  v[j] = temp;
}

void print( int A[], int N ) {
  int i;
  for ( i = 0; i < N; i++ ) {
    printf("%d%c", A[i], ( i == N - 1 ) ? '\n' : ',' );
  }
}
