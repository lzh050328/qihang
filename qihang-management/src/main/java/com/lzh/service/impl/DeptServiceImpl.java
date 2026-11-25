package com.lzh.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.spring.service.impl.ServiceImpl;
import com.lzh.common.DataPage;
import com.lzh.common.Result;
import com.lzh.dto.DeptDto;
import com.lzh.handler.CustomException;
import com.lzh.mapper.DeptMapper;
import com.lzh.pojo.Dept;
import com.lzh.pojo.Role;
import com.lzh.pojo.User;
import com.lzh.service.DeptService;
import com.lzh.service.RoleService;
import com.lzh.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class DeptServiceImpl extends ServiceImpl<DeptMapper, Dept> implements DeptService {


    @Autowired
    private UserService userService;

    @Lazy
    @Autowired
    private DeptService deptService;



    @Override
    public DataPage<Dept> findByPage(DeptDto deptDto) {
        int pageNo = (deptDto.getPage() == null || deptDto.getPage() < 1) ? 1 : deptDto.getPage();
        int pageSize = (deptDto.getPageSize() == null || deptDto.getPageSize() < 1) ? 10 : deptDto.getPageSize();
        IPage<Dept> page = new Page<>(pageNo, pageSize);
        LambdaQueryWrapper<Dept> lambdaQueryWrapper = Wrappers.lambdaQuery(Dept.class);
        lambdaQueryWrapper.like(deptDto.getName()!=null&&deptDto.getName().length()>0,Dept::getName, deptDto.getName());
        lambdaQueryWrapper.eq(deptDto.getStatus()!=null,Dept::getStatus, deptDto.getStatus());
        deptService.page(page, lambdaQueryWrapper);
        DataPage<Dept> dataPage = new DataPage<>(page.getTotal(), page.getRecords());
        return dataPage;
    }

    @Override
    public Integer deleteDept(Integer id) {
        long count = userService.count(Wrappers.lambdaQuery(User.class).eq(User::getDeptId, id));
        if (count > 0){
            throw new CustomException("部门下有用户，不能删除");
        }
        return deptService.removeById(id) ? 1 : 0;
    }

    @Override
    public int add(Dept dept) {
        dept.setCreateTime(LocalDateTime.now());
        dept.setUpdateTime(LocalDateTime.now());
        return deptService.saveOrUpdate(dept)  ? 1 : 0;
    }

    @Override
    public Object findById(Integer id) {
        return deptService.getById(id);
    }

    @Override
    public int update(Dept dept) {
        dept.setUpdateTime(LocalDateTime.now());
        return deptService.saveOrUpdate(dept) ? 1 : 0;
    }

    @Override
    public List<Dept> findAll() {
        LambdaQueryWrapper<Dept> lambdaQueryWrapper = Wrappers.lambdaQuery(Dept.class);
        lambdaQueryWrapper.eq(Dept::getStatus, 1);
        return deptService.list(lambdaQueryWrapper);
    }
}
