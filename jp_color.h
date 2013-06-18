/*
 * Jean-Pierre Prunaret
 * 2012-06-18
 *
 * License : GNU GPL 3
 * Display color in linux console
 *
 * Windows Console Text colors in C :
 * http://www.mailsend-online.com/blog/setting-windows-console-text-colors-in-c.html
 */

#include<stdio.h>
#include<stdlib.h>
#include<sys/types.h>
#include<unistd.h>

#define SHOW_INFO

#define COLOR_NORMAL	"\033[0m"
#define COLOR_1		"\033[30m"
#define COLOR_2		"\033[31m"
#define COLOR_3		"\033[32m"
#define COLOR_4		"\033[1;33m"
#define COLOR_5		"\033[34m"
#define COLOR_6		"\033[35m"
#define COLOR_7		"\033[36m"
#define COLOR_8		"\033[37m"
#define COLOR_9		"\033[38m"
#define COLOR_10	"\033[39m"
#define COLOR_11	"\033[40m"
#define COLOR_12	"\033[41m"
#define COLOR_13	"\033[42m"
#define COLOR_14	"\033[43m"
#define COLOR_15	"\033[44m"
#define COLOR_16	"\033[45m"
#define COLOR_17	"\033[46m"
#define COLOR_18	"\033[47m"
#define COLOR_19	"\033[48m"
#define COLOR_20	"\033[49m"

//#define BG_LIGHT

#ifndef BG_LIGHT

	#define BLACK    "\033[1;30m"
	#define RED      "\033[1;31m"
	#define GREEN    "\033[1;32m"
	#define YELLOW   "\033[1;33m"
	#define BLUE     "\033[1;34m"
	#define PURPLE   "\033[1;35m"
	#define CYAN     "\033[1;36m"
	#define GREY     "\033[1;37m"

#else // else BG_LIGHT

	#define BLACK    "\033[0;30m"
	#define RED      "\033[0;31m"
	#define GREEN    "\033[0;32m"
	#define YELLOW   "\033[0;33m"
	#define BLUE     "\033[0;34m"
	#define PURPLE   "\033[0;35m"
	#define CYAN     "\033[0;36m"
	#define GREY     "\033[0;37m"

#endif // endif BG_LIGHT

#define DEFAULT_COLOR "\033[0;m"

#define F(x)	     do if (!f(x)) { printf("erreur\n"); exit(1); } while (0)
#define T(x,y)	     do { fprintf(stdout,"%s[%s%s%s]%s\t%s\n",BLUE,GREEN,x,BLUE,DEFAULT_COLOR,y); exit(1); } while (0)
#define OK(y)	     do { fprintf(stdout,"%s[%s%s%s]%s    %s\n",BLUE,GREEN,"ok",BLUE,DEFAULT_COLOR,y);} while (0)
#define OK_(y,...)   do { fprintf(stdout,"%s[%s%s%s]%s    %s",BLUE,GREEN,"ok",BLUE,DEFAULT_COLOR,y); fprintf(stdout, __VA_ARGS__);} while (0)
#define NOK(y)	     do { fprintf(stderr,"%s[%s%s%s]%s   %s\n",BLUE,RED,"nok",BLUE,DEFAULT_COLOR,y); } while (0)
#define NOK_(y,...)  do { fprintf(stderr,"%s[%s%s%s]%s   %s",BLUE,RED,"nok",BLUE,DEFAULT_COLOR,y); fprintf(stderr, __VA_ARGS__);} while (0)

#ifdef SHOW_INFO
#define INFO(y)		do { fprintf(stderr,"%s[%s%s%s]%s   %s\n",BLUE,YELLOW,"nok",BLUE,DEFAULT_COLOR,y); } while (0)
#define INFO_(y, ...)	do { fprintf(stdout,"%s[%s%s%s]%s\t%s"  ,BLUE,YELLOW,"info",BLUE,DEFAULT_COLOR,y);fprintf(stdout, __VA_ARGS__);} while (0)
#else
#define INFO(y)		do { /* Do Nothing */ } while (0)
#define INFO_(y, ...)	do { /* Do Nothing */ } while (0)
#endif
