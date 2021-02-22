package org.shenling.week5.spring.service;

import lombok.Getter;
import lombok.Setter;
import org.shenling.week5.spring.POJO.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component("studentService")
public class StudentServiceImpl implements StudentService {

    @Autowired
    private Student student  = null;

    @Override
    public void printStudent() {
        System.out.println(student);
    }
}
