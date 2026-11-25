package com.lzh.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActivityDto {
    private Integer channel;
    private Integer type;
    private Integer page;
    private Integer pageSize;
}
