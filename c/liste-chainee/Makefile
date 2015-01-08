all: circular.bin client.bin


circular.bin: circular.c jppcircular.h
	$(CC) -Wall -pedantic circular.c -o circular.bin

client.bin: client.c
	$(CC) -Wall client.c -o client.bin

list.o: list.c
	$(CC) -Wall -c list.c 

clean:
	rm -f *.o *.bin

