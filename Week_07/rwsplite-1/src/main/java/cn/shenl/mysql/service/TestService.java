package cn.shenl.mysql.service;

import cn.shenl.mysql.config.datasource.CurDataSource;
import cn.shenl.mysql.config.datasource.DataSourceNames;
import cn.shenl.mysql.entity.Test;
import cn.shenl.mysql.mapper.TestMapper;
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

    @CurDataSource(name = DataSourceNames.SECOND)
    public Test findById(int id) {
        return testMapper.selectById(id);
    }

    public int addOne(Test test){
        return testMapper.insertOne(test);
    }

}
