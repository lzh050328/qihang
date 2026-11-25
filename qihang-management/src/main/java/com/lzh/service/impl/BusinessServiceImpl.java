package com.lzh.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.BeanUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.spring.service.impl.ServiceImpl;
import com.lzh.common.DataPage;
import com.lzh.dto.BusinessDto;
import com.lzh.handler.CustomException;
import com.lzh.mapper.BusinessMapper;
import com.lzh.pojo.Business;
import com.lzh.pojo.BusinessTrackRecord;
import com.lzh.pojo.Customer;
import com.lzh.service.BusinessService;
import com.lzh.service.BusinessTrackRecordService;
import com.lzh.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BusinessServiceImpl extends ServiceImpl<BusinessMapper, Business> implements BusinessService {
    @Autowired
    private CustomerService customerService;
    @Autowired
    private BusinessTrackRecordService businessTrackRecordService;

    @Override
    public DataPage<Business> findByPage(BusinessDto businessDto) {
        int pageNo = (businessDto.getPage() == null || businessDto.getPage() < 1) ? 1 : businessDto.getPage();
        int pageSize = (businessDto.getPageSize() == null || businessDto.getPageSize() < 1) ? 10 : businessDto.getPageSize();
        IPage<Business> page = new Page<>(pageNo, pageSize);
        IPage<Business> result = baseMapper.findByPage(page, businessDto);
        return new DataPage<>(result.getTotal(), result.getRecords());
    }

    @Override
    public Object assignUser(Integer businessId, Integer userId) {
        // 加：商机分配（PUT /businesses/assign/{businessId}/{userId}），原来这里只有 return null
        Business business = getById(businessId);
        if (business == null) {
            throw new CustomException("商机不存在: id=" + businessId);
        }
        business.setUserId(userId);
        business.setStatus(2);   // 分配后进入"待跟进"（按你 Business 实体里的状态字典）
        return updateById(business);
    }

    @Override
    public boolean back(Integer id) {
        Business business = getById(id);
        if (business == null) {
            throw new CustomException("商机不存在: id=" + id);
        }
        if (business.getStatus() == 5) {
            throw new CustomException("已转为客户的商机不能踢回公海");
        }
        business.setStatus(4);   // 回收
        // userId 置空：updateById 会跳过 null 字段，必须用 UpdateWrapper 显式 set；
        // 用 update(entity, wrapper) 而不是 update(null, wrapper)，是为了让 updateTime 的自动填充仍然生效
        return update(business, Wrappers.<Business>lambdaUpdate()
                .eq(Business::getId, id)
                .set(Business::getUserId, null));
    }

    @Override
    public boolean toCustomer(Integer id) {
        LambdaQueryWrapper<Business> lqw = Wrappers.lambdaQuery();
        lqw.eq(Business::getId, id);
        Business business = getOne(lqw);
        Customer customer = new Customer();
        BeanUtil.copyProperties(business, customer);
        customer.setBusinessId(id);

        return customerService.save(customer);

    }

    @Override
    public Business findById(Integer id) {
        return getBaseMapper().findById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean follow(Business business) {
        if (business == null || business.getId() == null) {
            throw new CustomException("商机id不能为空");
        }
        business.setStatus(3);   // 跟进中
        updateById(business);

        // 跟进记录：前端把 keyItems/trackStatus/record 和商机字段一起放在请求体里传过来
        BusinessTrackRecord ctr = new BusinessTrackRecord();
        ctr.setBusinessId(business.getId());
        ctr.setUserId(business.getUserId());
        ctr.setTrackStatus(business.getTrackStatus());
        ctr.setRecord(business.getRecord());
        ctr.setNextTime(business.getNextTime());
        // 前端传数组，key_items 存字符串：List.toString() 出来就是 "[课程, 时间]"，和 findById 返回的格式一致
        ctr.setKeyItems(business.getKeyItems() == null ? null : business.getKeyItems().toString());
        return businessTrackRecordService.save(ctr);
    }

    @Override
    public DataPage<Business> findPool(BusinessDto businessDto) {
        businessDto.setStatus(4);   // 公海池只放"回收"状态的商机（样例数据里池子里的 3 条都是 status=4）
        return findByPage(businessDto);
    }
}
