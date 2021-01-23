#!/bin/bash

TIME=$(ps aux | grep -E "$$" | awk -F' ' '{print $9}' | tr -s ' ' | head -1)
DATE=$(date | awk -F' ' '{print $3 " " $2 " "}')

(mkdir ~/test && (echo "catalog test was created successfully" >>~/report | touch ~/test/"$DATE$TIME" )) | 
ping 'www.net_nikogo.ru' 2>>~/report