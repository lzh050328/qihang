package com.lzh.service;

import com.baomidou.mybatisplus.spring.service.IService;
import com.lzh.common.DataPage;
import com.lzh.common.Result;
import com.lzh.dto.CourseDto;
import com.lzh.pojo.Course;

import java.util.List;

public interface CourseService extends IService<Course> {
    DataPage<Course> findByPage(CourseDto courseDto);

    Integer add(Course course);

    List<Course> findBySubject(Integer subject);
}
