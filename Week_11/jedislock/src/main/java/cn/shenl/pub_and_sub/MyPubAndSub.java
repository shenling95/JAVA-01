package cn.shenl.pub_and_sub;

import redis.clients.jedis.JedisPubSub;

/**
 * @ClassName MyPubAndSub
 * @Description TODO
 * @Author dmm
 * @Date 2021/3/31 20:20
 * @Version 1.0
 */
public class MyPubAndSub extends JedisPubSub {
    @Override
    public void onMessage(String channel, String message) {
        System.out.println("onMessage  Channel:" + channel + ",Message:" + message);
    }

    @Override
    public void onPMessage(String pattern, String channel, String message) {
        System.out.println("onPMessage  Pattern:" + pattern + ",Channel:" + channel + ",Message:" + message);
    }

    @Override
    public void onSubscribe(String channel, int subscribedChannels) {
        System.out.println(" onSubscribe---channel:"+channel+",subscribedChannels:"+subscribedChannels);
    }

    @Override
    public void onPUnsubscribe(String pattern, int subscribedChannels) {
        System.out.println(" onPUnsubscribe---pattern:"+pattern+",subscribedChannels:"+subscribedChannels);
    }

    @Override
    public void onPSubscribe(String pattern, int subscribedChannels) {
        System.out.println("onPSubscribe---pattern:"+pattern+",subscribedChannels:"+subscribedChannels);
    }

}
