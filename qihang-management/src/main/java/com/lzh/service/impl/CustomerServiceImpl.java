package com.lzh.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.spring.service.impl.ServiceImpl;
import com.lzh.common.DataPage;
import com.lzh.dto.CustomerDto;
import com.lzh.handler.CustomException;
import com.lzh.mapper.CustomerMapper;
import com.lzh.pojo.Customer;
import com.lzh.service.CustomerService;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl extends ServiceImpl<CustomerMapper, Customer> implements CustomerService {

    @Override
    public DataPage<Customer> findByPage(CustomerDto customerDto) {
        int pageNo = (customerDto.getPage() == null || customerDto.getPage() < 1) ? 1 : customerDto.getPage();
        int pageSize = (customerDto.getPageSize() == null || customerDto.getPageSize() < 1) ? 10 : customerDto.getPageSize();
        IPage<Customer> page = new Page<>(pageNo, pageSize);
        IPage<Customer> result = baseMapper.findByPage(page, customerDto);
        return new DataPage<>(result.getTotal(), result.getRecords());
    }

    @Override
    public Integer add(Customer customer) {
        // phone 上有唯一索引，重复提交会抛 DuplicateKeyException，
        // 全局异常处理器只会返回"服务器正忙"，前端看不出原因 —— 这里先查一次给出明确提示
        Customer exist = getOne(Wrappers.lambdaQuery(Customer.class).eq(Customer::getPhone, customer.getPhone()));
        if (exist != null) {
            throw new CustomException("该手机号已存在客户，请勿重复添加");
        }
        try {
            return save(customer) ? 1 : 0;
        } catch (DuplicateKeyException e) {
            // 并发下同时提交会绕过上面的查询，唯一索引才是最终防线，这里兜住
            throw new CustomException("该手机号已存在客户，请勿重复添加");
        }
    }

    @Override
    public Customer findById(Integer id) {
        return getById(id);
    }

    @Override
    public boolean edit(Customer customer) {
        if (customer == null || customer.getId() == null) {
            throw new CustomException("客户id不能为空");
        }
        // 创建时间只由 INSERT 时的自动填充负责，前端传上来的值不能覆盖真实创建时间
        customer.setCreateTime(null);
        return updateById(customer);
    }
}
