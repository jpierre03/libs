all: circular client


circular: circular.c
	$(CC) -Wall circular.c -o circular

client: client.c
	$(CC) -Wall client.c -o client

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
