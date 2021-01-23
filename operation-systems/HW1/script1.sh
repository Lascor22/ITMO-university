#!/bin/bash
if [[ $1 -ne "" ]]
then 
	if [ $1 = $2 ]
	then 
		echo "$1 = $2"
	fi

	if [ $1 != $2 ]
	then 
		echo "$1 != $2"
	fi
fi

