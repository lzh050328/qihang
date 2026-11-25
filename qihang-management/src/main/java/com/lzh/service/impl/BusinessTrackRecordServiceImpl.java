package com.lzh.service.impl;

import com.baomidou.mybatisplus.spring.service.impl.ServiceImpl;
import com.lzh.mapper.BusinessTrackRecordMapper;
import com.lzh.pojo.BusinessTrackRecord;
import com.lzh.service.BusinessTrackRecordService;
import org.springframework.stereotype.Service;

@Service
public class BusinessTrackRecordServiceImpl extends ServiceImpl<BusinessTrackRecordMapper, BusinessTrackRecord> implements BusinessTrackRecordService {
}
