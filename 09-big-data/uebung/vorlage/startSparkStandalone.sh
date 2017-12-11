#!/usr/bin/env bash

echo "Important: Modify this script and point the slave to your spark master url"

echo "Starting Apache Spark Master"
../spark-2.2.0-bin-hadoop2.7/sbin/start-master.sh

echo "Waiting 5 seconds to let the Master come up"
sleep 5

echo "Starting 2 Apache Spark Slaves:"
#Set your local spark master in the line below
../spark-2.2.0-bin-hadoop2.7/sbin/start-slave.sh "spark://cth-qaware-de:7077"
