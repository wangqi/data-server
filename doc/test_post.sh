#!/usr/bin/env bash

echo "Send a test post message to server"

export Server="http://localhost:8080/pb"
export Server="http://api.qiku.mobi/pb"
#export Query="action=purchase&country_code=cn&source=talkingdata&app_key=battleship2cn&site_id=1&site_name=%25E6%2588%2598%25E8%2588%25B0%25E5%25B8%259D%25E5%259B%25BD2-IOS-CN&plat_id=hcc8eb70726c00ca557a3457e84b15905&ios_ifa=AD424C41-3D92-4412-975C-F8E4107CCB5D&game_user_id=72620552581299889&created=1488442636635&device_type=iPhone9%2C1&bundle_id=AppStore&td_app_key=24957A8D3B3940B88391BBFC09F01E57&order_id=1000000278243915&currency_code=CNY&unit_amount=9800&ip=61.51.80.122&os_version=10.2.1&revenue=648"

export Query="action=purchase&country_code=cn&source=talkingdata&app_key=battleship2cn&site_id=1&site_name=%E6%88%98%E8%88%B0%E5%B8%9D%E5%9B%BD2-IOS-CN&plat_id=hcc8eb70726c00ca557a3457e84b15905&ios_ifa=09CF6B7E-F1C5-4817-B21C-7C9CA9D46652&game_user_id=72339073309615899&device_type=iPhone9,1&bundle_id=AppStore&install_time=1488533316284&td_app_key=24957A8D3B3940B88391BBFC09F01E57&currency_code=CNY&ip=61.51.80.122&os_version=10.2.1&revenue=3000&payout=1000000278663222&"

curl --data $Query $Server
