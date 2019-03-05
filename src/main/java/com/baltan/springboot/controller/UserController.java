package com.baltan.springboot.controller;

import com.baltan.springboot.bean.User;
import com.baltan.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Description:
 *
 * @author Baltan
 * @date 2019/1/8 17:02
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/getAllUsers")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/getUserById/{id}")
    public User getUserById(@PathVariable("id") Integer id) {
        return userService.getUserById(id);
    }

    @GetMapping("/updateUser")
    /**
     * 例如：http://localhost:8080/user/updateUser/?id=4&age=23&name=zhangsan&username=zhangsan&password
     * =zhangsan666
     */
    public User updateUser(User user) {
        return userService.updateUser(user);
    }

    @GetMapping("/deleteUserById/{id}")
    public void deleteUserById(@PathVariable("id") Integer id) {
        userService.deleteUserById(id);
    }

    @GetMapping("/getUserByUsername/{username}")
    public User getUserByUsername(@PathVariable("username") String username) {
        return userService.getUserByUsername(username);
    }
}
