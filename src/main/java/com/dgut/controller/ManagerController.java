package com.dgut.controller;

import com.dgut.jsonBean.*;
import com.dgut.service.ManagersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author WuJiewei
 * 与管理员相关的控制层
 */

@RestController
@CrossOrigin(origins = "*")
public class ManagerController {

    @Autowired
    private ManagersService managersService;

    /**
     * 封装GllistBeanPage
     * @return
     */
    public Map<String, GllistChangeBean> returnMap(String gid, String name) {
        GllistChangeBean gllistChangeBean = new GllistChangeBean("0", "hi", new ArrayList<>(), 0);
        List<GllistBean> data = managersService.findAll(gid, name);
        Map<String, GllistChangeBean> map = new HashMap<String, GllistChangeBean>();  // 合并后的GllistBean保存在map中
        for(GllistBean getById : data) {
            gllistChangeBean = map.get(getById.getGid());  // 通过id判断是否存在对应的管理员
            if(gllistChangeBean != null){  // 存在则合并role字段，并保存到map
                gllistChangeBean.getRole().add(",");
                gllistChangeBean.getRole().add(getById.getRole());
                map.put(getById.getGid(), gllistChangeBean);
            } else {  // 不存在，则将新的保存到map
//                gllistChangeBean = gllistChangeBean1;  这句有拷贝错误，比较隐蔽
                gllistChangeBean = new GllistChangeBean("0", "hi", new ArrayList<>(), 0);  // 只是初始化一哈
                gllistChangeBean.setGid(getById.getGid());
                gllistChangeBean.setName(getById.getName());
                gllistChangeBean.getRole().add(getById.getRole());  // 将GllistBean的Role字段添加到GllistChangeBean的Roles列表
                gllistChangeBean.setState(getById.getState());
                map.put(getById.getGid(), gllistChangeBean);
            }
        }

        return map;
    }


    /**
     * 展示所有的管理员，还要展示对应的角色
     * @return
     */
    @RequestMapping("/gllist")
    @ResponseBody
    public GllistBeanPage findAll(String gid, String name, String currentPage) {
        if(managersService.findPagePower(8)) return new GllistBeanPage(null,-1);
        if(gid.equals("")) {
            gid = null;
        }
        if(name.equals("")) {
            name = null;
        }
        Map<String, GllistChangeBean> map = returnMap(gid, name);

        int start = (Integer.parseInt(currentPage)-1)*7;
        int end = start+7>map.size()?map.size():start+7;
        GllistBeanPage gllistAndNumBean = new GllistBeanPage(map, map.size(), start, end);
        return gllistAndNumBean;
    }

    /**
     * 修改管理员的角色和状态
     * @param gllistFromGleditBean
     * @return
     */
    @RequestMapping("/gledit")
    @ResponseBody
    public Integer editRolesOfManager(@RequestBody GllistFromGleditBean gllistFromGleditBean) {
        if(managersService.findPagePower(9)) return 3;
        return managersService.editRolesOfManager(gllistFromGleditBean);
    }


    @RequestMapping("/gladda")
    @ResponseBody
    public String addManager(@RequestBody ManagerWithoutNameBean managerWithoutNameBean) {
//        if(managersService.findPagePower(9)) return 3;  //这里要改
        Integer flag =  managersService.addManager(managerWithoutNameBean);
        if (flag == 1)
            return "{\"success\":1}";
        else
            return "{\"success\":0}";
    }

}
