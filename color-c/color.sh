#!/bin/bash

while true;
do
	(echo -n a; echo -n b 1>&2)
done

#2> >(while read line; do echo -e "\e[01;31m$line\e[0m"; done)
