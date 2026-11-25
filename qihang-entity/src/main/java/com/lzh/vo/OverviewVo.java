package com.lzh.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 首页概览：线索、商机各状态的统计结果
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OverviewVo {
    private Long clueTotal;              // 线索总数
    private Long clueWaitAllot;          // 待分配
    private Long clueWaitFollow;         // 待跟进
    private Long clueFollowing;          // 跟进中
    private Long clueFalse;              // 伪线索
    private Long clueConvertBusiness;    // 转为商机
    private Long businessTotal;          // 商机总数
    private Long businessWaitAllot;      // 待分配
    private Long businessWaitFollow;     // 待跟进
    private Long businessFollowing;      // 跟进中
    private Long businessFalse;          // 回收
    private Long businessConvertCustomer; // 转客户
}
