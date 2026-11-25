package com.lzh.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BusinessDto {
    private Integer businessId;
    private String name;
    private String phone;
    private Integer status;
    private Integer subject; // 意向学科，公海池列表按它筛选
    private String assignName;
    private Integer page;
    private Integer pageSize;
}
