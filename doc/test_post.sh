#!/usr/bin/env bash

echo "Send a test post message to server"

#export Server="http://localhost:8080/pb"
export Server="https://api.qiku.mobi/pb"

export Query="action=login&country_code=cn&source=talkingdata&app_key=battleship2cn&site_id=1&site_name=%E6%88%98%E8%88%B0%E5%B8%9D%E5%9B%BD2-IOS-CN&plat_id=hcc8eb70726c00ca557a3457e84b15905&ios_ifa=09CF6B7E-F1C5-4817-B21C-7C9CA9D46652&game_user_id=72339073309615904&device_type=iPhone9,1&install_time=1488849398307&td_app_key=24957A8D3B3940B88391BBFC09F01E57&ip=117.136.0.161&os_version=10.2.1"

curl --data $Query $Server
