#!/bin/bash
sleep 120

TIME=$(date --date='2 min' | awk -F' ' '{print $1 " " $2 " " $3 " " $4 " " $6}')

echo "./1.sh" | at -t "$TIME"
./1.sh