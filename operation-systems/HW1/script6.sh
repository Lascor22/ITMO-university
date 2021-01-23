#!/bin/bash
if [ "$PWD" == "$HOME" ]
then
	exit 0
else
	echo "Error: working directory isn't home"
	exit 1
fi