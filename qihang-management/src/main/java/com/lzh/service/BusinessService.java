package com.lzh.service;

import com.baomidou.mybatisplus.spring.service.IService;
import com.lzh.common.DataPage;
import com.lzh.dto.BusinessDto;
import com.lzh.pojo.Business;
import org.springframework.stereotype.Service;


public interface BusinessService extends IService<Business> {
    DataPage<Business> findByPage(BusinessDto businessDto);




    Object assignUser(Integer businessId, Integer userId);

    boolean back(Integer id);

    boolean toCustomer(Integer id);

    Business findById(Integer id);

    boolean follow(Business business);

    DataPage<Business> findPool(BusinessDto businessDto);
}
