#!/bin/bash

echo $$ > .pid

current=1

MODE="ADD"

end()
{
	MODE="STOP"
}

mul()
{
	MODE="MULTIPLY"
}

add()
{
	MODE="ADD"
}

trap 'end' SIGTERM 

trap 'add' USR1 

trap 'mul' USR2 

while true; do
	
	case "$MODE" in

		"ADD")
			current=$(($current+2))
			echo "$current"
		;;

		"MULTIPLY")
			current=$(($current*2))
			echo "$current"
		;;

		"STOP")
			echo "Stopped by SIGTERM"
			exit
		;;
	
	esac
	sleep 1
done