#! /bin/bash

REPOSITORY=/home/ec2-user/app/step1
PROJECT_NAME=what-should-i-eat-telegram-bot

cd $REPOSITORY/$PROJECT_NAME/

echo "> Git Pull"

git pull

echo "> Project Build"

./gradlew bootJar

echo "> Copying build file"

cd $REPOSITORY
cp $REPOSITORY/$PROJECT_NAME/build/libs/what-*.jar $REPOSITORY/

echo "Close the application which is currently running"

CURRENT_PID=$(pgrep -f what-to-eat-telegram-bot)


if [ -z "$CURRENT_PID" ]; then
	echo "No application is running"
else
	echo "> kill -15 $CURRENT_PID"
	kill -9 $CURRENT_PID
	sleep 10
fi

echo "New application is released"

JAR_NAME=$(ls -tr $REPOSITORY/ | grep *.jar | tail -n 1)
echo "> JAR Name: $JAR_NAME"
nohup java -jar $REPOSITORY/$JAR_NAME 2>&1 &




