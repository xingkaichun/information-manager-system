#!/bin/sh
echo "start at: " `date +%Y%m%d%k%M%S`

## service name
APP_NAME=information-manager-system
SERVICE_NAME=information-manager-system-1.0-SNAPSHOT
JAR_NAME=$SERVICE_NAME\.jar
PID=$SERVICE_NAME\.pid

program_dir=`dirname $0`
if [ `echo "$0" |grep -c "/"` -le 0 ];then
   program_dir=`pwd`
else
   program_dir=`cd ${program_dir};pwd`
fi

cd ${program_dir}

echo `pwd`

nohup java -Xms256M -Xmx1024M -Xss512k -XX:+HeapDumpOnOutOfMemoryError -XX:+DisableExplicitGC -XX:+UseParNewGC -XX:+UseConcMarkSweepGC -XX:+UseCMSCompactAtFullCollection -Xloggc:logs/gc.log -Dloader.path=libs/,config/,front-end/ -jar $JAR_NAME &

echo "start ."