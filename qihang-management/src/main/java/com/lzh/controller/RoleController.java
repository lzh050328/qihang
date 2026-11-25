package com.lzh.controller;

import com.lzh.common.DataPage;
import com.lzh.common.Result;
import com.lzh.dto.RoleDto;
import com.lzh.pojo.Role;
import com.lzh.service.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/roles")
public class RoleController {
    @Autowired
    private RoleService roleService;
    @GetMapping
    public Result getRolesByPage(RoleDto roleDto) {
        log.info("分页查询角色");
        log.info("分页查询角色结构 {}", roleDto);
        if (roleService.getRolesByPage(roleDto).getTotal() > 0){
            return Result.success(roleService.getRolesByPage(roleDto));
        }
        return Result.error("没有数据");
    }
    @DeleteMapping("/{id}")
    public Result deleteRole(@PathVariable Integer id) {
        log.info("删除角色");
        log.info("删除角色结构 {}", id);
        return roleService.removeById(id) ? Result.success() : Result.error("删除失败");
    }
    @PostMapping
    public Result addRole(@RequestBody Role role) {
        log.info("添加角色");
        log.info("添加角色结构 {}", role);
        return roleService.addRole(role) > 0 ? Result.success() : Result.error("添加失败");
    }
    @GetMapping("/{id}")
    public Result findRoleById(@PathVariable Integer id) {
        log.info("查询角色");
        log.info("查询角色结构 {}", id);
        Role role = roleService.getById(id);
        return Result.success(role);
    }
    @PutMapping
    public Result updateRole(@RequestBody Role role) {
        log.info("修改角色");
        log.info("修改角色结构 {}", role);
        return roleService.saveOrUpdate(role) ? Result.success() : Result.error("修改失败");
    }
    @GetMapping("/list")
    public Result findRoleList() {
        log.info("查询角色列表");
        List<Role> role = roleService.list();
        log.info("查询角色列表结果: {}", role);
        if (role.size() ==0) {
            return Result.error("没有数据");
        }
        return Result.success(role);

    }
}
