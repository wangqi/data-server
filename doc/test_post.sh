#!/usr/bin/env bash

echo "Send a test post message to server"

export Server="http://localhost:8080/pb"
export Query="action=purchase&country_code=cn&source=talkingdata&app_key=battleship2cn&site_id=1&site_name=%25E6%2588%2598%25E8%2588%25B0%25E5%25B8%259D%25E5%259B%25BD2-IOS-CN&plat_id=hcc8eb70726c00ca557a3457e84b15905&ios_ifa=AD424C41-3D92-4412-975C-F8E4107CCB5D&game_user_id=72620552581299889&created=1488442636635&device_type=iPhone9%2C1&bundle_id=AppStore&td_app_key=24957A8D3B3940B88391BBFC09F01E57&order_id=1000000278243915&currency_code=CNY&unit_amount=9800&ip=61.51.80.122&os_version=10.2.1&revenue=648"

curl --data $Query $Server
