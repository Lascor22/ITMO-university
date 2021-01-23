#!/bin/bash

proceses=$(ls /proc | grep -E '[0-9]+' | sort -n)
unordered_answer=""
while read LINE; do
	statm=$(find "/proc/$LINE/statm" 2>>/dev/null)
	if [[ "$statm" != "" ]] 
	then
		numbers=$(cat "/proc/$LINE/statm")
		IFS=' ' read -r -a array <<<"$numbers"
		unordered_answer=$unordered_answer"\n$LINE $((${array[1]}-${array[2]}))" 
	fi
done <<<"$proceses" 

echo -e "$unordered_answer" | sort -n -r -k 2 |  awk -F' ' '{print $1 ":" $2}' >4.out

