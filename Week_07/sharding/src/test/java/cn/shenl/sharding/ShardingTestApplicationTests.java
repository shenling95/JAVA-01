package cn.shenl.sharding;

import cn.shenl.sharding.service.TestService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ShardingTestApplicationTests {

	@Autowired
	private TestService service;

	@Test
	void contextLoads() {
		service.addOne(new cn.shenl.sharding.entity.Test(4,4));
	}

	@Test
	void selectTest() {
		System.out.println(service.findById(3));
		System.out.println(service.findById(2));

	}

}
