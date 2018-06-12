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


    public static int getCount() {
        return map.size();
    }

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

    public static String getPhone(Channel socketChannel) {
        return channelStringMap.get(socketChannel);
    }


    public static void remove(Channel socketChannel) {


        if (socketChannel != null) {
            String phone = channelStringMap.get(socketChannel);
            channelStringMap.remove(socketChannel);
            if (phone != null) {
                map.remove(phone);
            }
        }

    }

    public static Set<Map.Entry<String, Channel>> getALL() {
        return map.entrySet();
    }

}
