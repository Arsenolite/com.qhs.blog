package com.qhs.blog.mapper;

import com.qhs.blog.bean.User;
import org.apache.ibatis.annotations.Param;

/**
 * 2017年5月27日20:03:43实现userMapper
 * 2017年5月29日18:00:19重命名为Dao，Mapper移到XML中实现
 */
public interface userMapper {
    //根据id精确获取User
    public User getUser(@Param("id") Integer id);

    //修改User信息
    public int update(@Param("user") User user);

    //注册用户的时候添加
    public int add(@Param("user") User user);

    //验证用户名是否已经存在

    public User checkName(@Param("user") User user);

    //验证邮箱是否已经存在

    public User checkEmail(@Param("user") User user);

}
