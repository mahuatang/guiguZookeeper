package com.atguigu.zookeeper;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

public class TestZookeeper {
    private String connectString = "127.0.0.1:2181,127.0.0.1:2182,127.0.0.1:2183";
    private int sessionTimeout = 2000;
    private ZooKeeper zkClient;

    @Before
    public void init() {
        try {
            zkClient = new ZooKeeper(connectString, sessionTimeout, new Watcher() {
                public void process(WatchedEvent watchedEvent) {
                    System.out.println("-----start------");
                    List<String> list = null;
                    try {
                        list = zkClient.getChildren("/", true);
                    } catch (KeeperException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    for (String item : list) {
                        System.out.println(item);
                    }

                    System.out.println("---end-----");
                }
            });

            System.out.println("hello world");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //1.创建节点
    @Test
    public void createNode() throws Exception{
        String path = zkClient.create("/atguigu", "dahaige".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        System.out.println();
    }

    //2.获取子节点,并监控节点的变化
    @Test
    public void getDataAndWatch() throws Exception{
        List<String> list = zkClient.getChildren("/", true);

        for (String item : list) {
            System.out.println(item);
        }

        Thread.sleep(Long.MAX_VALUE);
    }

    //3.判断节点是否存在
    @Test
    public void exist() throws Exception{
        Stat stat = zkClient.exists("/atguig88u", false);
        System.out.println(stat == null ? "not exist" : "exist");
    }

    //监听服务器节点的动态上下线
}
