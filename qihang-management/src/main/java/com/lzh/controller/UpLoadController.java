package com.lzh.controller;

import com.lzh.common.OSSUtils;
import com.lzh.common.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class UpLoadController {
    @Autowired
    private OSSUtils ossUtils;
    @PostMapping("/upload")
    public Result upload(MultipartFile image) throws Exception {
        String url = ossUtils.upload(image);
        return Result.success(url);
    }

}
