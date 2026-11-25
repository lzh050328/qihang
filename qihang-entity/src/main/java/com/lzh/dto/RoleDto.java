package com.lzh.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleDto {
    private String name;
    private String label;
    private Integer page = 1;
    private Integer pageSize = 10;

}
