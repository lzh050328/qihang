package com.lzh.service;

import com.baomidou.mybatisplus.spring.service.IService;
import com.lzh.common.Result;
import com.lzh.common.DataPage;

import com.lzh.dto.DeptDto;
import com.lzh.pojo.Dept;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface DeptService  extends IService<Dept> {

    DataPage<Dept> findByPage(DeptDto deptDto);

    Integer deleteDept(Integer id);

    int add(Dept dept);

    Object findById(Integer id);

    int update(Dept dept);

    List<Dept> findAll();
}
