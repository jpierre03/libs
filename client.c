// File name – client.c
// Written and tested on Linux Fedora Core 12 VM

#include <stdio.h>
#include <unistd.h>
#include <sys/socket.h>
#include <sys/types.h>
#include <netinet/in.h>
#include <stdlib.h>
#include <string.h>
#include <netdb.h>

#define MAX_SIZE 50

int main()
{

	// Client socket descriptor which is just integer number used to access a socket
	int sock_descriptor;
	struct sockaddr_in serv_addr;

	// Structure from netdb.h file used for determining host name from local host's ip address
	struct hostent *server;

	// Buffer to input data from console and write to server
	char buff[MAX_SIZE];

	// Create socket of domain - Internet (IP) address, type - Stream based (TCP) and protocol unspecified
	// since it is only useful when underlying stack allows more than one protocol and we are choosing one.
	// 0 means choose the default protocol.
	sock_descriptor = socket(AF_INET, SOCK_STREAM, 0);

	if(sock_descriptor < 0){
		printf("Failed creating socket\n");
	}

	bzero((char *)&serv_addr, sizeof(serv_addr));

	server = gethostbyname("127.0.0.1");

	if(server == NULL) {       
		printf("Failed finding server name\n");
		return -1;
	}

	serv_addr.sin_family = AF_INET;
	memcpy((char *) &(serv_addr.sin_addr.s_addr), (char *)(server->h_addr), server->h_length);

	// 16 bit port number on which server listens
	// The function htons (host to network short) ensures that an integer is  
	// interpreted correctly (whether little endian or big endian) even if client and 
	// server have different architectures
	serv_addr.sin_port = htons(1234);

	if (connect(sock_descriptor, (struct sockaddr *)&serv_addr, sizeof(serv_addr)) < 0) {
		printf("Failed to connect to server\n");
		return -1;
	} else {
		printf("Connected successfully - Please enter string\n");
	}

	fgets(buff, MAX_SIZE-1, stdin);

	int count = write(sock_descriptor, buff, strlen(buff));

	if(count < 0) {
		printf("Failed writing rquested bytes to server\n");
	}

	close(sock_descriptor); 
	return 0;
}

