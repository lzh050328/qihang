package com.lzh.controller;

import com.lzh.common.Result;
import com.lzh.dto.DeptDto;
import com.lzh.handler.CustomException;
import com.lzh.pojo.Dept;
import com.lzh.service.DeptService;
import com.lzh.service.impl.DeptServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/depts")
public class DeptController {
    @Autowired
    private DeptService deptService;
    @GetMapping("/list")
    public Result findAll(){
//        打印所有部门的日志
        log.info("查询所有部门");
        log.info("查询所有部门结果: {}", deptService.findAll());
        List<Dept> depts = deptService.findAll();
        if (depts.size() ==0){
            return Result.error("查询失败");
        }else{
            return Result.success(depts);
        }


    }


    @GetMapping
    public Result findByPage(DeptDto deptDto){
        log.info("分页查询部门: {}", deptDto);
        log.info("分页查询部门结果: {}", deptService.findByPage(deptDto));
        if (deptService.findByPage(deptDto).getTotal() == 0) {
            return Result.error("查询失败");
        } else {
            return Result.success(deptService.findByPage(deptDto));
        }



    }



    @DeleteMapping("/{id}")
    public Result deleteDept(@PathVariable Integer id){
        log.info("删除部门: {}", id);
        if (deptService.deleteDept(id) == 1){
            log.info("删除部门结果: {}", "删除成功");
            return Result.success("删除成功");
        }else{
            log.info("删除部门结果: {}", "删除失败");
            return Result.error("删除失败");
        }
    }
    @PostMapping
    public Result add(@RequestBody Dept dept)throws Exception{
        if (dept.getName() == null || dept.getName().length() == 0) {
            throw new CustomException("部门名称不能为空");
        }
        try {
            log.info("添加部门: {}", dept);
            int result = deptService.add(dept);
            log.info("添加部门结果: {}", result);
            return result==1?Result.success("添加成功"):Result.error("添加失败");
        } catch (Exception e) {
            throw new CustomException("部门名称不能重复");
        }
    }
    @GetMapping("/{id}")
    public Result findById(@PathVariable Integer id){
        log.info("查询部门: {}", id);
        log.info("查询部门结果: {}", deptService.findById(id));
        return Result.success(deptService.findById(id));
    }
    @PutMapping
    public Result update(@RequestBody Dept dept){
        if (dept.getId() == null) {
            throw new CustomException("部门id不能为空");
        }
        log.info("修改部门: {}", dept);
        int result = deptService.update(dept);
        log.info("修改部门结果: {}", result);
        return result==1?Result.success("修改成功"):Result.error("修改失败");
    }

}
