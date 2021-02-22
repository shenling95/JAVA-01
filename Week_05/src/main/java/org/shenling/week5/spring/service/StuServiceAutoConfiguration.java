package org.shenling.week5.spring.service;

import org.shenling.week5.spring.POJO.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * 所谓自动配置，就是配置核心业务实例，或者说，是创建核心业务实例
 */
@Configuration
@ConditionalOnClass(StuService.class)
@EnableConfigurationProperties(StuServiceProperties.class)
public class StuServiceAutoConfiguration {
    @Autowired
    private StuServiceProperties properties;

    @Bean
    @ConditionalOnProperty(name = "stu.service.enable", havingValue = "true", matchIfMissing = true)
    // havingValue = "true" 表示如果wrap.service.enable值为true则条件成立
    // 如果没有配havingValue的值，那么只要值不为"false"，都认为条件成立!
    // matchIfMissing 表示如果没有对应配置，那么默认是条件成立还是不成立
    public StuService stuService() {
        return new StuService(new Student(properties.getName(), properties.getAge()));
    }

    // 注意在@Configuration的类中@Bean的创建是有顺序的
    // 下面这个放到上面那么容器中就会有两个WrapService 实例
    @Bean
    @ConditionalOnMissingBean  //容器中没有实例才会创建
    public StuService stuService2() {
        return new StuService(new Student("shenling", 23));
    }
}


