#!/bin/bash

PID_TIMES=$(ps aux | tr -s ' ' | awk -F' ' '{print $1 " " $2 ":" $11}' | grep -E 'user *' |  awk -F' ' '{print $2 ":" $11}')

echo -e "$PID_TIMES" | wc | awk -F' ' '{print $1}' >1.out

echo -e "$PID_TIMES" >>1.out
