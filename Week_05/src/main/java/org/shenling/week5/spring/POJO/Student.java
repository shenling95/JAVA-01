package org.shenling.week5.spring.POJO;


import lombok.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Component(value = "student1")
public class Student {
    @Value("shenling")
    private String name;
    @Value("23")
    private int age;
}
