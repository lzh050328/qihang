package com.lzh.service;

import com.baomidou.mybatisplus.spring.service.IService;
import com.lzh.common.DataPage;
import com.lzh.dto.CustomerDto;
import com.lzh.pojo.Customer;

public interface CustomerService extends IService<Customer> {

    DataPage<Customer> findByPage(CustomerDto customerDto);

    Integer add(Customer customer);

    Customer findById(Integer id);

    boolean edit(Customer customer);
}
