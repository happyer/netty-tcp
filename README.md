

this is gps platform gataway



--online jvm option





DEFAULT_JVM_OPTS="-Xmx4g -Xms4g
-XX:+UseG1GC
-XX:GCTimeRatio=19
-XX:MaxGCPauseMillis=400
-XX:+PrintGC
-XX:+PrintGCDetails
-XX:+PrintGCTimeStamps
-XX:+PrintHeapAtGC
-Xloggc:/home/app-gps/logs/gc.log
-Dcom.sun.management.jmxremote -Dcom.sun.management.jmxremote.port=9010
-Dcom.sun.management.jmxremote.authenticate=false
-Dcom.sun.management.jmxremote.ssl=false
-Djava.rmi.server.hostname=your localhost"


-Djava.nio.channels.spi.SelectorProvider=sun.nio.ch.EPollSelectorProvider


