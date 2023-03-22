#!/bin/bash

ubuntuVersion=$(lsb_release -a 2>/dev/null  | grep "Release" | awk '{print $2}')
if [[ "$ubuntuVersion" == "20.04" ]]; then
    echo "deb [ arch=amd64,arm64 ] https://repo.mongodb.org/apt/ubuntu focal/mongodb-org/6.0 multiverse" | sudo tee /etc/apt/sources.list.d/mongodb-org-6.0.list
elif [[ "$ubuntuVersion" == "22.04" ]]; then
    echo "deb [ arch=amd64,arm64 ] https://repo.mongodb.org/apt/ubuntu jammy/mongodb-org/6.0 multiverse" | sudo tee /etc/apt/sources.list.d/mongodb-org-6.0.list
elif [[ "$ubuntuVersion" == "18.04" ]]; then
    echo "deb [ arch=amd64,arm64 ] https://repo.mongodb.org/apt/ubuntu bionic/mongodb-org/6.0 multiverse" | sudo tee /etc/apt/sources.list.d/mongodb-org-6.0.list
else
    echo "i cant install mongodb"
    exit 0
fi    



sudo apt update
sudo apt install snapd -y
sudo apt install -y mongodb-org
sudo apt install openjdk-17-jdk
sudo apt install openjdk-17-jre

sudo snap install intellij-idea-ultimate --classic
sudo systemctl daemon-reload
sudo systemctl start mongod


# installation for react 
sudo apt install npm

wget  https://downloads.mongodb.com/compass/mongodb-compass_1.36.1_amd64.deb
sudo dpkg -i mongodb-compass_1.36.1_amd64.deb