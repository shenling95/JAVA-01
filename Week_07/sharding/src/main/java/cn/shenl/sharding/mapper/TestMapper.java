package cn.shenl.sharding.mapper;

import cn.shenl.sharding.entity.Test;
import org.springframework.stereotype.Repository;

/**
 * @ClassName TestMapper
 * @Description TODO
 * @Author dmm
 * @Date 2021/3/14 16:43
 * @Version 1.0
 */
@Repository
public interface TestMapper {

    Test selectById(int id);

    int insertOne(Test test);
}
