package cn.shenl.mysql;

import cn.shenl.mysql.service.TestService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
class MysqlWrSpliteApplicationTests {

	@Autowired
	private TestService service;

	@Test
	void contextLoads() {
		cn.shenl.mysql.entity.Test test = service.findById(100);
		System.out.println(test);
	}

}
