package com.lzh.controller;

import com.lzh.common.Result;
import com.lzh.dto.ActivityDto;
import com.lzh.pojo.Activity;
import com.lzh.service.ActivityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/activities")
public class ActivityController {
    @Autowired
    private ActivityService activityService;

    @GetMapping
    public Result findByPage(ActivityDto activityDto){
        log.info("分页查询活动: {}", activityDto);
        return Result.success(activityService.findByPage(activityDto));

    }
    @DeleteMapping("/{id}")
    public Result deleteById(@PathVariable Integer id){
        log.info("删除活动: {}", id);
        if(activityService.removeById(id))
        return Result.success();
        return Result.error("删除失败");

    }
    @PostMapping
    public Result add(@RequestBody Activity activity){
        log.info("添加活动");
        return activityService.save(activity) ? Result.success() : Result.error("添加失败");
    }
    @GetMapping("/{id}")
    public Result getById(@PathVariable Integer id){
        log.info("查询活动: {}", id);
        return Result.success(activityService.getById(id));
    }

    @PutMapping
    public Result update(@RequestBody Activity activity){
        log.info("修改活动");
        return activityService.updateById(activity) ? Result.success() : Result.error("修改失败");
    }
    @GetMapping("/type/{type}")
    public Result getByType(@PathVariable Integer type){
        log.info("查询活动: {}", type);
        if(activityService.getByType(type) != null)
        return Result.success(activityService.getByType(type));
        return Result.error("查询失败");
    }
    @GetMapping("/list")
    public Result findAll(){
        return Result.success(activityService.list());
    }


}
