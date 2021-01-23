#!/bin/bash

#2
sudo ps aux | tr -s ' ' | awk -F' ' '{print $2 " " $9}' | sort -k 2 | tail -2 | head -1
#3
sudo ps aux | grep -E ' /sbin/' |  tr -s ' ' | awk -F' ' '{print $2}' >3.out
