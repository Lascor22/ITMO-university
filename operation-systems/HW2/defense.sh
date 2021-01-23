#!/bin/bash
sudo find /var/log/ >list.txt
grep -rhnE "*/pm/*" list.txt