#!/usr/bin/env bash

echo "Send a test post message to server"

export Server="http://localhost:8080/pb"
#export Server="https://api.qiku.mobi/pb"

export Query="action=login&country_code=cn&source=talkingdata&app_key=battleship2cn&site_id=1&site_name=%E6%88%98%E8%88%B0%E5%B8%9D%E5%9B%BD2-IOS-CN&plat_id=hcc8eb70726c00ca557a3457e84b15905&ios_ifa=09CF6B7E-F1C5-4817-B21C-7C9CA9D46652&game_user_id=72339073309615904&device_type=iPhone9,1&install_time=1488849398307&td_app_key=24957A8D3B3940B88391BBFC09F01E57&ip=117.136.0.161&os_version=10.2.1"

#curl --data $Query $Server

# Test MAT postback
curl "$Server?source=mat&app_key=battleship2cn&site_id=1&region_name=beijing&sdk_version=4.10.0&action=install&country_code=CN&site_name=战舰帝国2-IOS-CN&user_agent=Mozilla%2F5.0+%28iPhone%3B+CPU+iPhone+OS+10_2_1+like+Mac+OS+X%29+AppleWebKit%2F602.4.6+%28KHTML%2C+like+Gecko%29+Mobile%2F14D27&plat_id=b88f4db5-6a87-4749-96d5-afe8a3d1e4e9&publisher_name=&device_id=&device_type=ios+phone&device_carrier=%E4%B8%AD%E5%9B%BD%E7%A7%BB%E5%8A%A8&device_model=iPhone9%2C1&device_brand=Apple&lang=zh-Hans-CN&click_ip=&click_time=&match_type=&ad_name=&currency_code=USD&pub_camp_id=&pub_camp_name=&imp_time=&payout=0&referral_source=&referral_url=&revenue=0&revenue_usd=0&status=approved&ios_ifa=09CF6B7E-F1C5-4817-B21C-7C9CA9D46652&ios_ifv=AF6FB560-A6F9-4D07-AE91-E5643D39FFC5&google_aid=&pub_adset=&pub_ad=&os_version=10.2.1&install_time=2017-03-03+08%3A44%3A32&publisher_id=&agency_name=&campaign_id=&campaign_name=&stat_click_id=&pub_keyword=&pub_place=&adv_camp_id=&adv_camp_name=&adv_adset=&adv_ad=&adv_keyword=&adv_place=&sdk=ios&game_user_id=72339073309615897&os_jailbroke=0&pub_pref_id=&pub_sub1=&pub_sub2=&pub_sub3="

