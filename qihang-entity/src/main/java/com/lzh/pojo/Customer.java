package com.lzh.pojo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import jdk.jfr.Name;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Customer {
    private Integer id; // 客户ID, 主键
    private String phone; // 手机号
    private Integer channel; // 渠道来源，1:线上活动, 2:推广介绍
    private String name; // 客户姓名
    private Integer gender; // 性别，1:男, 2:女
    private Integer age; // 年龄
    private String wechat; // 微信号
    private String qq; // QQ号
    private Integer degree; // 学历, 1:高中、2:中专、3:大专、4:本科、5:硕士、6:博士、7:其他
    private Integer jobStatus; // 在职情况, 1: 在职, 0: 离职
    private Integer subject; // 意向学科，1:AI智能应用开发(java), 2:AI大模型开发(python)，3:AI鸿蒙开发，4:AI大数据，5:AI嵌入式，6:AI测试，7:AI运维
    private Integer courseId; // 课程ID
    private Integer businessId; // 商机ID
    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime; // 创建时间
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime updateTime; // 修改时间
    @TableField(exist = false)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String courseName; // 意向课程名称，列表查询时关联课程表带出来
}