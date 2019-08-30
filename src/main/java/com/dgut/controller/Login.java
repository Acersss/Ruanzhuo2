package com.dgut.controller;

import com.alibaba.fastjson.JSON;
import com.dgut.service.ManagersService;
import com.dgut.util.Send;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.dgut.jsonBean.Manager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class Login {

    @Autowired
    private HttpServletRequest request;
    @Autowired
    private ManagersService managersService;

    @RequestMapping(value = "/login/dgut")
    public String login(String token, String state,HttpServletResponse response) throws IOException {
        List<NameValuePair> first = new ArrayList<>();
        first.add(new BasicNameValuePair("token", token));
        first.add(new BasicNameValuePair("appid", "javaee"));
        first.add(new BasicNameValuePair("appsecret", "b3b52e43ccfd"));
        first.add(new BasicNameValuePair("userip", request.getLocalAddr()));
        String fj = Send.post("https://cas.dgut.edu.cn/ssoapi/v2/checkToken", first);

        if (JSON.parseObject(fj).getString("message").equals("success")) {
            String access_token = JSON.parseObject(fj).getString("access_token");
            String openid = JSON.parseObject(fj).getString("openid");
            List<NameValuePair> second = new ArrayList<>();
            second.add(new BasicNameValuePair("access_token", access_token));
            second.add(new BasicNameValuePair("openid", openid));
            String sj = Send.post("https://cas.dgut.edu.cn/oauth/getUserInfo", second);

            if (JSON.parseObject(sj).getString("message") == null) {
                Manager manager = JSON.parseObject(sj, Manager.class);
                request.getSession().setAttribute("manager", manager);
                System.out.println(manager);
                if (manager!=null){
                    managersService.setManagersName(manager.getUsername(),manager.getName());
                }
                //response.sendRedirect("http://ming-cdn.test.upcdn.net");
                response.sendRedirect("http://localhost:8080");
            } else {
                return JSON.parseObject(sj).getString("message");
            }
        } else {
            return JSON.parseObject(fj).getString("message");
        }
        return "1";
    }

    @RequestMapping(value="/loginstate",method = {RequestMethod.POST,RequestMethod.GET})
    public Manager zt(HttpServletResponse response) throws IOException {
        Manager m = (Manager) request.getSession().getAttribute("manager");
        if(m==null) {
            m = new Manager();
            m.setName("未登录");
            m.setOpenid("https://cas.dgut.edu.cn?appid=javaee&state="+ request.getSession().getId());
        } else if (managersService.findGid(m.getUsername())) {
            m = new Manager();
            m.setName("未登记");
            m.setOpenid("http://localhost:8080/tc");
        }
        return m;
    }

    @RequestMapping(value="/tc",method = {RequestMethod.POST,RequestMethod.GET})
    public void tc(HttpServletResponse response) throws IOException {
        request.getSession().removeAttribute("manager");
        response.sendRedirect("https://cas.dgut.edu.cn/logout?callback=http://localhost:8080");
    }
}
