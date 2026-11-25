package com.lzh.controller;

import com.lzh.common.DataPage;
import com.lzh.common.Result;
import com.lzh.dto.CourseDto;
import com.lzh.pojo.Course;
import com.lzh.service.CourseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/courses")
public class CourseController {
    @Autowired
    private CourseService courseService;
    @GetMapping
    public Result findByPage(CourseDto courseDto){
        log.info("分页查询课程: {}", courseDto);

        return Result.success(courseService.findByPage(courseDto));
    }
    @DeleteMapping("/{id}")
    public Result deleteById(@PathVariable Integer id){
        log.info("删除课程: {}", id);
        return courseService.removeById(id) ? Result.success() : Result.error("删除失败");

    }
    @PostMapping
    public Result add(@RequestBody Course course){
        log.info("添加课程");
        if (courseService.add(course) > 0) {
            return Result.success("添加成功");
        }
        return Result.error("添加失败");
    }
    @GetMapping("/{id}")
    public Result getById(@PathVariable Integer id){
        log.info("查询课程: {}", id);
        return Result.success(courseService.getById(id));
    }
    @PutMapping
    public Result update(@RequestBody Course course){
        log.info("修改课程");
        if (courseService.updateById(course)) {
            return Result.success("修改成功");
        }
        return Result.error("修改失败");
    }
    @GetMapping("/list")
    public Result findAll(){
        return Result.success(courseService.list());
    }
    @GetMapping("subject/{subject}")
    public Result findBySubject(@PathVariable Integer subject){
        return Result.success(courseService.findBySubject(subject));
    }
}
