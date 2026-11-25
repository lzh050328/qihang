package com.lzh.controller;

import com.lzh.common.Result;
import com.lzh.dto.CustomerDto;
import com.lzh.pojo.Customer;
import com.lzh.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customers")
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @GetMapping
    public Result findByPage(CustomerDto customerDto){
        return Result.success(customerService.findByPage(customerDto));
    }
    @PostMapping
    public Result add(@RequestBody Customer customer){
        // 走 service 的 add（里面有手机号重复校验），并按结果返回成功/失败
        return customerService.add(customer) > 0 ? Result.success() : Result.error("添加失败");
    }
    @GetMapping("/{id}")
    public Result findById(@PathVariable Integer id){
        return Result.success(customerService.findById(id));
    }
    @PutMapping
    public Result edit(@RequestBody Customer customer){
        return customerService.edit(customer) ? Result.success() : Result.error("修改失败");
    }
}
