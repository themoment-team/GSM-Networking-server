#!/bin/bash
JAR_NAME=gsmNetworking-0.0.1-SNAPSHOT.jar

CURRENT_PID=$(pgrep -f $JAR_NAME)
if [ -z $CURRENT_PID ]
then
  echo "현재 구동중인 애플리케이션이 없으므로 종료하지 않습니다."
else
  echo "> kill -15 $CURRENT_PID"
  kill -15 $CURRENT_PID
  sleep 5
fi

chmod +x /home/ec2-user/GSM-Networking-builds/build/libs/gsmNetworking-0.0.1-SNAPSHOT.jar

nohup java -jar -Dspring.profiles.active=prod /home/ec2-user/GSM-Networking-builds/build/libs/gsmNetworking-0.0.1-SNAPSHOT.jar > /dev/null 2> /dev/null < /dev/null &