#!/bin/bash
 
if [ $1 -ge $2 ]
then
	if [ $1 -ge $3 ]
    then 
    	echo $1
		exit 0
	fi
fi

if [ $2 -ge $1 ]
then
	if [ $2 -ge $3 ]
    then 
    	echo $2
		exit 0
	fi
fi

if [ $3 -ge $2 ]
then
	if [ $3 -ge $1 ]
    then 
    	echo $3
		exit 0
	fi
fi
