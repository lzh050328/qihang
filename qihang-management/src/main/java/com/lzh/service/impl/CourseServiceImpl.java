package com.lzh.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.spring.service.impl.ServiceImpl;
import com.lzh.common.DataPage;
import com.lzh.dto.CourseDto;
import com.lzh.handler.CustomException;
import com.lzh.mapper.CourseMapper;
import com.lzh.pojo.Course;
import com.lzh.service.CourseService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements CourseService {
    private final CourseMapper courseMapper;

    public CourseServiceImpl(CourseMapper courseMapper) {
        this.courseMapper = courseMapper;
    }

    @Override
    public DataPage<Course> findByPage(CourseDto courseDto) {
        int pageNo = (courseDto.getPage() == null || courseDto.getPage() < 1) ? 1 : courseDto.getPage();
        int pageSize = (courseDto.getPageSize() == null || courseDto.getPageSize() < 1) ? 10 : courseDto.getPageSize();
        IPage<Course> page = new Page<>(pageNo, pageSize);
        LambdaQueryWrapper<Course> lqw = Wrappers.lambdaQuery(Course.class);
        lqw.like(courseDto.getName() != null && courseDto.getName().length() > 0, Course::getName, courseDto.getName());
        lqw.eq(courseDto.getSubject() != null , Course::getSubject, courseDto.getSubject());
        lqw.eq(courseDto.getTarget() != null , Course::getTarget, courseDto.getTarget());
        courseMapper.selectPage(page, lqw);

        return new DataPage<>(page.getTotal(), page.getRecords());

    }

    @Override
    public Integer add(Course course) {
        if (course.getName() == null || course.getName().length() == 0) {
            throw new CustomException("课程名称不能为空");
        }
        boolean result = courseMapper.exists(Wrappers.lambdaQuery(Course.class).eq(Course::getName, course.getName()));
        if(result) {
            throw new CustomException("课程名称已存在");
        }
        return save(course) ? 1 : 0;

    }

    @Override
    public List<Course> findBySubject(Integer subject) {
        LambdaQueryWrapper<Course> lqw = Wrappers.lambdaQuery(Course.class);
        lqw.eq(subject != null, Course::getSubject, subject);
        return  courseMapper.selectList(lqw);
    }
}
