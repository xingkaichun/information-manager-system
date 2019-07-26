#!/bin/sh
echo "start at: " `date +%Y%m%d%k%M%S`

JAR_NAME=${project.artifactId}-${project.version}.jar

OLD_PID=`ps -ef | grep $JAR_NAME | grep -v grep | awk '{print $2}'`
if [ "$OLD_PID" != "" ]; then
	echo "kill already process! PID:"$OLD_PID
	kill $OLD_PID
	echo "starting...！"
else
	echo "starting...！"
fi

program_dir=`dirname $0`
if [ `echo "$0" |grep -c "/"` -le 0 ];then
   program_dir=`pwd`
else
   program_dir=`cd ${program_dir};pwd`
fi

cd ${program_dir}

echo `pwd`

nohup java -Xms256M -Xmx1024M -Xss512k -XX:+HeapDumpOnOutOfMemoryError -XX:+DisableExplicitGC -XX:+UseParNewGC -XX:+UseConcMarkSweepGC -XX:+UseCMSCompactAtFullCollection -Xloggc:gc.log -Dloader.path=libs/,config/,front-end/ -Dspring.profiles.active=pro -jar $JAR_NAME &

echo "start success ."