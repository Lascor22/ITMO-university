#!/usr/bin/python

s1 = "curl 'http://1d3p.wp.codeforces.com/new' -H 'Connection: keep-alive' -H 'Cache-Control: max-age=0' -H 'Origin: http://1d3p.wp.codeforces.com' -H 'Upgrade-Insecure-Requests: 1' -H 'DNT: 1' -H 'Content-Type: application/x-www-form-urlencoded' -H 'User-Agent: Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) snap Chromium/77.0.3865.75 Chrome/77.0.3865.75 Safari/537.36' -H 'Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3' -H 'Referer: http://1d3p.wp.codeforces.com/' -H 'Accept-Encoding: gzip, deflate' -H 'Accept-Language: ru-RU,ru;q=0.9,en-US;q=0.8,en;q=0.7' -H 'Cookie: JSESSIONID=A280794127A8B406EC368DC03A8C55C3' --data '_af=34be50b38beccce4&proof=%d&amount=%d&submit=Submit' --compressed --insecure"

with open("new_get.sh", "w") as f:
	for i in range(101):
		if i > 2:
			f.write(s1 % (i*i, i) + '\n')
