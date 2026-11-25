package com.lzh.pojo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private String remark;
    private Integer gender;
    private String password = "123456";
    private Integer roleId;
    private Integer status;
    private String username;
    private Integer deptId;
    private String image;
    private String email;
    private Integer id;
    private String name;
    private String  phone;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime updateTime;
    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;
    @TableField(exist = false)
    private String deptName;
    @TableField(exist = false)
    private String roleName;
    @TableField(exist = false)
    private String roleLabel;
    @TableField(exist = false)
    private String token;

}
