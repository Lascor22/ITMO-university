#!/bin/bash
echo $$ > .pid

count=0
MODE="WORK"

change()
{
	MODE="STOP"
}

trap 'change' SIGTERM 

while true; do
	case "$MODE" in
	
		"WORK")

			count=$(($count+1))
			echo "$count"
		;;

		"STOP")
			echo "Stopped by SIGTERM"
			exit
		;;
	esac

	sleep 1
done