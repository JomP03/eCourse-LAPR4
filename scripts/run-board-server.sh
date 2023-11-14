#!/usr/bin/env bash

echo ""
echo "All Packages will be updated..."
sudo apt-get update -y >> /dev/null

mvn --version >> /dev/null

if [ "$?" != 0 ]; then
    echo ""
    echo "Maven is not installed. Please install maven and try again."
    echo "You can use the following commands"
    echo ""
    echo "sudo apt-get install maven"
    echo ""
    exit 1
fi

java --version >> /dev/null

if [ "$?" != 0 ]; then
    echo ""
    echo "Java is not installed. Please install java and try again."
    echo "You can use the following command:"
    echo ""
    echo "sudo apt-get install openjdk-19-jdk"
    echo ""
    exit 1
fi

cd ..

export ECOURSE_BS=ecourse.app.board.server/target/app.board.server-0.1.0.jar:ecourse.app.board.server/target/dependency/*;

java -cp $ECOURSE_BS ECourseBoardServerApp
