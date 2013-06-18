/*
 * Jean-Pierre Prunaret
 * 2012-06-18
 *
 * License : GNU GPL 3
 * Display color in linux console
 */

#include "jp_color.h"


void separator(void) {
	printf("----------\n");
}

void color_test(void) {
	printf("\033[30m test \033[0m hehe \n" );
	printf("\033[31m test \033[0m hehe \n" );
	printf("\033[32m test \033[0m hehe \n" );
	printf("\033[1;33m test \033[0m hehe \n" );
	printf("\033[34m test \033[0m hehe \n" );
	printf("\033[35m test \033[0m hehe \n" );
	printf("\033[36m test \033[0m hehe \n" );
	printf("\033[37m test \033[0m hehe \n" );
	//printf("\033[38m test \033[0m hehe \n" );
	//printf("\033[39m test \033[0m hehe \n" );
	printf("\033[40m test \033[0m hehe \n" );
	printf("\033[41m test \033[0m hehe \n" );
	printf("\033[42m test \033[0m hehe \n" );
	printf("\033[43m test \033[0m hehe \n" );
	printf("\033[44m test \033[0m hehe \n" );
	printf("\033[45m test \033[0m hehe \n" );
	printf("\033[46m test \033[0m hehe \n" );
	printf("\033[47m test \033[0m hehe \n" );
	//printf("\033[48m test \033[0m hehe \n" );
	//printf("\033[49m test \033[0m hehe \n" );
}

void color_boolean_test(void) {
	int i=0;
	for(i=1; i<=10; i++) {
		char string[50];
		sprintf(string, "%d",i);
		if(i%2) {
			OK(string);
		} else {
			NOK(string);
		}
	}
}

int main(int argc, char **argv) {
	color_test();
	color_boolean_test();

	return 0;
}

