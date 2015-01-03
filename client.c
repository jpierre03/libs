// File name – client.c
// Written and tested on Linux Fedora Core 12 VM

#include <netdb.h>
#include <netinet/in.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/socket.h>
#include <sys/types.h>
#include <unistd.h>

#define MAX_SIZE (80)
#define ELA_DATA_SIZE (1+10+1)

/* Opaque buffer element type.  This would be defined by the application. */
typedef struct { char c; } ElemType;

#include "jppcircular.h"

void seek(CircularBuffer *cb, char toFind){
    ElemType *elem = &(cb->elems[cb->start]);

    if(elem->c == toFind){
        // found

    } else if(!cbIsEmpty(cb)) {
        printf("+");
        cbRead(cb, elem);
        seek(cb, toFind);
    }
}

int main(int argc, char** argv) {
    /**
     * Default values
     */
    //const char hostname[]="localhost";
    const char hostname[]="172.16.201.197";
    unsigned int port_number = htons(10001);

	/**
     * Client socket descriptor which is just integer number used to access a socket
     */
	int sock_descriptor;
	struct sockaddr_in serv_addr;

	/**
     * Structure from netdb.h file used for determining host name from local host's ip address
     */
	struct hostent *server;

	/**
     * Buffer to input data from console and write to server
     */
	char buff[MAX_SIZE];
    
    /**
     * Circular buffer data
     */
    CircularBuffer cb;
	ElemType elem = {0};
    
	int testBufferSize = MAX_SIZE + 2 * ELA_DATA_SIZE; /* arbitrary size */
	cbInit(&cb, testBufferSize);

    /**
	 * Create socket of domain - Internet (IP) address, type - Stream based (TCP) and protocol unspecified
	 * since it is only useful when underlying stack allows more than one protocol and we are choosing one.
	 * 0 means choose the default protocol.
     */
	sock_descriptor = socket(AF_INET, SOCK_STREAM, 0);

	if(sock_descriptor < 0){
		printf("Failed creating socket\n");
	}

	bzero((char *)&serv_addr, sizeof(serv_addr));

	server = gethostbyname(hostname);

	if(server == NULL) {       
		printf("Failed finding server name\n");
		return -1;
	}

	serv_addr.sin_family = AF_INET;
	memcpy((char *) &(serv_addr.sin_addr.s_addr), (char *)(server->h_addr), server->h_length);

    /**
	 * 16 bit port number on which server listens
     * The function htons (host to network short) ensures that an integer is
	 * interpreted correctly (whether little endian or big endian) even if client and
	 * server have different architectures
     */
	serv_addr.sin_port = port_number;

	if (connect(sock_descriptor, (struct sockaddr *)&serv_addr, sizeof(serv_addr)) < 0) {
		printf("Failed to connect to server\n");
		return -1;
	} else {
		printf("Connected successfully - Please enter string\n");
	}
    
	int count=0;
	do {
		const int count = read(sock_descriptor, buff, MAX_SIZE - 1);
        
		if( count > 0) {
			buff[count]=0;
			//printf("%s\n", buff);

            int i = 0;
            for (i = 0; i < count; i++){
                elem.c=buff[i];
                cbWrite(&cb, &elem);
            }
		}
        
        seek(&cb, '[');

        printf("> ");
        if(cb.count >= ELA_DATA_SIZE) {
            /* Remove and print all elements */
            while (!cbIsEmpty(&cb)) {
                
                cbRead(&cb, &elem);
                printf("%c", elem.c);
            }
             printf("\t(%d)\n", cb.count);
        }else{
            printf("\t\t(%d)\n", cb.count);
        }
	} while(count >= 0);

	{/**
		fgets(buff, MAX_SIZE-1, stdin);

		int count = write(sock_descriptor, buff, strlen(buff));

		if(count < 0) {
			printf("Failed writing rquested bytes to server\n");
		}
	*/}
	close(sock_descriptor); 
	return 0;
}
