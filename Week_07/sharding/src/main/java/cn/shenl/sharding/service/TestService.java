package cn.shenl.sharding.service;


import cn.shenl.sharding.entity.Test;
import cn.shenl.sharding.mapper.TestMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName TestService
 * @Description TODO
 * @Author dmm
 * @Date 2021/3/14 18:20
 * @Version 1.0
 */

@Service
public class TestService {

    @Autowired
    private TestMapper testMapper;

    public Test findById(int id) {
        return testMapper.selectById(id);
    }

    public int addOne(Test test){
        return testMapper.insertOne(test);
    }

}
