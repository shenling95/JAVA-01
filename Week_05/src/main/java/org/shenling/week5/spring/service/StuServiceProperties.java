package org.shenling.week5.spring.service;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties("org.shenling.week5.spring.service")
public class StuServiceProperties {
    private String name;
    private int age;
}
