package com.atguigu.zookeeper;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.util.ArrayList;
import java.util.List;

public class DistributeClient {
    public static void main(String[] args) throws Exception{
        DistributeClient client = new DistributeClient();
        //1 获取zookeeper集群连接
        client.getConnect();
        //2 注册监听
        client.getChildren();
        //3 后续逻辑处理
        client.bussiness();
    }

    private void bussiness() throws Exception{
        Thread.sleep(Long.MAX_VALUE);
    }

    private void getChildren() throws Exception{
        List<String> children = zkClient.getChildren("/servers", true);
        //存储服务器节点主机名称集合
        ArrayList<Object> hosts = new ArrayList<Object>();
        for (String child : children) {
            byte[] data = zkClient.getData("/servers/" + child, false, null);
            hosts.add(new String(data));
        }

        //将所有在西安主机名称打印到控制台
        System.out.println(hosts);
    }

    private String connectString = "127.0.0.1:2181,127.0.0.1:2182,127.0.0.1:2183";
    private int sessionTImeout = 2000;
    private ZooKeeper zkClient;

    private void getConnect() throws Exception {
        zkClient = new ZooKeeper(connectString, sessionTImeout, new Watcher() {
            public void process(WatchedEvent watchedEvent) {
                try {
                    getChildren();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
