#grep -r -E '[A-Za-z0-9\-\.\_\/]+@[A-Za-z0-9\-\.\_\/]+\.[a-z]' test.txt >ans.out

grep -rhosE '[A-Za-z0-9\-\.\_\/]+@[A-Za-z0-9\-\.\_\/]+\.[a-z]' /etc | grep @ | sort | uniq |tr '\n' ',' | sed 's/.$//'  >emails.lst