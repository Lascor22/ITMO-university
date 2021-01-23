#!/bin/bash
curr=1
cnt=1
read curr
while [ $(($curr%2)) -ne 0 ]; do
	cnt=$(($cnt+1))
	read curr
done
echo "cnt = $cnt"