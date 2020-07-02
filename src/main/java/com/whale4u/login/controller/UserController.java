package com.whale4u.login.controller;

import com.whale4u.login.model.Result;
import com.whale4u.login.model.User;
import com.whale4u.login.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping(value = "/regist")
    public Result regist(User user) {
        return userService.regist(user);
    }


    @PostMapping(value = "/login")
    public Result login(User user) {
        return userService.login(user);
    }
}
