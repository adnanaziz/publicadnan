#include <stdio.h>

int main() {
  double length = -2.0;
  printf("Enter a length in feet\n");
  int ret = scanf("%lf", & length );
  printf("Equivalent of %.2f feet in cm is %.2f\n", length, 30.48 * length);
}

/* test function above with testme.expect script shown below:
 *
 * spawn ./scanf
 * expect "Enter a length in feet"
 * send 4
 * expect "Equivalent of 4.00 feet in cm is 121.92"
 *
 */
