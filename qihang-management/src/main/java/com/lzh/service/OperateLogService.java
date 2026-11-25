package com.lzh.service;

import com.baomidou.mybatisplus.spring.service.IService;
import com.lzh.common.DataPage;
import com.lzh.dto.LogDto;
import com.lzh.pojo.OperateLog;

public interface OperateLogService extends IService<OperateLog> {
    /*
     * 分页查询操作日志
     * @param logDto
     * @return
     */
    DataPage<OperateLog> findByPage(LogDto logDto);
}
