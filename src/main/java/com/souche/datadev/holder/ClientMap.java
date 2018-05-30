package com.souche.datadev.holder;

import io.netty.channel.Channel;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by chauncy on 2018/3/7.
 */
public class ClientMap {


    private static Map<String, Channel> map = new ConcurrentHashMap(1024);


    public static void add(String clientId, Channel socketChannel) {
        map.put(clientId, socketChannel);
    }


    public static Channel get(String clientId) {
        return map.get(clientId);
    }


    public static void remove(Channel socketChannel) {
        for (Map.Entry entry : map.entrySet()) {
            if (entry.getValue() == socketChannel) {
                map.remove(entry.getKey());
            }
        }
    }

    public static Set<Map.Entry<String, Channel>> getALL() {
        return map.entrySet();
    }

}
