package com.lzh.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lzh.dto.CourseDto;
import com.lzh.dto.UserDto;
import com.lzh.pojo.Course;
import com.lzh.pojo.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CourseMapper extends BaseMapper<Course> {

}
