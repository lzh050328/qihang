package com.lzh.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lzh.dto.LogDto;
import com.lzh.pojo.OperateLog;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OperateLogMapper extends BaseMapper<OperateLog> {
    IPage<OperateLog> findByPage(IPage<OperateLog> page, LogDto logDto);
}
