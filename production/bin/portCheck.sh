#!/bin/sh
cd `dirname $0`/../
SERVER_HOME=`pwd`
java -classpath $SERVER_HOME/lib/fastcatsearch-server-[0-9]*.jar org.fastcatsearch.util.PingTest "$@"
