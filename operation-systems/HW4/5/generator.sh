#!/bin/bash
echo "1" > data.txt
while true; do
	read LINE
	echo "$LINE" >> data.txt
	if [[ "$LINE" == "QUIT" ]] 
	then
		rm data.txt
		exit
	fi
done

