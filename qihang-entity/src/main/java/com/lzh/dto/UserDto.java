package com.lzh.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private String name;
    private Integer status;
    private String  phone;
    private String email;
    private Integer deptId;
    private Integer page = 1;
    private Integer pageSize = 10;
    @TableField(exist = false)
    private Integer start;

}
