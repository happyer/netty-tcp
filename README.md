

this is gps platform gataway



--online jvm option

-Xmx4g -Xms4g
-XX:+UseG1GC
-XX:GCTimeRatio=19 
-XX:MaxGCPauseMillis=400

-XX:+PrintGC 
-XX:+PrintGCDetails 
-XX:+PrintGCTimeStamps 
-XX:+PrintHeapAtGC 
-Xloggc:../logs/gc.log 日志文件的输出路径

==开启监控

-Dcom.sun.management.jmxremote -Dcom.sun.management.jmxremote.port=9010 
-Dcom.sun.management.jmxremote.authenticate=false 
-Dcom.sun.management.jmxremote.ssl=false 
-Djava.rmi.server.hostname=192.168.59.99

