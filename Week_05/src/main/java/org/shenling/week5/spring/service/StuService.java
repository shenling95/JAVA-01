package org.shenling.week5.spring.service;

import lombok.AllArgsConstructor;
import org.shenling.week5.spring.POJO.Student;

@AllArgsConstructor
public class StuService {

    private Student student;

    public Student getStudent(){
        return student;
    }
}
