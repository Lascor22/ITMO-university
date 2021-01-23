#!/bin/bash
foo=5
if [[ $foo > 06 ]]; 
then
	echo "1- 5 больше 6"
else
	echo "1- 5 меньше 6"
fi
if [[ $foo -gt 06 ]]; 
then
	echo "2- 5 больше 6"
else
	echo "2- 5 меньше 6"
fi