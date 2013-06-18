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
	for(i=1; i<=6; i++) {
		char string[50];
		sprintf(string, "%d",i);
		if(i%2) {
			OK(string);
		} else {
			NOK(string);
		}
	}
}

void color_varargs_test(void) {
	char string[]="a string";
	int zero=0;
	int hexa=15;

	OK("simple ok");
	NOK("simple nok");
	INFO("simple info message");

	OK_("complex ok with args interpretations ", "|%s|%d|%x|\n", string, zero, hexa);
	NOK_("complex nok with args interpretations ", "|%s|%x|%d|\n", string, hexa, zero);
	INFO_("complex info with args interpretations ", "|%d|%x|%s|%s|%d|%x|\n", zero, hexa, string, string, zero, hexa);
}

int main(int argc, char **argv) {
	color_test();
	separator();
	color_boolean_test();
	separator();
	color_varargs_test();
	separator();

	return 0;
}

