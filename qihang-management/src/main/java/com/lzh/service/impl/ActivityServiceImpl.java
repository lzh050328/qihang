package com.lzh.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.spring.service.impl.ServiceImpl;
import com.lzh.common.DataPage;
import com.lzh.dto.ActivityDto;
import com.lzh.mapper.ActivityMapper;
import com.lzh.pojo.Activity;
import com.lzh.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActivityServiceImpl extends ServiceImpl<ActivityMapper, Activity>  implements ActivityService {


    @Override
    public DataPage<Activity> findByPage(ActivityDto activityDto) {
        int pageNo = (activityDto.getPage() == null || activityDto.getPage() < 1) ? 1 : activityDto.getPage();
        int pageSize = (activityDto.getPageSize() == null || activityDto.getPageSize() < 1) ? 10 : activityDto.getPageSize();
        IPage<Activity> page = new Page<>(pageNo, pageSize);
        LambdaQueryWrapper<Activity> lqw = Wrappers.lambdaQuery(Activity.class);
        lqw.eq(activityDto.getChannel() != null, Activity::getChannel, activityDto.getChannel());
        lqw.eq(activityDto.getType() != null, Activity::getType, activityDto.getType());
        IPage<Activity> result = getBaseMapper().selectPage(page, lqw);
        return new DataPage<>(result.getTotal(), result.getRecords());
    }

    @Override
    public List<Activity> getByType(Integer type) {
        LambdaQueryWrapper<Activity> lqw = Wrappers.lambdaQuery(Activity.class);
        lqw.eq(type != null, Activity::getType, type);
        return getBaseMapper().selectList(lqw);

    }
}


