package com.lzh.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.RequestParam;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeptDto {
    private String name;
    private Integer status ;

    private Integer page = 1;

    private Integer pageSize = 10;
}
