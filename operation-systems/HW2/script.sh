#!/bin/bash

#1
sudo grep -rh -e '^ACPI' /var/log/ >errors.log
#2
sudo grep '\(WW\)' Xorg.0.log | sed 's/(WW)/Warning/' >full.log | grep '\(II\)' Xorg.0.log | sed 's/(II)/Information/' >>full.log
#3
sudo grep -horsE '[A-Za-z0-9\-\.\_\/]+@[A-Za-z0-9\-\.\_\/]+\.[a-z]' /etc | grep @ | sort | uniq |tr '\n' ',' | sed 's/.$//'  >emails.lst
#4
sudo file $(find /bin) | grep -E "shell script" | tr ':' ' ' |  awk -F' ' '{print $1" "$2}' | sort -k 1 | uniq -f 1 -c | head -1 | awk -F' ' '{print $2}'
#5
sudo awk -F: '{print $3" "$1}' /etc/passwd | sort -n >5.out 
#6
sudo wc -l $(find /var/log -name '*.log') | tail --line=1 | awk -F' ' '{print $1}'
#7
sudo man bash | sed -e 's/[^[:alpha:]]/ /g' | grep -o -w '\w\{4,\}' | tr "\n" " " | tr -s " " | tr [A-Z] [a-z] | tr " " "\n" | sort | uniq -c | sort -nr | head -4