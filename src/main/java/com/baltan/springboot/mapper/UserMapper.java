package com.baltan.springboot.mapper;

import com.baltan.springboot.bean.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Description:
 *
 * @author Baltan
 * @date 2018/11/20 11:05
 */
@Mapper
@Component
public interface UserMapper {

    @Select("select * from user")
    List<User> getAllUsers();

    @Select("select * from user where id=#{id}")
    User getUserById(Integer id);

    @Update("update user set name=#{name},age=#{age},username=#{username},password=#{password} where " +
            "id=#{id}")
    void updateUser(User user);

    @Delete("delete user where id=#{id}")
    void deleteUserById(Integer id);

    @Insert("insert into user(name,age,username,password) values(#{name},#{age},#{username},#{username})")
    void insertUser(User user);

    @Select("select * from user where username=#{username}")
    User getUserByUsername(String username);
}
