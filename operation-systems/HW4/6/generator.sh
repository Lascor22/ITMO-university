#!/bin/bash
while true; do
	read LINE
	case "$LINE" in
		
		"TERM")
		kill -SIGTERM $(cat .pid)
		rm .pid
		exit
		;;
		
		*)
		:
		
		;;

	esac
done