##----------------------------------------------------------------------------
# Created with genmake.pl v1.1 on Tue Jun 18 22:36:44 2013
# genmake.pl home: http://muquit.com/muquit/software/
# Copryright: GNU GPL (http://www.gnu.org/copyleft/gpl.html)
##----------------------------------------------------------------------------
rm=/bin/rm -f
CC= cc
DEFS=  
PROGNAME= jp_color.bin
INCLUDES=  -I.
LIBS=


DEFINES= $(INCLUDES) $(DEFS) -DSYS_UNIX=1
CFLAGS= -g $(DEFINES)

SRCS = jp_color.c jp_color_main.c

OBJS = jp_color.o jp_color_main.o

.c.o:
	$(rm) $@
	$(CC) $(CFLAGS) -c $*.c

all: $(PROGNAME) build

$(PROGNAME) : $(OBJS)
	$(CC) $(CFLAGS) -o $(PROGNAME) $(OBJS) $(LIBS)

clean:
	$(rm) $(OBJS) $(PROGNAME) core *~ *.bin

build: color.bin color-stderr.bin
	date

color.bin: color.c jp_color.h
	cc color.c -o color.bin

color-stderr.bin: color-stderr.c
	cc color-stderr.c -o color-stderr.bin
