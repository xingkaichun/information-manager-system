#!/bin/sh
echo "start at: " `date +%Y%m%d%k%M%S`

## service name
APP_NAME=information-manager-system
SERVICE_NAME=information-manager-system-1.0-SNAPSHOT
JAR_NAME=$SERVICE_NAME\.jar

OLD_PID=`ps -ef | grep $JAR_NAME | grep -v grep | awk '{print $2}'`
if [ "$OLD_PID" != "" ]; then
	echo "Process already exist.Will kill it , and then start it！"
	echo "Kill already process!PID:"$OLD_PID
	kill $OLD_PID
else
	echo "process not exist.Will start it！"
fi

program_dir=`dirname $0`
if [ `echo "$0" |grep -c "/"` -le 0 ];then
   program_dir=`pwd`
else
   program_dir=`cd ${program_dir};pwd`
fi

cd ${program_dir}

echo `pwd`

nohup java -Xms256M -Xmx1024M -Xss512k -XX:+HeapDumpOnOutOfMemoryError -XX:+DisableExplicitGC -XX:+UseParNewGC -XX:+UseConcMarkSweepGC -XX:+UseCMSCompactAtFullCollection -Xloggc:logs/gc.log -Dloader.path=libs/,config/,front-end/ -Dspring.profiles.active=pro -jar $JAR_NAME &

echo "start ."