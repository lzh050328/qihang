package com.lzh.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.spring.service.impl.ServiceImpl;
import com.lzh.common.DataPage;
import com.lzh.common.Result;
import com.lzh.dto.RoleDto;
import com.lzh.mapper.RoleMapper;
import com.lzh.pojo.Dept;
import com.lzh.pojo.Role;
import com.lzh.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {
    @Autowired
    private RoleMapper roleMapper;
    @Override
    public DataPage<Role> getRolesByPage(RoleDto roleDto) {
        int pageNo = (roleDto.getPage() == null || roleDto.getPage() < 1) ? 1 : roleDto.getPage();
        int pageSize = (roleDto.getPageSize() == null || roleDto.getPageSize() < 1) ? 10 : roleDto.getPageSize();
        IPage<Role> page = new Page<>(pageNo, pageSize);
        LambdaQueryWrapper<Role> lambdaQueryWrapper = Wrappers.lambdaQuery(Role.class);
        lambdaQueryWrapper.like(roleDto.getName() != null && roleDto.getName().length() > 0, Role::getName, roleDto.getName());
        lambdaQueryWrapper.like(roleDto.getLabel() != null && roleDto.getLabel().length() > 0, Role::getLabel, roleDto.getLabel());
        roleMapper.selectPage(page, lambdaQueryWrapper);
        DataPage<Role> dataPage = new DataPage<Role>(page.getTotal(), page.getRecords());
        return dataPage;

    }

//    @Override
//    public Integer deleteRole(Integer id) {
//        return roleMapper.deleteById(id) == 1 ? 1 : 0;
//
//    }

    @Override
    public Integer addRole(Role role) {
        role.setCreateTime(LocalDateTime.now());
        role.setUpdateTime(LocalDateTime.now());
        return roleMapper.insert(role) == 1 ? 1 : 0;
    }

//    @Override
//    public Role findRoleById(Integer id) {
//        Role role = roleMapper.selectById(id);
//        return role;
//    }

//    @Override
//    public Integer updateRole(Role role) {
//        role.setUpdateTime(LocalDateTime.now());
//        return roleMapper.updateById(role) == 1 ? 1 : 0;
//    }

//    @Override
//    public List<Role> findRoleList() {
//        LambdaQueryWrapper<Role> lambdaQueryWrapper = Wrappers.lambdaQuery(Role.class);
//
//        return roleMapper.selectList(lambdaQueryWrapper);
//    }
}
