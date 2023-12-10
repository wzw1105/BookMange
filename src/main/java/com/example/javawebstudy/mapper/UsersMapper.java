package com.example.javawebstudy.mapper;

import com.example.javawebstudy.entity.Student;
import com.example.javawebstudy.entity.User;
import org.apache.ibatis.annotations.*;

public interface UsersMapper {
    @Results({
            @Result(column = "name", property = "username"),
            @Result(column = "password", property = "password")
    })
    @Select("select * from users where name=#{username} and password=#{password}")
    public User selectUserByNamePassword(@Param("username") String submit_username, @Param("password") String submit_password);

}
