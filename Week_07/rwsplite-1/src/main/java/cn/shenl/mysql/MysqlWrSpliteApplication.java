package cn.shenl.mysql;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;


@MapperScan("cn.shenl.mysql.mapper")
@SpringBootApplication(exclude = {
		DataSourceAutoConfiguration.class
})
public class MysqlWrSpliteApplication {

	public static void main(String[] args) {
		SpringApplication.run(MysqlWrSpliteApplication.class, args);
	}

}
