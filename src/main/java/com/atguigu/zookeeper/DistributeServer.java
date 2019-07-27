package com.atguigu.zookeeper;

import org.apache.zookeeper.*;

import java.io.IOException;

public class DistributeServer {
    public static void main(String[] args) throws Exception {
        DistributeServer server = new DistributeServer();
        //1 连接zookeeper集群
        server.getConnect();
        //2 注册节点
        server.regist(args[0]);
        //3 业务逻辑处理
        server.business();
    }

    public void business() throws Exception{
        Thread.sleep(Long.MAX_VALUE);
    }

    public void regist(String hostname) throws Exception{
        String path =zkClient.create("/servers/server", hostname.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);

        System.out.println(hostname + "is online ");
    }

    private String connectString = "127.0.0.1:2181,127.0.0.1:2182,127.0.0.1:2183";
    private int sessionTImeout = 2000;
    private ZooKeeper zkClient;

    private void getConnect() throws Exception {
        zkClient = new ZooKeeper(connectString, sessionTImeout, new Watcher() {
            public void process(WatchedEvent watchedEvent) {

            }
        });

    }
}
