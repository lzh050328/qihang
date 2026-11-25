package com.lzh.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.spring.service.IService;
import com.lzh.common.DataPage;
import com.lzh.dto.UserDto;
import com.lzh.pojo.User;
import com.lzh.vo.LoginVo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService extends IService<User> {


    DataPage<User> findByPage(UserDto userDto);


    List<User> findByLabel(String roleLabel);

    List<User> findByDeptId(Integer deptId);

    LoginVo login(User user);
}
