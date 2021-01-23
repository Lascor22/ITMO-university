#!/bin/bash
str="start"
ans=""
quit="q"
read str
while [ "$str" != "q" ]; do
	ans="$ans$str"
	read str
done
echo "result is $ans"