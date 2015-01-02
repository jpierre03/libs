all: realkey.bin
	date

realkey.bin: realkey.c
	g++ -ansi -Wall -pedantic -O3 -o realkey.bin realkey.c -L/usr/X11R6/lib -lX11

