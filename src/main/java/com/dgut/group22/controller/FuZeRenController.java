package com.dgut.group22.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.dgut.group22.javaBean.Course;
import com.dgut.group22.javaBean.Teacher;
import com.dgut.group22.service.CourseService;
import com.dgut.group22.service.FuZeRenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

//Ning
@RestController
@Transactional
@RequestMapping("/FuZeRen")
public class FuZeRenController {
    @Autowired
    FuZeRenService fuZeRenService;
    @Autowired
    CourseService courseService;

    @RequestMapping(value="/findAllFuZeRen/{page}",method = {RequestMethod.POST})
    public String findAllFuZeRen(@PathVariable("page") String page){
        int anInt = Integer.parseInt(page);
        List<Teacher> fuZeRen = new ArrayList<>();
        List<Teacher> allFuZeRen = fuZeRenService.findAllFuZeRen();

        for(int i=(anInt-1)*9; i<(anInt-1)*9+9 && i<allFuZeRen.size(); i++){
            fuZeRen.add(allFuZeRen.get(i));
        }
        JSONObject jsonObject = new JSONObject();
        int r = allFuZeRen.size()%9==0?0:1;

        jsonObject.put("page",allFuZeRen.size()/9+r);
        jsonObject.put("curPage",anInt);
        jsonObject.put("data",fuZeRen);
        return JSON.toJSONString(jsonObject, SerializerFeature.DisableCircularReferenceDetect);
    }

    @RequestMapping(value="/findAllFuZeRenAfter/{page}",method = {RequestMethod.POST})
    public String findAllFuZeRenAfter(@PathVariable("page") String page){
        int anInt = Integer.parseInt(page);
        List<Teacher> fuZeRen = new ArrayList<>();
        List<Teacher> allFuZeRen = fuZeRenService.findAllTeacher();

        for(int i=(anInt-1)*5; i<(anInt-1)*5+5 && i<allFuZeRen.size(); i++){
            fuZeRen.add(allFuZeRen.get(i));
        }
        JSONObject jsonObject = new JSONObject();
        int r = allFuZeRen.size()%5==0?0:1;
        jsonObject.put("page",allFuZeRen.size()/5+r);
        jsonObject.put("curPage",anInt);
        jsonObject.put("data",fuZeRen);
        return JSON.toJSONString(jsonObject, SerializerFeature.DisableCircularReferenceDetect);
    }

    @RequestMapping(value = "/findFuZeRenById/{teacher_id}&{course_id}",method = {RequestMethod.POST})
    public String findFuZeRenId(@PathVariable("teacher_id")String teacher_id,@PathVariable ("course_id") String course_id){
        Teacher fuZeRen = fuZeRenService.findFuZeRenById(teacher_id);
        Course course=courseService.findById(course_id);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("teacher",fuZeRen);
        jsonObject.put("course",course);
        return JSON.toJSONString(jsonObject, SerializerFeature.DisableCircularReferenceDetect);
    }

    @RequestMapping(value = "/findFuZeRenIdAfter/{teacher_id}",method = {RequestMethod.POST})
    public String findFuZeRenIdAfter(@PathVariable("teacher_id")String teacher_id){
        Teacher fuZeRen = fuZeRenService.findFuZeRenById(teacher_id);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("teacher",fuZeRen);
        return JSON.toJSONString(jsonObject, SerializerFeature.DisableCircularReferenceDetect);
    }

    @RequestMapping(value = "/updateFuZeRen",method = {RequestMethod.POST})
    public String updateFuZeRen(Teacher teacher){
        fuZeRenService.updateTeacher(teacher);
        return "1";
    }

    @RequestMapping(value = "/deleteFuZeRen/{course_id}",method = {RequestMethod.POST})
    public String deleteFuZeRen(@PathVariable ("course_id") String course_id){
        String flag="0";
        flag = fuZeRenService.deleteFuZeRen(course_id);
        JSONObject jsonObject = new JSONObject();
        if(flag=="1")
            jsonObject.put("data","成功");
        else
            jsonObject.put("data","失败");
        return jsonObject.toJSONString();
    }
}
