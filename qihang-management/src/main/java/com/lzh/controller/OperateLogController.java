package com.lzh.controller;

import com.lzh.common.Result;
import com.lzh.dto.LogDto;
import com.lzh.service.OperateLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/logs")
public class OperateLogController {
    @Autowired
    private OperateLogService operateLogService;

    @GetMapping
    public Result findByPage(LogDto logDto){
        return Result.success(operateLogService.findByPage(logDto));
    }
}
