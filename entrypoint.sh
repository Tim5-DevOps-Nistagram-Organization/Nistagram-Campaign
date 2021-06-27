#!/bin/sh

./consul agent -config-dir=/consul-config &

java -jar nistagram-campaign.jar