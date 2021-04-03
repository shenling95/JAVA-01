package cn.shenl.lock;

/*
 * 分布式锁，基于redis实现
 * 其争抢的资源为redis中的一个key
 */

import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;

import java.util.Collections;
import java.util.UUID;

@Slf4j
public class DistributedLock {

    private Jedis jedis;

    private String ID ;

    private static final String SET_IF_NOT_EXIST = "NX";  // 当key不存在时，我们进行set操作

    private static final String SET_WITH_EXPIRE_TIME = "EX"; // 毫秒 ms 为单位

    private static final String LOCK_SUCCESS = "OK";

    private static final String RELEASE_SUCCESS = "OK";

    private static final String LOCK_KEY = "LOCK_KEY";

    public DistributedLock() {
        this.jedis = new Jedis("localhost", 6379);

        // 创建唯一标识
        this.ID = UUID.randomUUID().toString();
    }

    /**
     *
     * @param lockKey 分布式锁key
     * @param requestId 锁的值
     * @param acquireTimeout 尝试获取锁的超时时间，单位毫秒
     * @param expireTime 锁的过期时间，单位毫秒
     * @return true 获得锁， false 未获得
     */
    public boolean tryLock(String lockKey,String requestId,long acquireTimeout,long expireTime){

        long end = System.currentTimeMillis() + acquireTimeout;
        while(System.currentTimeMillis() < end){
            String result = jedis.set(lockKey, requestId, SET_IF_NOT_EXIST, SET_WITH_EXPIRE_TIME, expireTime);

            if (LOCK_SUCCESS.equals(result)) {
                return true;
            }
            try {
                //尝试获取锁失败，休眠10ms再试
                Thread.sleep(10);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                log.error("RedisLock中断异常",e);
                return false;
            }
        }
        return false;
    }

    public boolean tryLock(String lockKey) {
        return tryLock(lockKey, ID, 1000L, 30000L);
    }

    public boolean tryLock() {
        return tryLock(LOCK_KEY, ID, 1000L, 30000L);
    }

    /**
     * 尝试释放分布式锁
     * @param lockKey
     * @param requestId
     */
    public boolean tryRelease(String lockKey,String requestId){
        String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
        Object result = jedis.eval(script, Collections.singletonList(lockKey), Collections.singletonList(requestId));

        if (RELEASE_SUCCESS.equals(result)) {
            return true;
        }
        return false;
    }

    public boolean tryRelease(String lockKey){
        return tryRelease(lockKey, ID);
    }

    public boolean tryRelease(){
        return tryRelease(LOCK_KEY, ID);
    }

}
