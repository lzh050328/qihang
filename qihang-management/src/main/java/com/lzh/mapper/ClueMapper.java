package com.lzh.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lzh.dto.ClueDto;
import com.lzh.pojo.Clue;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ClueMapper extends BaseMapper<Clue> {
    IPage<Clue> findByPage(IPage<Clue> page, ClueDto clueDto);

    Clue findById(Integer id);

    IPage<Clue> findPoll(IPage<Clue> page, Clue clue);
}
