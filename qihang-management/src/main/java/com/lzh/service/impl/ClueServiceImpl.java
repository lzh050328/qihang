package com.lzh.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.convert.Convert;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.spring.service.impl.ServiceImpl;
import com.lzh.common.DataHolder;
import com.lzh.common.DataPage;
import com.lzh.dto.ClueDto;
import com.lzh.handler.CustomException;
import com.lzh.mapper.ClueMapper;
import com.lzh.pojo.Business;
import com.lzh.pojo.Clue;
import com.lzh.pojo.ClueTrackRecord;
import com.lzh.service.BusinessService;
import com.lzh.service.ClueService;
import com.lzh.service.ClueTrackRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ClueServiceImpl extends ServiceImpl<ClueMapper, Clue> implements ClueService {
    private final ClueTrackRecordService clueTrackRecordService;
    @Autowired
    private DataHolder<Map<String, Object>> dataHolder;


    public ClueServiceImpl(ClueTrackRecordService clueTrackRecordService) {
        this.clueTrackRecordService = clueTrackRecordService;
    }

    @Override
    public DataPage<Clue> findByPage(ClueDto clueDto) {
        // 改：先给 page/pageSize 兜底。?page=&pageSize= 这种空串会被 Spring 转成 null，
        //     直接 new Page<>(null, null) 会拆箱 NPE（表现为"服务器正忙"）
        int pageNo = (clueDto.getPage() == null || clueDto.getPage() < 1) ? 1 : clueDto.getPage();
        int pageSize = (clueDto.getPageSize() == null || clueDto.getPageSize() < 1) ? 10 : clueDto.getPageSize();
        IPage<Clue> page = new Page<>(pageNo, pageSize);
        IPage<Clue> result = baseMapper.findByPage(page, clueDto);

        return new DataPage<>(result.getTotal(), result.getRecords());
    }

    @Override
    public Integer add(Clue clue) {
        // 改：clue.phone 上有唯一索引，重复提交会抛 DuplicateKeyException，
        //     全局异常处理器只会返回"服务器正忙"，前端看不出原因 —— 这里先查一次给出明确提示
        Clue exist = getOne(Wrappers.lambdaQuery(Clue.class).eq(Clue::getPhone, clue.getPhone()));
        if (exist != null) {
            throw new CustomException("该手机号已存在线索，请勿重复添加");
        }
        try {
            return save(clue) ? 1 : 0;
        } catch (DuplicateKeyException e) {
            // 并发下同时提交会绕过上面的查询，唯一索引才是最终防线，这里兜住
            throw new CustomException("该手机号已存在线索，请勿重复添加");
        }
    }

    @Override
    public int assign(Integer clueId, Integer userId) {
        Clue clue = getById(clueId);
        clue.setUserId(userId);
        clue.setStatus(2);
        return saveOrUpdate(clue) ? 1 : 0;
    }

    @Override
    public Clue findById(Integer id) {
        Clue clue = getBaseMapper().findById(id);
        return clue;
    }

    @Override
    public void follow(Clue clue) {
        clue.setStatus(3);
        saveOrUpdate(clue);
        ClueTrackRecord ctr = new ClueTrackRecord();
        BeanUtil.copyProperties(clue,ctr);
        ctr.setId(null);
        ctr.setClueId(clue.getId());
        // 跟进人就是当前登录人（从线程中转站取），不能依赖请求体传的 userId —— 那张表 user_id 是 NOT NULL
        Map<String, Object> claims = dataHolder.get();
        if (claims != null && claims.get("id") != null) {
            ctr.setUserId(Convert.toInt(claims.get("id")));
        }
        // 跟进类型：1 正常跟进（伪线索那条走 falseClue，设的是 0）
        ctr.setType(1);
        clueTrackRecordService.saveOrUpdate(ctr);
    }

    @Override
    public void falseClue(Integer id, ClueDto clueDto) {
        Clue clue = getById(id);
        clue.setStatus(4);
        saveOrUpdate(clue);
        ClueTrackRecord ctr = new ClueTrackRecord();
        BeanUtil.copyProperties(clue,ctr);
        ctr.setId(null);
        ctr.setType(0);
        ctr.setClueId(clue.getId());
        ctr.setFalseReason(clueDto.getReason());
        ctr.setRecord(clueDto.getRemark());
        clueTrackRecordService.saveOrUpdate(ctr);
    }
    @Autowired
    public BusinessService businessService;
    @Override
    public void toBusiness(Integer id) {
        Clue clue = getById(id);
        clue.setStatus(5);
        saveOrUpdate(clue);
        Business business = new Business();
        BeanUtil.copyProperties(clue, business);
        business.setId(null);
        business.setClueId(clue.getId());
        business.setStatus(1);
        business.setUserId(null);
        businessService.saveOrUpdate(business);
    }

    @Override
    public DataPage<Clue> findPoll(Clue clue) {
        int pageNo = (clue.getPage() == null || clue.getPage() < 1) ? 1 : clue.getPage();
        int pageSize = (clue.getPageSize() == null || clue.getPageSize() < 1) ? 10 : clue.getPageSize();
        IPage<Clue> page = new Page<>(pageNo, pageSize);
        IPage<Clue> result = baseMapper.findPoll(page, clue);
        return new DataPage<>(result.getTotal(), result.getRecords());

    }
}
