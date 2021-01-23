#!/bin/bash
MODE="MULTIPLY"
current=1

(tail -n 0 -f data.txt) |
	while true; do

		read LINE;

		case "$LINE" in

		"QUIT")
			echo "expected exit"
			echo "result = $current"
			killall tail
			exit
		;;
		
		'+')
			MODE="ADD"
		;;
		
		'*')
			MODE="MULTIPLY"
		;;
		
		*)
			check=$(echo "$LINE" | grep -E "^[0-9]+$")

			if [[ "$check" != "" ]]
			then
				case "$MODE" in

					"ADD")
						current=$(($current+$LINE))
						echo "$temp + $LINE = $current"
					;;

					"MULTIPLY")
						temp=$current
						current=$(($current*$LINE))
						echo "$temp * $LINE = $current"
					;;

				esac
			else	
				echo "Incorrect input"
				exit 1
			fi
		;;
		esac
	done

	