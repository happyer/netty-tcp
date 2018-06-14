package com.chauncy.datadev;

import com.chauncy.datadev.server.KMServer;
import com.chauncy.datadev.server.define.IServer;

/**
 * Created by chauncy on 2018/5/29.
 */
public class App {


    private final static int port = 6688;
    private final static String serverName = "km-server";


    public static void main(String[] args) {

        IServer kmServer = new KMServer(serverName, port);
        try {
            kmServer.startServer();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
