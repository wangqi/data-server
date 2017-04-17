#!/usr/bin/env bash

# Be pretty
echo -e " "
echo -e " It's used to increment save and send the table's data to S3"
echo -e " <function>(backup|resotre) <table>"
echo -e " "

# Basic variables
mysqlpass="UXPBqanyBp6"
mysqluser="root"
mysqlhost="dataserverdb.cqddzsinpvlz.rds.cn-north-1.amazonaws.com.cn"
mysqlport=3306

region="cn-north-1"
access_key="AKIAPKZGEVPJ5JS3KXRA"
secret_key="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"
bucket="qiku-db-sync"
database="apidb2"
table="ad_install ad_purchase ad_event ad_gameuser ad_register ad_click"

# Timestamp (sortable AND readable)
month=`date +"%Y_%m"`
today=`date +"%Y_%m_%d"`
last_hour=`date +"%Y-%m-%d 00:00:00" -d "1 day ago"`
this_hour=`date +"%Y-%m-%d 00:00:00"`
stamp=`date +"%Y_%m_%d_24" -d "1 day ago"`
#last_hour="2017-04-16 00:00:00"
#this_hour="2017-04-17 00:00:00"
#stamp="2017_04_16_24"
echo "last_hour=$last_hour"
echo "this_hour=$this_hour"
echo "stamp=$stamp"

filename="$database-$stamp.sql"
tmpfile="$month/$filename"

backup() {
    echo "backup data between $last_hour and $this_hour"
    # Define our filenames
    object="$bucket/$stamp/$filename"

    echo "backup file is: $filename. backup table is: $table from last_hour: $last_hour. this hour: $this_hour"

    mkdir $month
    echo "#"mysqldump -u $mysqluser -p$mysqlpass -h $mysqlhost -P $mysqlport --compact --no-create-db --no-create-info --insert-ignore --compress --force "$database" "$table" --where "created >= '$last_hour' and created<'$this_hour'" > "$tmpfile"
    mysqldump -u $mysqluser -p$mysqlpass -h $mysqlhost -P $mysqlport --compact --no-create-db --no-create-info --insert-ignore --compress --force "$database" $table --where "created >= ""'""$last_hour""'"" and created < ""'""$this_hour""'" >> "$tmpfile"
    if [ -s "$tmpfile" ]
    then
        echo "$tmpfile is not empty"
        gzip -f "$tmpfile"
        s3put --access_key $access_key --secret_key $secret_key --bucket $bucket --prefix ./ --region $region "$tmpfile".gz
    else
        echo "$tmpfile is empty. ignore it"
        rm -f "$tmpfile"
    fi
}

restore() {
    cd /data/scripts
    echo "restore data"
    url="https://s3.cn-north-1.amazonaws.com.cn/qiku-db-sync/$tmpfile.gz"
    echo "url: $url"
    #curl --retry 100 --retry-delay 1 --connect-timeout 30 "$url" -O "$filename.gz"
    rm -f "$filename.gz"
    wget --retry-connrefused --waitretry=1 --read-timeout=20 --timeout=15 "$url" -O "$filename.gz"
    gunzip -f "$filename.gz"
    mysql -uroot -pr00t1234 -h qikudb.c2v07yqpyqzr.ap-northeast-1.rds.amazonaws.com $database < "$filename"
    mv "$filename" log/
    gzip -f log/"$filename"
}

manual_load() {
    cd /data/scripts
    echo "manual load sql into database: $1"
    for sql_file in log/$database-$1_*.sql
    do
        echo "reimport $sql_file"
        mysql -uroot -pr00t1234 -h qikudb.c2v07yqpyqzr.ap-northeast-1.rds.amazonaws.com $database < $sql_file
    done
}

if [ "$1" = "backup" ]
then
    backup
elif [ "$1" = 'restore' ]
then
    restore
elif [ "$1" = 'manual_load' ]
then
    manual_load $2
else
    echo "unknown command $1"
fi