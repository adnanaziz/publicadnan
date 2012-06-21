#include <stdio.h>
#include <pthread.h>
#include <stdlib.h>

#define N 100000000
#define L 20

extern void * task(void *);

char *operation;
int read;

long A[N];

void init() {
  long i;
  for ( i = 0 ; i < N; i++ ) {
    A[i] = i;
  }
}

int main(int argc, char **argv) {

  read = 1;
  if ( argc > 1 && !strcmp(argv[1], "write" ) ) {
    read = 0;
    operation = "writing";
  }
  operation = "reading";

  char *message1 = "Thread 1";
  char *message2 = "Thread 2";

  pthread_t thread1, thread2;
  int iret1, iret2;
  iret1 = pthread_create( &thread1, NULL, task, (void*) message1);
  // iret2 = pthread_create( &thread2, NULL, task, (void*) message2);

  /* Wait till threads are complete before main continues. Unless we  */
  /* wait we run the risk of executing an exit which will terminate   */
  /* the process and all threads before the threads have completed.   */
  pthread_join( thread1, NULL);
  // pthread_join( thread2, NULL);
  printf("Thread 1 returns: %d\n",iret1);
  printf("Thread 2 returns: %d\n",iret2);

  exit(0);
}


void * task(void *dummy) {

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
