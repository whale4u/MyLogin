package com.whale4u.login.service;

import com.whale4u.login.dao.UserMapper;
import com.whale4u.login.model.Result;
import com.whale4u.login.model.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public Result regist(User user) {
        Result result = new Result();
        result.setSuccess(false);
        result.setDetail(null);

        try {
            User existUser = userMapper.findUserByName(user.getUsername());
            if (existUser != null) {
                result.setMsg("用户名已存在");
            } else {
                userMapper.regist(user);
//                System.out.println(user.getId());
                result.setMsg("注册成功");
                result.setSuccess(true);
                result.setDetail(user);
            }
        } catch (Exception e) {
            result.setMsg(e.getMessage());
            e.printStackTrace();
        }

        return result;
    }


    public Result login(User user) {
        Result result = new Result();
        result.setSuccess(false);
        result.setDetail(null);

        try {
            Long userId = userMapper.login(user);
            if(userId == null){
                result.setMsg("用户名或密码错误");
            } else {
                result.setMsg("登录成功");
                result.setSuccess(true);
                user.setId(userId);
                result.setDetail(user);
            }
        } catch (Exception e) {
            result.setMsg(e.getMessage());
            e.printStackTrace();
        }

        return result;
    }

    public Result JwtLogin(User user) {
        String jwtToken = "";
        Result result = new Result();
        result.setSuccess(false);
        result.setDetail(null);

        try {
            Long userId = userMapper.login(user);
            if(userId == null){
                result.setMsg("用户名或密码错误");
            } else {
                result.setMsg("登录成功");
                result.setSuccess(true);
//                user.setId(userId);
//                result.setDetail(user);
                jwtToken = Jwts.builder().setSubject(user.getUsername()).claim("roles", "user").setIssuedAt(new Date())
                        .signWith(SignatureAlgorithm.HS256, "secretkey").compact();
                result.setDetail(jwtToken);
            }
        } catch (Exception e) {
            result.setMsg(e.getMessage());
            e.printStackTrace();
        }

        return result;
    }
}
