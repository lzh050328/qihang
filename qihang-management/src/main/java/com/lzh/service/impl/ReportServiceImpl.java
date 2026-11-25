package com.lzh.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lzh.pojo.Business;
import com.lzh.pojo.Clue;
import com.lzh.service.BusinessService;
import com.lzh.service.ClueService;
import com.lzh.service.ReportService;
import com.lzh.vo.OverviewVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    private ClueService clueService;
    @Autowired
    private BusinessService businessService;

    @Override
    public OverviewVo overview() {
        // ponytail: 12 次 count，首页概览这点数据量够用；真成瓶颈了再换 2 条 group by status 的统计 SQL
        OverviewVo vo = new OverviewVo();
        // 线索状态：1待分配 2待跟进 3跟进中 4伪线索 5转商机
        vo.setClueTotal(clueService.count());
        vo.setClueWaitAllot(countClue(1));
        vo.setClueWaitFollow(countClue(2));
        vo.setClueFollowing(countClue(3));
        vo.setClueFalse(countClue(4));
        vo.setClueConvertBusiness(countClue(5));
        // 商机状态：1待分配 2待跟进 3跟进中 4回收 5转客户
        vo.setBusinessTotal(businessService.count());
        vo.setBusinessWaitAllot(countBusiness(1));
        vo.setBusinessWaitFollow(countBusiness(2));
        vo.setBusinessFollowing(countBusiness(3));
        vo.setBusinessFalse(countBusiness(4));
        vo.setBusinessConvertCustomer(countBusiness(5));
        return vo;
    }

    private Long countClue(Integer status) {
        return clueService.count(Wrappers.<Clue>lambdaQuery().eq(Clue::getStatus, status));
    }

    private Long countBusiness(Integer status) {
        return businessService.count(Wrappers.<Business>lambdaQuery().eq(Business::getStatus, status));
    }
}
