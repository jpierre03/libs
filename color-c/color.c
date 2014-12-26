/*
 * Jean-Pierre Prunaret
 * 2012-06-18
 *
 * License : GNU GPL 3
 * Display color in linux console
 */

#include "color.h"

//#define TEST_OK  BLUE "[" GREEN  "ok" BLUE "] " DEFAULT_COLOR

void color_test(void){
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

void color_boolean_test(void){
	int i=0;
	for(i=1;i<=10;i++){
		char string[50];
		sprintf(string, "%d",i);
		if(i%2){
			OK(string);
		}else{
			NOK(string);
		}
	}
}

/*
 * Crée un processus fils exécutant un nouveau programme. PROGRAM est le nom
 * du programme à exécuter ; le programme est recherché dans le path.
 * ARG_LIST est une liste terminée par NULL de chaînes de caractères à passer
 * au programme comme liste d'arguments. Renvoie l'identifiant du processus
 * nouvellement créé.
 */
int spawn (char* program, char** arg_list){
	int result = 0;
	pid_t child_pid;
	/* Duplique ce processus. */
	child_pid = fork ();
	if (child_pid != 0)
		/* Nous sommes dans le processus parent. */
		return child_pid;
	else {
		/* Exécute PROGRAM en le recherchant dans le path. */
		execvp (program, arg_list);
		result = -1;
		/* On ne sort de la fonction execvp uniquement si une erreur survient. */
		fprintf (stderr, "une erreur est survenue au sein de execvp\n");
		abort ();
		NOK("une erreur est survenur au sein de execvp");
	}
	return result;
}

int synchro(char* arg_list[]){
	int child_status;

	/*
	 * Crée un nouveau processus fils exécutant la commande passée en arguments.
	 * Ignore l'identifiant du processus fils renvoyé.
	 */
	spawn (arg_list[0], arg_list);

	wait (&child_status);

	char str[250];
	if (WIFEXITED (child_status)==0){
		sprintf (str,"processus fils (%s) terminé normalement, le code de sortie %d",
				arg_list[0],
				WEXITSTATUS (child_status));
		OK(str);
	}else{
		sprintf (str,"processus fils (%s) terminé anormalement, le code de sortie %d\n",
				arg_list[0],
				WEXITSTATUS (child_status));
		NOK(str);
	}

	return 0;
}

/**
 * Demo sequence. mtr is used.
 * Need to be run with enough rights
 */
void demo(){
	OK("C'est parti");
	NOK("C'est pas bon");

	color_test();
	color_boolean_test();

	char* arg_list[] = {
		"mtr",     /* argv[0], le nom du programme. */
		"www.free.fr",
		"-c",
		"1",
		"--curses",
		NULL      /* La liste d'arguments doit se terminer par NULL.  */
	};
	synchro(arg_list);

	/*
	char* str1[]={"mtr","www.orange.fr","-c","2","--curses"};
	char* str2[]={"mtr","www.yahoo.com","-c","2","--curses"};
	char* str3[]={"mtr","www.google.com","-c","2","--curses"};

	int i;

	for(i=0; i<10; i++) {
		synchro(str1);
		synchro(str2);
		synchro(str3);
	}
	*/
}

int main(int argc, char **argv){
	if(argc <= 1) {
		demo();
	}

	/**
	 * Colorize according to program arguments.
	 * If available, comments are displayed.
	 */
	if(argc >= 2) {
		int isOk = strcmp("OK", argv[2-1]) == 0 || strcmp("ok", argv[2-1]) == 0;
		int isNok = strcmp("NOK", argv[2-1]) == 0 || strcmp("nok", argv[2-1]) == 0;
		int isComment = argc >= 3;
		char* comment = isComment ? argv[3-1] : "";

		if(isOk){
			OK(comment);

		}
		if(isNok){
			NOK(comment);
		}

	}

	return 0;
}

