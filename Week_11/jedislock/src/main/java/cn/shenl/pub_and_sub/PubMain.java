package cn.shenl.pub_and_sub;

/**
 * @ClassName PubMain
 * @Description TODO
 * @Author dmm
 * @Date 2021/3/31 20:48
 * @Version 1.0
 */
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class PubMain {
    //Channel
    public static final String CHANNEL = "mytestchannel";
    //redis连接地址
    public static final String HOST = "192.168.99.100";
    //端口
    public static final int PORT = 32768;
    //配置
    private static JedisPoolConfig poolConfig = new JedisPoolConfig();
    //连接池
    private static JedisPool jedisPool  = new JedisPool(poolConfig, HOST, PORT);

    public static void main(String[] args) {

        //发送者
        final Jedis publisherJedis = jedisPool.getResource();
        //订阅者
        final Jedis subscriberJedis = jedisPool.getResource();

        //监听器
        final MyPubAndSub jedisPus = new MyPubAndSub();
        //启动线程
        new Thread(new Runnable() {
            public void run() {
                try {
                    System.out.println("开始订阅。。。。。。。。");
                    subscriberJedis.subscribe(jedisPus, CHANNEL);
                    System.out.println("订阅结束。。。。。。。。。");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
        //开始发送消息
        new Publisher(publisherJedis, CHANNEL).startPublish();
        //关闭连接
        publisherJedis.close();
        //取消订阅
        jedisPus.unsubscribe();
        //订阅者关闭
        subscriberJedis.close();
    }

}
