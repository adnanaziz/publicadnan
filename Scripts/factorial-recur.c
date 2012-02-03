#include <stdio.h>
int main(int argc, char**argv){
  int i;
  int n = -1;
  int factorial = 1;
  printf("Enter a number\n");
  scanf("%d", & n );
  if ( n >= 0 ) {
    for( i = 1; i <= n; i++ ) {
      factorial *= i;
    }
    printf("The factorial of %d is %d\n", factorial );
  else {
    printf("You entered a negative number: %d\n", n );
  }
  return 0;
}


