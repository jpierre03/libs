all: build
	date

build: color color-stderr

color: color.c color.h
	cc color.c -o color

color-stderr: color-stderr.c
	cc color-stderr.c -o color-stderr

