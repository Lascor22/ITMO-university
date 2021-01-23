#!/bin/bash
while true; do
	read LINE
	case "$LINE" in
		
		"TERM")
			kill -SIGTERM $(cat .pid)
			rm .pid
			exit
		;;
		
		"+")
			kill -USR1 $(cat .pid)
		;;

		
		'*')
			kill -USR2 $(cat .pid)
		;;
		
		*)
			:
		
		;;
	esac
done