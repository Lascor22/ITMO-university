#!/bin/bash

proceses=$(cat ~/lab3/5.out)
answer=""
prev_ppid="0"
count=0
sum_avg=0
while read LINE; do
	IFS=" : " read -r -a array <<<"$LINE"
	if [[ "${array[1]}" != "" ]]
	then
		curr_pid="${array[0]}"
		curr_ppid="${array[1]}"
		curr_avg="${array[2]}"
		if [[ "$curr_ppid" != "$prev_ppid" ]]
		then
			temp=0
			if [[ "$count" != "" ]] && [[ "$count" != "0" ]]
			then
				temp=$(echo "$sum_avg/$count" | bc -l)
			fi
			if [[ ${temp:0:1} == "." ]] 
			then
				temp="0$temp"
			fi
			

			answer=$answer"\n$prev_ppid is $temp\n"
			prev_ppid="$curr_ppid"
			count=0
			sum_avg=0
		fi
		count=$(($count+1))
		sum_avg=$(echo "$sum_avg+$curr_avg" | bc -l)
		answer=$answer"\n$curr_pid : $curr_ppid : $curr_avg"
	fi
done <<<"$proceses"

echo -e "$answer" 
