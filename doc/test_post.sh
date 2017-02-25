#!/usr/bin/env bash

echo "Send a test post message to server"

export Server="http://localhost:8080/pb"
export Query="action=install&source=talkingdata&app_key=battleship_cn&os_version=9.3.2&device_type=ios%20phone&device_brand=Apple&device_carrier=SoftBank&device_mode=iPhone8,1&lang=ja&publisher_id=123456&publisher_name=coolfactory&install_ip=60.121.19.40&install_time=2016-07-02%2018:57:45&site_id=1&site_name=battleship_cn&ios_ifa=786e2626-bf07-4c6f-b2e3-b6f0189efb3c&user_agent=Mozilla/5.0%20(iPhone;%20CPU%20iPhone%20OS%209_3_2%20like%20Mac%20OS%20X)%20AppleWebKit/601.1.46%20(KHTML,%20like%20Gecko)%20Mobile/13F69"

curl --data $Query $Server
