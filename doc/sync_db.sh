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
database="apidb"
table="$2"

# Timestamp (sortable AND readable)
month=`date +"%Y_%m"`
last_hour=`date +"%Y-%m-%d %H:00:00" -d  "1 hour ago"`
this_hour=`date +"%Y-%m-%d %H:00:00"`
last_hour="2017-03-06 00:00:00"
this_hour="2017-03-09 00:00:00"
stamp=`date +"%Y_%m_%d_%H"`

backup() {
    echo "backup data"
    # Define our filenames
    filename="$database-$stamp.sql.gz"
    tmpfile="$month/$filename"
    object="$bucket/$stamp/$filename"

    echo "backup file is: $filename. backup table is: $table from last_hour: $last_hour. this hour: $this_hour"

    mkdir $month
    #mysqldump -u $mysqluser -p$mysqlpass -h $mysqlhost -P $mysqlport --compact --no-create-db --no-create-info --insert-ignore --compress --force "$database" "$table" --where "created >= '$last_hour' and created<'$this_hour'" | gzip -c > "$tmpfile"
    mysqldump -u $mysqluser -p$mysqlpass -h $mysqlhost -P $mysqlport --compact --no-create-db --no-create-info --insert-ignore --compress --force "$database" "$table" --where "created >= ""'""$last_hour""'"" and created < ""'""$this_hour""'"  | gzip -c > "$tmpfile"

    s3put --access_key $access_key --secret_key $secret_key --bucket $bucket --prefix ./ --region $region "$month/$filename".gz
}

restore() {
    echo "restore data"
}

if [ "$1" = "backup" ]
then
    backup
elif [ "$1" = 'restore' ]
then
    restore
else
    echo "unknown command $1"
fi