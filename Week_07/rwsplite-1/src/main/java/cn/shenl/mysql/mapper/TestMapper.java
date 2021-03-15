package cn.shenl.mysql.mapper;

import cn.shenl.mysql.entity.Test;
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
