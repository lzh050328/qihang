package com.lzh.service;

import com.baomidou.mybatisplus.spring.service.IService;
import com.lzh.common.DataPage;
import com.lzh.common.Result;
import com.lzh.dto.ActivityDto;
import com.lzh.pojo.Activity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ActivityService extends IService<Activity> {
    DataPage<Activity> findByPage(ActivityDto activityDto);


    List<Activity> getByType(Integer type);
}
