package com.souche.datadev;

import com.souche.datadev.server.KMServer;
import com.souche.datadev.server.define.IServer;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by chauncy on 2018/5/29.
 */
public class App {


    private final static int port = 6688;
    private final static String serverName = "km-server";


    public static void main(String[] args) {

        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.submit(new KMServer(serverName,port));


    }

}
