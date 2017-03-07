#!/usr/bin/env bash

#awk -F\\t '{print $9}' /data/logs/tomcat8/data_server_access.log_2017-03* | while read data; do
#   curl --data "$data" "http://localhost:8080/pb"
#done

awk -F\\t '{print "http://localhost:8080/pb?"$9}' /data/logs/tomcat8/data_server_access.log_2017-03*  > urls.txt
xargs -n 1 curl  < urls.txt