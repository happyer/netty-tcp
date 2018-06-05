package com.souche.datadev;

import com.souche.datadev.server.NettyNioSever;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by chauncy on 2018/5/29.
 */
public class App {


    public static void main(String[] args) {


        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.submit(new NettyNioSever(5000));


    }

}
