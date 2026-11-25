package com.lzh.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lzh.dto.UserDto;
import com.lzh.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Mapper
@Service
public interface UserMapper extends BaseMapper<User> {
    public IPage<User> findByPage(IPage<User> page,UserDto userDto);
//    public long findByTotal(UserDto userDto);

    public List<User> findByLabel(String userDto);
    public List<User> findByDeptId(Integer userDto);

    User findByUsername(User user);
}
