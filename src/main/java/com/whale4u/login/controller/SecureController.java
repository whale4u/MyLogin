package com.whale4u.login.controller;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/secure")
public class SecureController {
    @RequestMapping("/users")
    public String loginSuccess() {
        return "Welcomed to users pages!";
    }
}
