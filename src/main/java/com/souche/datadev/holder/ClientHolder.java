package com.souche.datadev.holder;

import com.souche.datadev.pack.Header;
import io.netty.channel.Channel;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by chauncy on 2018/3/7.
 */
public class ClientHolder {


    private static Map<String, Channel> map = new ConcurrentHashMap(1024);


    private static Map<Channel, String> channelStringMap = new ConcurrentHashMap<>(1024);


    public static void add(Header header, Channel channel) {
        map.put(header.getPhone(), channel);
        channelStringMap.put(channel, header.getPhone());
    }

    public static void add(String clientId, Channel socketChannel) {
        map.put(clientId, socketChannel);
    }


    public static Channel get(String clientId) {
        return map.get(clientId);
    }


    public static void remove(Channel socketChannel) {

        //space change time
        String phone = channelStringMap.get(socketChannel);
        channelStringMap.remove(socketChannel);
        map.remove(phone);
//        for (Map.Entry entry : map.entrySet()) {
//            if (entry.getValue() == socketChannel) {
//                map.remove(entry.getKey());
//            }
//        }
    }

    public static Set<Map.Entry<String, Channel>> getALL() {
        return map.entrySet();
    }

}
