all: main

main: main.o list.o
	gcc -o main main.o list.o -lm

main.o: main.c
	gcc -Wall -c main.c

list.o: list.c
	gcc -Wall -c list.c 

clean:
	rm -f *.o main

run: main
	./main
