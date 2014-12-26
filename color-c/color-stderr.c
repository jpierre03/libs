/***
 * Created by Stéphane Gimenez
 * Date : Jul 27 '11 at 15:01
 * URL : http://stackoverflow.com/questions/6841143/how-to-set-font-color-for-stdout-and-stderr
 * http://stackoverflow.com/users/852586/stephane-gimenez
 * use case : ./c_color_script sh -c "while true; do (echo -n a; echo -n b 1>&2) done"
 *
 * No specific licence granted. Might be Public Domain.
 * @2012/10/28 : Jean-Pierre Prunaret : add header
 */

#include "unistd.h"
#include "stdio.h"
#include <sys/select.h>

int main(int argc, char **argv)
{

	char buf[1024];
	int pout[2], perr[2];
	pipe(pout); pipe(perr);

	if (fork()!=0)
	{
		close(1); close(2);
		dup2(pout[1],1); dup2(perr[1],2);
		close(pout[1]); close(perr[1]);
		execvp(argv[1], argv+1);
		fprintf(stderr,"exec failed\n");
		return 0;
	}

	close(pout[1]); close(perr[1]);

	while (1)
	{
		fd_set fds;
		FD_ZERO(&fds);
		FD_SET(pout[0], &fds);
		FD_SET(perr[0], &fds);
		int max = pout[0] > perr[0] ? pout[0] : perr[0];
		int v = select(max+1, &fds, NULL, NULL, NULL);
		if (FD_ISSET(pout[0], &fds))
		{
			int r;
			r = read(pout[0], buf, 1024);
			if (!r) {close(pout[0]); continue;}
			write(1, "\033[33m", 5);
			write(1, buf, r);
			write(1, "\033[0m", 4);
		}
		if (FD_ISSET(perr[0], &fds))
		{
			int r;
			r = read(perr[0], buf, 1024);
			if (!r) {close(perr[0]); continue;}
			write(2, "\033[31m", 5);
			write(2, buf, r);
			write(2, "\033[0m", 4);
		}

		if (v <= 0) break;
	}

	return 0;
}

