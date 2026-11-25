package com.lzh.controller;

import com.lzh.common.Result;
import com.lzh.pojo.User;
import com.lzh.service.UserService;
import com.lzh.vo.LoginVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {
    @Autowired
    private UserService userService;
    @PostMapping("/login")
    public Result login(@RequestBody User user){
        LoginVo loginVo = userService.login(user);
        return Result.success(loginVo);
    }



}
