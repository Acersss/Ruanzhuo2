package com.dgut.jsonBean;

import java.io.Serializable;
import java.util.List;

/**
 * @author WuJiewei
 * 对GllistBean的改进，与前端的json格式吻合
 */
public class GllistChangeBean implements Serializable {

    private String gid;  // 管理员莞工账号
    private String name;  // 管理员名称
    private List<String> role;  // 管理员的角色名称列表
    private Integer state;  // 账号是否停用，0 或 1

    public GllistChangeBean () {

    }

    public GllistChangeBean (String gid, String name, List<String> role, Integer state) {
        this.gid = gid;
        this.name = name;
        this.role = role;
        this.state = state;
    }

    public String getGid() {
        return gid;
    }

    public void setGid(String gid) {
        this.gid = gid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public List<String> getRole() {
        return role;
    }

    public void setRole(List<String> role) {
        this.role = role;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "GllistChangeBean{" +
                "gid=" + gid +
                ", name='" + name + '\'' +
                ", role=" + role +
                ", state=" + state +
                '}';
    }
}
