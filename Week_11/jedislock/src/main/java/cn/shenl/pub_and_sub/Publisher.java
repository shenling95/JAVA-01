package cn.shenl.pub_and_sub;

/**
 * @ClassName Publisher
 * @Description TODO
 * @Author dmm
 * @Date 2021/3/31 20:46
 * @Version 1.0
 */
import java.io.BufferedReader;
import java.io.InputStreamReader;

import redis.clients.jedis.Jedis;

public class Publisher {

    private Jedis jedis;
    private String channel;

    public Publisher(Jedis jedis, String channel) {
        this.jedis = jedis;
        this.channel = channel;
    }

    public void startPublish() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            while (true) {
                System.out.println("请输入message:");
                String line = reader.readLine();
                if (!"quit".equals(line)) {
                    jedis.publish(channel, line);
                } else {
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

