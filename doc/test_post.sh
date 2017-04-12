#!/usr/bin/env bash

echo "Send a test post message to server"

export server="http://localhost:8080/pb"
#export server="https://api.qiku.mobi/pb"

export install_query="action=install&source=mat&app_key=battleship-android&site_id=85368&region_name=saitama&sdk_version=4.4.0&country_code=JP&site_name=戦艦帝国-Android-JP&user_agent=Dalvik/2.1.0 (Linux; U; Android 5.0; SC-04F Build/LRX21T)&plat_id=e203fbe3-b97c-4c41-8903-35773410c9b2&publisher_name=Line DSP - RED&device_id=&device_type=android phone&device_carrier=NTT DOCOMO&device_model=SC-04F&device_brand=samsung&lang=ja&click_ip=180.23.106.228&click_time=2017-04-12 05:35:23&match_type=referrer&ad_name=&currency_code=USD&pub_camp_id=&pub_camp_name=&imp_time=&payout=0&referral_source=mat_click_id=90e36b6d9c2037f920b3446cc7e5ea55-20170412-163748&utm_campaign=Internal&utm_content=Internal&utm_medium=Internal&utm_source=Line DSP - RED&utm_term=Internal&referral_url=&revenue=0&revenue_usd=0&status=approved&ios_ifa=&ios_ifv=&google_aid=092C12E8-DF62-4EF3-BD1B-F4703AE489E4&pub_adset=&pub_ad=&os_version=5.0&install_time=2017-04-12 05:46:14&publisher_id=334969&agency_name=850&campaign_id=345736&campaign_name=戦艦帝国-测试版-Android-JP (Default)&stat_click_id=cb3f9d3bdddb2ac1477a3b78ef774d1b&pub_keyword=&pub_place=&adv_camp_id=&adv_camp_name=&adv_adset=&adv_ad=&adv_keyword=&adv_place=&sdk=android&game_user_id=&os_jailbroke=0&pub_pref_id=zzfSnkyft87cdoJwGqUGcC9zuH5620vd0x6rN0q_WyrP&pub_sub1=&pub_sub2=&pub_sub3=&ip=180.23.106.228"

#curl --data $Query $Server


# Test Android MAT Install
curl "$server?$install_query"
