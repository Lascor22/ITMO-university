#!/bin/bash

proceses=$(ls /proc | grep -E '[0-9]+' | sort -n)
unordered_answer=""

while read LINE; do

	status_check=$(find "/proc/$LINE/status" 2>>temp_err.out)
	sched_check=$(find "/proc/$LINE/sched" 2>>temp_err.out)

	if [[ "$status_check" != "" ]] && [[ "$sched_check" != "" ]]
	then
		status=$(cat "/proc/$LINE/status")
		sched=$(cat "/proc/$LINE/sched")

		pid_cur=$(echo -e "$status" | grep -E '^Pid:' | tr ':' ' ' | tr -s ' ' | awk -F' ' '{print $2}')
		ppid_cur=$(echo -e "$status" | grep -E '^PPid:' | tr ':' ' ' | tr -s ' ' | awk -F' ' '{print $2}')
		sum_exec=$(echo -e "$sched" | grep -E '^se.sum_exec_runtime' | tr ':' ' ' | tr -s ' ' | awk -F' ' '{print $2}')
		nr=$(echo -e "$sched" | grep -E '^nr_switches' | tr ':' ' ' | tr -s ' ' | awk -F' ' '{print $2}')
		SleepAVG=$(echo "$sum_exec/$nr" | bc -l)
		if [[ ${SleepAVG:0:1} == "." ]] 
		then
			SleepAVG="0$SleepAVG"
		fi
		unordered_answer=$unordered_answer"\n$pid_cur $ppid_cur $SleepAVG" 
	fi
done <<<"$proceses"

echo -e "$unordered_answer" | sort -n -k 2 | awk -F' ' '{print $1 " : " $2 " : " $3}' >5.out

rm temp_err.out
