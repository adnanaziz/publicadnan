/*
  Name: Yoon, Hongseon
  UT EID: hy3444
  Section: 16110
  EE312-Assignment 2
  Purpose: 1.Learn how to input and output numeric values using formatted I/O library functions
           2.Learn how to define and symbolically manipulate numeric data
		   3.Learn how to use basic programming constructs, specifically loops and conditionals
		   4.Sharpen your problem solving skills and general programming skills
 */

#include<stdio.h>

int main ( void )
{
	int number, AusHour, Min, DubHour, Dollar, Cent;
	double Fahrenheit, Celsius, Kg, Pound, Ounce, Miles, Km, EUR;
	const double FREEZING_PT = 32.0;
	const double SCALE_FACTOR = 5.0/9.0;
	const double SCALE_FACTOR1 = 9.0/5.0;
	const double MONEY_FACTOR = 1.2686;
	const double POUND_FACTOR = .45359237;
	const double OUNCE_FACTOR = 16.0;  
	const double MILES_FACTOR = .6213712;
	printf("1. Convert a given Austin time to Dublin time\n");
	printf("2. Convert a given Dublin time to Austin time\n");
	printf("3. Convert a given USD value to EUR\n");
	printf("4. Convert a given EUR value to USD\n");
	printf("5. Convert a given Fahrenheit temperature to Celsius\n");
	printf("6. Convert a given Celsius temperature to Fahrenheit\n");
	printf("7. Convert a given weight in kg to pounds, ounces\n");
	printf("8. Convert a given weight in pounds, ounces to kg\n");
	printf("9. Convert a given distance in km to miles\n");
	printf("10.Convert a given distance in miles to km\n");
	printf("11.Stop doing conversions and quit the program\n");

	printf("Enter a number from the menu (1-11) to select a specific conversion to perform or to quit: ");
	scanf("%d",&number);

	while ( number >= 0 && number != 11 ){
	if(number==1){
		printf("Enter a Austin time to be converted, expressed in hours and minutes: ");
		scanf("%d %d", &AusHour, &Min);
		DubHour = (AusHour + 6) % 24;
		if(AusHour>=0 && AusHour<=17){
		printf("The time in Dublin equivalent to %d %.2d in Austin is %d %.2d the same day\n",AusHour,Min,DubHour,Min);
		}
		else{
		printf("The time in Dublin equivalent to %d %.2d in Austin is %d %.2d the next day\n",AusHour,Min,DubHour,Min);
		}
	}
	else if(number==2){
		printf("Enter a Dublin time to be converted, expressed in hours and minutes: ");
		scanf("%d %d", &DubHour, &Min);
		AusHour = (DubHour + 18) % 24;
		if(DubHour>=0 && DubHour<=5){
		printf("The time in Austin equivalent to %d %.2d in Dublin is %d %.2d the previous day\n",DubHour,Min,AusHour,Min);
		}
		else{
		printf("The time in Austin equivalent to %d %.2d in Dublin is %d %.2d the same day\n",DubHour,Min,AusHour,Min);
		}
	}
	else if(number==3){
		printf("Enter an USD to be converted, expressed in dollars and cents: ");
		scanf("%d %d", &Dollar, &Cent);
		EUR = (Dollar + .01*Cent) / MONEY_FACTOR;
		printf("The bill in EUR equivalent to %d dollars %.2d cents in USD is %.2lf\n",Dollar,Cent,EUR);
	}
	else if(number==4){
		printf("Enter an EUR to be converted: ");
		scanf("%lf",&EUR);
		Dollar = EUR * MONEY_FACTOR;
		Cent = 100 * ((EUR * MONEY_FACTOR) - Dollar);
		printf("The bill in USD equivalent to %.2lf in USD is %d dollars %.2d cents\n", EUR, Dollar, Cent);
	}
	else if(number==5){
		printf("Enter a Fahrenheit temperature to be converted: ");
		scanf("%lf", &Fahrenheit);
		Celsius = SCALE_FACTOR*(Fahrenheit - FREEZING_PT);
		printf("The temperature in Celcius equivalent to %1.0lf in Fahrenheit is %.1lf\n",Fahrenheit,Celsius);
	}
	else if(number==6){
		printf("Enter a Celsius temperature to be converted: ");
		scanf("%lf", &Celsius);
		Fahrenheit = SCALE_FACTOR1*Celsius + FREEZING_PT;
		printf("The temperature in Fahrenheit equivalent to %.1lf in Celsius is %1.0lf\n",Celsius,Fahrenheit);
	}
	else if(number==7){
		printf("Enter a kg weight to be converted: ");
		scanf("%lf", &Kg);
		Pound = Kg / POUND_FACTOR;
		Ounce = (Kg / POUND_FACTOR) * OUNCE_FACTOR;
		printf("The weight in Pound and Ounce equivalent to %.1lf in kg are %1.0lf and %1.0lf\n",Kg,Pound,Ounce);
	}
	else if(number==8){
		printf("Enter a pound weight and ounce to be converted: ");
		scanf("%lf %lf", &Pound, &Ounce);
		Kg = (Pound * POUND_FACTOR) + ((Ounce / OUNCE_FACTOR) * POUND_FACTOR);
		printf("The total weight in kg equivalent to %.1lf %.1lf in pound and ounce are %1.0lf\n",Pound,Ounce,Kg);
	}
	else if(number==9){
		printf("Enter a km distance to be converted: ");
		scanf("%lf", &Km);
		Miles = Km * MILES_FACTOR;
		printf("The distance in miles equivalent to %.1lf in km is %1.0lf\n",Km,Miles);
	}
	else if(number==10){
		printf("Enter a miles distance to be converted: ");
		scanf("%lf", &Miles);
		Km = Miles / MILES_FACTOR;
		printf("The distance in km equivalent to %.1lf in miles is %1.0lf\n",Miles,Km);
	}
	else{
		printf("This is a bad input! Try again\n");
	}
	printf("Enter a number from the menu (1-11) to select a specific conversion to perform or to quit: ");
	scanf("%d",&number);
	}

	if(number==11){
		printf("Good Bye\n");
	}
	else{
		
	}
	return 0;
}
