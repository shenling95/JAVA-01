package cn.shenl.counter;

import redis.clients.jedis.Jedis;

import java.util.Collections;

public class CounterBasedOnRedis {

    private Jedis jedis;

    private String GOODS ;

    private static final String RELEASE_SUCCESS = "OK";

    public CounterBasedOnRedis() {
        this.jedis = new Jedis("localhost", 6379);
        GOODS = "goods";
    }

    public boolean tryDecrease(String countKey, String requestId){
        String script = "if redis.call('get', KEYS[1]) >= 1 then return redis.call('DECR ', KEYS[1]) else return -1 end";
        Object result = jedis.eval(script, Collections.singletonList(countKey), Collections.singletonList(requestId));

        return "-1".equals(result);
    }



}
