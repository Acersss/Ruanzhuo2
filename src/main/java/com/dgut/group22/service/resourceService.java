package com.dgut.group22.service;

import com.dgut.group22.javaBean.experiment_house;
import com.dgut.group22.javaBean.resource;
import com.dgut.group22.dao.resourceDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class resourceService {

    @Autowired
    private resourceDao resourceDao;
    public List<resource> GetAllResource(){
        List<resource> resources = resourceDao.selectAllResource();
        return resources;
    }

    public List<experiment_house> GetAllExperiment_house(){
        List<experiment_house> experiment_houses = resourceDao.selectAllExperiment_house();
        return experiment_houses;
    }
}
