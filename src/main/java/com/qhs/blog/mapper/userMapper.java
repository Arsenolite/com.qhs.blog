package com.qhs.blog.mapper;

import com.qhs.blog.bean.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * 2017年5月27日20:03:43实现userMapper
 */
public interface userMapper {
    //根据id精确获取User
    @Select("select * from `blog_user` where `id` = #{id}")
    public User getUser(@Param("id") Integer id);

    //修改User信息
    @Update("<script>" +
            "update `blog_user` " +
            "<set> " +
            "<if test=\"user.name !=null\">name=#{user.name},</if>" +
            "<if test=\"user.pwd !=null\">name=#{user.pwd},</if>" +
            "<if test=\"user.sig !=null\">name=#{user.sig},</if>" +
            "<if test=\"user.ava !=null\">name=#{user.ava},</if>" +
            "<if test=\"user.phone !=null\">name=#{user.phone},</if>" +
            "<if test=\"user.level !=null\">name=#{user.level},</if>" +
            "<if test=\"user.email !=null\">name=#{user.email},</if>" +
            "</set>" +
            "where id = #{user.id}" +
            "</script>")
    public int update(@Param("user") User user);

    //注册用户的时候添加
    @Insert("insert into `blog_user` values " +
            "#{user.name},#{user.pwd},#{user.sig},#{user.ava}," +
            "#{user.phone},#{user.level},#{user.email}")
    public int add(@Param("user") User user);

    //验证用户名是否已经存在
    @Select("select Id from `blog_user` where `user_name` = #{user.name}")
    public int checkName(@Param("user") User user);

    //验证用户名是否已经存在
    @Select("select Id from `blog_user` where `user_email` = #{user.email}")
    public int checkEmail(@Param("user") User user);

}
