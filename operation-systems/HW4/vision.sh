#!/bin/bash
if [[ "$1" != "" ]]; 
then
	(tail -n 0 -f "$1") |
	while true; do
		read LINE;
		if [[ "$LINE" != "" ]]; then
			echo "$LINE"
		fi
	done
else
	echo "you didn't specify a file"
fi
