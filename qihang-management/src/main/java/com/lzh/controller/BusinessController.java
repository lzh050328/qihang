package com.lzh.controller;

import com.lzh.common.Result;
import com.lzh.dto.BusinessDto;
import com.lzh.pojo.Business;
import com.lzh.service.BusinessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/businesses")
public class BusinessController {
    @Autowired
    private BusinessService businessService;
    @GetMapping
    public Result findByPage(BusinessDto businessDto){
        return Result.success(businessService.findByPage(businessDto));
    }
    @PostMapping
    public Result updateStatus(@RequestBody Business business){
        // 改：走 service 的 updateStatus（里面有校验），并按结果返回成功/失败
        //     （原来 Result.success(saveOrUpdate(...)) 失败也会返回 code=1、data=false，且没有校验）
        return businessService.save(business) ? Result.success() : Result.error("更新失败");
    }
    @PutMapping("/assign/{businessId}/{userId}")
    public Result assignUser(@PathVariable Integer businessId, @PathVariable Integer userId){
        return Result.success(businessService.assignUser(businessId, userId));
    }
    @PutMapping("/back/{id}")
    public Result back(@PathVariable Integer id){
        // 踢回公海：status 置 4(回收) + 清空归属人，按结果返回成功/失败
        return businessService.back(id) ? Result.success() : Result.error("踢回公海失败");
    }
    @PostMapping("/toCustomer/{id}")
    public Result toCustomer(@PathVariable Integer id){
        // 转换客户：status 置 5(客户) + 清空归属人，按结果返回成功/失败
        return businessService.toCustomer(id) ? Result.success() : Result.error("转换客户失败");
    }
    @PutMapping
    public Result follow(@RequestBody Business business){
        // 跟进商机：更新商机(nextTime 等) + 落一条跟进记录，按结果返回成功/失败
        return businessService.follow(business) ? Result.success() : Result.error("跟进失败");
    }
    @GetMapping("/pool")
    public Result findPool(BusinessDto businessDto){
        // 公海池：字面量路径比 /{id} 更精确，Spring 会优先匹配到这里
        return Result.success(businessService.findPool(businessDto));
    }
    @GetMapping("/{id}")
    public Result findById(@PathVariable Integer id){
        return Result.success(businessService.findById(id));
    }
}
