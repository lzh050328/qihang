package com.lzh.pojo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor

public class ClueTrackRecord {
    private Integer id;
    private Integer clueId;
    private Integer userId;
    private Integer subject;
    private Integer level;
    private String record;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime nextTime;
    private Integer type;
    private Integer falseReason;
    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;
    // 扩展 - 跟进人姓名，关联 user 表查出来，不是 clue_track_record 的列
    @TableField(exist = false)
    private String assignName;
}
