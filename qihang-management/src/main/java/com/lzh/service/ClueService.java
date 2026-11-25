package com.lzh.service;

import com.baomidou.mybatisplus.spring.service.IService;
import com.lzh.common.DataPage;
import com.lzh.dto.ClueDto;
import com.lzh.pojo.Clue;
import org.springframework.transaction.annotation.Transactional;

public interface ClueService  extends IService<Clue> {
    DataPage<Clue> findByPage(ClueDto clueDto);

    /** 新增线索（会校验手机号是否已存在） */
    Integer add(Clue clue);

    int assign(Integer clueId, Integer userId);

    Clue findById(Integer id);

    @Transactional(rollbackFor = Exception.class)
    void follow(Clue clue);

    @Transactional(rollbackFor = Exception.class)
    void falseClue(Integer id, ClueDto clueDto);

    @Transactional(rollbackFor = Exception.class)
    void toBusiness(Integer id);

    DataPage<Clue> findPoll(Clue clue);
}
