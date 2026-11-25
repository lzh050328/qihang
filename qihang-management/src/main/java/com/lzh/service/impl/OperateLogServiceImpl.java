package com.lzh.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.spring.service.impl.ServiceImpl;
import com.lzh.common.DataPage;
import com.lzh.dto.LogDto;
import com.lzh.mapper.OperateLogMapper;
import com.lzh.pojo.OperateLog;
import com.lzh.service.OperateLogService;
import org.springframework.stereotype.Service;

@Service
public class OperateLogServiceImpl extends ServiceImpl<OperateLogMapper, OperateLog> implements OperateLogService {

    @Override
    public DataPage<OperateLog> findByPage(LogDto logDto) {
        int pageNo = (logDto.getPage() == null || logDto.getPage() < 1) ? 1 : logDto.getPage();
        int pageSize = (logDto.getPageSize() == null || logDto.getPageSize() < 1) ? 10 : logDto.getPageSize();
        IPage<OperateLog> page = new Page<>(pageNo, pageSize);
        IPage<OperateLog> result = baseMapper.findByPage(page, logDto);
        return new DataPage<>(result.getTotal(), result.getRecords());
    }
}
