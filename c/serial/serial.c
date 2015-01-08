#include<stdio.h>
#include<string.h>
#include <errno.h>
#include <termios.h>
#include <unistd.h>
#include <termios.h>
#include <fcntl.h>
#include <sys/signal.h>
#include <sys/types.h>


int set_interface_attribs (int fd, int speed, int parity);
void set_blocking (int fd, int should_block);

int set_interface_attribs (int fd, int speed, int parity)
{
	struct termios tty;
	memset (&tty, 0, sizeof tty);
	if (tcgetattr (fd, &tty) != 0) {
		printf ("error %d from tcgetattr", errno);
		return -1;
	}

	cfsetospeed (&tty, speed);
	cfsetispeed (&tty, speed);

	tty.c_cflag = (tty.c_cflag & ~CSIZE) | CS8;     // 8-bit chars
	// disable IGNBRK for mismatched speed tests; otherwise receive break
	// as \000 chars
	tty.c_iflag &= ~IGNBRK;         // disable break processing
	tty.c_lflag = 0;                // no signaling chars, no echo,
	// no canonical processing
	tty.c_oflag = 0;                // no remapping, no delays
	tty.c_cc[VMIN]  = 0;            // read doesn't block
	tty.c_cc[VTIME] = 5;            // 0.5 seconds read timeout

	tty.c_iflag &= ~(IXON | IXOFF | IXANY); // shut off xon/xoff ctrl

	tty.c_cflag |= (CLOCAL | CREAD);// ignore modem controls,
	// enable reading
	tty.c_cflag &= ~(PARENB | PARODD);      // shut off parity
	tty.c_cflag |= parity;
	tty.c_cflag &= ~CSTOPB;
	tty.c_cflag &= ~CRTSCTS;

	if (tcsetattr (fd, TCSANOW, &tty) != 0)	{
		printf ("error %d from tcsetattr", errno);
		return -1;
	}
	return 0;
}

void set_blocking (int fd, int should_block)
{
	struct termios tty;
	memset (&tty, 0, sizeof tty);
	if (tcgetattr (fd, &tty) != 0) {
		printf ("error %d from tggetattr", errno);
		return;
	}

	tty.c_cc[VMIN]  = should_block ? 1 : 0;
	tty.c_cc[VTIME] = 5;            // 0.5 seconds read timeout

	if (tcsetattr (fd, TCSANOW, &tty) != 0) {
		printf ("error %d setting term attributes", errno);
	}
}


int main (int argc, char** argv) {

	/** Définir port par defaut */
	char *portname = "/dev/ttyusb0";
	/** Port en argument */
	if (argc >= 2) {
		portname = argv[2-1];
	}
	/** Ouverture du port serie */
	int fd = open (portname, O_RDWR | O_NOCTTY | O_SYNC);
	/** Vérifier si le port a bien été ouvert */
	if (fd < 0) {
		printf ("error %d opening %s: %s", errno, portname, strerror (errno));
		return -1; //Quitter
	}
	/** Définir des propriétés du port serie */
	set_interface_attribs (fd, B9600, 0);  // set speed to 9600 bps, 8n1 (no parity)
	//set_blocking (fd, 0);                // set no blocking

	/** flusher le port serie */
	usleep (1000000); //attente avant de vider
	tcflush (fd, TCIOFLUSH); //Vider la mémoire du port com
	usleep (1000000); //attente après avoir vider

	/** écoute sur le port serie et traitement */
	char buf [30];
	while(42==42) {
		int i=0;
		int n = read (fd, buf, sizeof buf);  // read up to 100 characters if ready to read
		for (i=0; i < n; i++) {
			printf("%c", buf[i]);
		}
	}
	return 0;
}

