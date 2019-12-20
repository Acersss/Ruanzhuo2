package com.dgut.group11.service;

import com.dgut.group11.dao.JiaoXueRiLiDao;
import com.dgut.group11.javabean.RiLi;
import com.dgut.group11.javabean.SuccessCourse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JiaoXueRiLiService {

    @Autowired
    private JiaoXueRiLiDao jiaoXueRiLiDao;


    public List<SuccessCourse> findAllSuccessCourse() {
        return jiaoXueRiLiDao.findAllSuccessCourse();
    }

    public SuccessCourse findSuccessCourseById(int success_id) {
        return jiaoXueRiLiDao.findSuccessCourseById(success_id);
    }

    public List<RiLi> findAllRiLiBySuccessCourseId(int success_id) {
        return jiaoXueRiLiDao.findAllRiLiBySuccessCourseId(success_id);
    }
}