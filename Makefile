all: circular


circular: circular.c
	$(CC) -Wall circular.c -o circular

main: main.o list.o
	$(CC) -o main main.o list.o -lm

main.o: main.c
	$(CC) -Wall -c main.c

list.o: list.c
	$(CC) -Wall -c list.c 

clean:
	rm -f *.o main

run: main
	./main
