package org.shenling.week5.spring;

import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.Test;
import org.shenling.week5.database.jdbc.HikariDatasourceConfig;
import org.shenling.week5.spring.POJO.Student;
import org.shenling.week5.spring.POJO.StudentConfig;
import org.shenling.week5.spring.service.StudentService;
import org.shenling.week5.spring.service.StudentServiceImpl;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

import java.sql.SQLException;

@SpringBootTest
class ApplicationTests {

	@Test
	void AnnotationTest() {
		ApplicationContext context = new AnnotationConfigApplicationContext(StudentConfig.class);
		Student student = (Student) context.getBean("student1", Student.class);
		System.out.println(student);
	}

	@Test
	void XmlTest() {
		ApplicationContext context = new GenericXmlApplicationContext("XmlTest");
		Student student = (Student) context.getBean("student2", Student.class);
		System.out.println(student);

		Student student2 = (Student) context.getBean("student3", Student.class);
		System.out.println(student2);
	}

	@Test
	void AutowiredTest() {
		ApplicationContext context = new AnnotationConfigApplicationContext(StudentConfig.class);
		StudentService studentService = context.getBean("studentService", StudentServiceImpl.class);
		studentService.printStudent();
	}

	@Test
	void HikariTest() {
		HikariDatasourceConfig hikariDatasourceConfig = new HikariDatasourceConfig();
		HikariDataSource dataSource = hikariDatasourceConfig.dataSource();
		try {
			dataSource.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
