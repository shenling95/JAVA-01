package org.shenling.week5.spring.POJO;

import org.springframework.context.annotation.ComponentScan;

//该类和 Student 类位于同一包名下
//@ComponentScan注解：
//代表进行扫描，默认是扫描当前包的路径，扫描所有带有 @Component 注解的 POJO。
@ComponentScan(basePackages = {"org.shenling.week5.spring.POJO", "org.shenling.week5.spring.service"})
public class StudentConfig {
}