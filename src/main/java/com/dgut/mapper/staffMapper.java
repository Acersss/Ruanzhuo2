package com.dgut.mapper;

import com.dgut.jsonBean.addBean;
import com.dgut.jsonBean.listBean;
import com.dgut.jsonBean.wageBean;
import com.dgut.model.staff;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface staffMapper {

    @Select("<script>"
            + "select wid,name,sex,degree,department,lphone,state,signingTime,departureTime from staff"
            + " <where>"
            + "  <if test='department != null' > "
            + "     and department = #{department}"
            + " </if>"
            + "  <if test='degree != null' > "
            + "     and degree = #{degree}"
            + " </if>"
            + "  <if test='search != null' > "
            + "     and name = #{search}"
            + " </if>"
            + "</where>"
            + "</script>")
    List<listBean> findList(@Param("department")String department, @Param("degree")String degree, @Param("stime")String stime, @Param("etime")String etime, @Param("search")String search, @Param("currentPage")String currentPage);

    @Select("select * from staff")
    List<staff> findOutlist(@Param("department")String department, @Param("degree")String degree, @Param("stime")String stime, @Param("etime")String etime, @Param("search")String search);

    @Update("update staff set state='0',departureTime=#{time} where wid=#{wid}")
    void deleteByWid(@Param("wid")String wid,@Param("time")String time);

    @Insert("insert into staff(name,sex,nation,nationality,origin,idType,idNumber,education,degree,department,job,title,lPhone,sPhone,email) VALUES(#{bean.name},#{bean.sex},#{bean.nation},#{bean.nationality},#{bean.origin},#{bean.idType},#{bean.idNumber},#{bean.education},#{bean.degree},#{bean.department},#{bean.job},#{bean.title},#{bean.lPhone},#{bean.sPhone},#{bean.email})")
    int add(@Param("bean")addBean bean);

    @Select("<script>"
            + "select wid,name,sex,degree,department,baseWage from staff"
            + " <where>"
            + "  <if test='department != null' > "
            + "     and department = #{department}"
            + " </if>"
            + "  <if test='degree != null' > "
            + "     and degree = #{degree}"
            + " </if>"
            + "  <if test='search != null' > "
            + "     and name = #{search}"
            + " </if>"
            + "</where>"
            + "</script>")
    List<wageBean> findXclist(@Param("department")String department, @Param("degree")String degree, @Param("search")String search);
}
