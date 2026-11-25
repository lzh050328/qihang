package com.lzh.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class CourseDto {
    private String name;
    private Integer subject;
    private Integer target;
    private Integer page = 1;
    private Integer pageSize = 10;

}
