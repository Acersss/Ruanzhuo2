package com.dgut.group22.service;

import com.dgut.group22.dao.ITeacherDao;
import com.dgut.group22.javaBean.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//Ning
@Service
public class TeacherService {
    @Autowired
    ITeacherDao teacherDao;
    public Teacher findById(String teacher_id){
        return teacherDao.findById(teacher_id);
    }
}
