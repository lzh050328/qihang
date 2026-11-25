package com.lzh.pojo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.lzh.deserializer.LenientLocalDateTimeDeserializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 线索实体类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("clue")
public class Clue {
    @TableId
    private Integer id; // 线索id, 主键
    private String phone; // 手机号
    private Integer channel; // 渠道来源，1:线上活动, 2:推广介绍
    private Integer activityId; // 活动信息，关联活动的id
    private String name; // 客户姓名
    private Integer gender; // 性别，1:男, 2:女
    private Integer age; // 年龄
    private String wechat; // 微信号
    private String qq; // qq号
    private Integer userId; // 归属人id，关联用户id
    private Integer status; // 线索状态，1:待分配, 2:跟进中, 3:伪线索, 4:转为商机
    private Integer subject; // 意向学科，1:ai智能应用开发(java), 2:ai大模型开发(python)，3:ai鸿蒙开发，4:ai大数据，5:ai嵌入式，6:ai测试，7:ai运维
    private Integer level; // 意向等级, 1:近期学习、2:打算学习(考虑中)、3:进行了解、4:打酱油
    // 加：前端日期控件传的是 2026-10-22T00:00:00（ISO），这里两种格式都接收
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @JsonDeserialize(using = LenientLocalDateTimeDeserializer.class)
    private LocalDateTime nextTime; // 下次跟进时间
    @TableField(fill = FieldFill.INSERT)
    @JsonDeserialize(using = LenientLocalDateTimeDeserializer.class)
    private LocalDateTime createTime; // 创建时间
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @JsonDeserialize(using = LenientLocalDateTimeDeserializer.class)
    private LocalDateTime updateTime; // 修改时间
    @TableField(exist = false)
    private String assignName;
    // 跟进记录：跟进时前端随线索一起提交，不是 clue 表的列，靠同名属性拷进 clue_track_record
    @TableField(exist = false)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String record;
    @TableField(exist = false)
    private List<ClueTrackRecord> trackRecords;
    @TableField(exist = false)
    private String activityName;
    @TableField(exist = false)
    private Integer page;
    @TableField(exist = false)
    private Integer pageSize;
}
