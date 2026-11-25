package com.lzh.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lzh.common.DataPage;
import com.lzh.common.Result;
import com.lzh.dto.UserDto;
import com.lzh.pojo.User;
import com.lzh.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;
    @GetMapping
    public Result findByPage(UserDto userDto){
        log.info("分页查询用户: {}", userDto);
        DataPage<User> data = userService.findByPage(userDto);
        return Result.success(data);
    }
    @DeleteMapping("/{ids}")
    public Result deleteByIds(@PathVariable List<Integer> ids){
        log.info("删除用户: {}", ids);
        return userService.removeByIds(ids) ? Result.success() : Result.error("删除失败");
    }
    @PostMapping
    public Result addUser(@RequestBody User user){
        log.info("添加用户: {}", user);
        return userService.saveOrUpdate(user) ? Result.success() : Result.error("添加失败");
    }
    @GetMapping("/{id}")
    public Result addUserById(@PathVariable Integer id){
        log.info("查询用户: {}", id);
        return Result.success(userService.getById(id));
    }
    @PutMapping
    public Result update(@RequestBody User user) {
        log.info("修改用户: {}", user);
        return userService.saveOrUpdate(user) ? Result.success() : Result.error("修改失败");
    }
    @GetMapping("/list")
    public Result findAll(){
        log.info("查询所有用户");
        return Result.success(userService.list());

    }
    @GetMapping("/role/{roleLabel}")
    public Result findByLabel(@PathVariable String roleLabel){
        log.info("查询用户: {}", roleLabel);
        if(userService.findByLabel(roleLabel) != null) {
            return Result.success(userService.findByLabel(roleLabel));
        }
        return Result.error("用户不存在");
    }
    @GetMapping("/dept/{deptId}")
    public Result findByDeptId(@PathVariable Integer deptId){
        log.info("查询用户: {}", deptId);
        return Result.success(userService.findByDeptId(deptId));

    }
    }



