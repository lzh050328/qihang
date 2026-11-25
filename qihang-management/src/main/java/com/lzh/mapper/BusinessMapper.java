package com.lzh.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lzh.dto.BusinessDto;
import com.lzh.pojo.Business;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BusinessMapper  extends BaseMapper<Business> {
    IPage<Business> findByPage(IPage<Business> page, BusinessDto businessDto);

    Business findById(Integer id);
}
