package com.lzh.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.spring.service.impl.ServiceImpl;
import com.lzh.common.DataPage;
import com.lzh.common.JwtUtil;
import com.lzh.dto.UserDto;
import com.lzh.handler.CustomException;
import com.lzh.mapper.UserMapper;
import com.lzh.pojo.User;
import com.lzh.service.UserService;
import com.lzh.vo.LoginVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    private final UserService userService;

    public UserServiceImpl(@Lazy UserService userService) {
        this.userService = userService;
    }

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public DataPage<User> findByPage(UserDto userDto) {
        int page = (userDto.getPage() == null || userDto.getPage() < 1) ? 1 : userDto.getPage();
        int pageSize = (userDto.getPageSize() == null || userDto.getPageSize() < 1) ? 10 : userDto.getPageSize();
        IPage<User> userPage = new Page<>(page, pageSize);

        IPage<User> result = getBaseMapper().findByPage(userPage, userDto);
        return new DataPage<>(userPage.getTotal(),userPage.getRecords());

    }

    @Override
    public List<User> findByLabel(String roleLabel) {
        return getBaseMapper().findByLabel(roleLabel);


    }

    @Override
    public List<User> findByDeptId(Integer deptId) {
        return getBaseMapper().findByDeptId(deptId);
    }

    @Override
    public LoginVo login(User user) {
        User  loginUser = getBaseMapper().findByUsername(user);
        if(loginUser == null){
            throw new CustomException("用户名或者密码错误");
        }

        String md5Password = DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
        if (!loginUser.getPassword().equals(md5Password)) {
            throw new CustomException("用户名或者密码错误");
        }
        if(loginUser.getStatus() == 0){
            throw new CustomException("用户已停用,请联系管理员");

        }
        LoginVo loginVo = new LoginVo();
        BeanUtils.copyProperties(loginUser, loginVo);

        // 生成 JWT 令牌，LoginInterceptor 校验的就是这个 token
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", loginUser.getId());
        claims.put("username", loginUser.getUsername());
        loginVo.setToken(jwtUtil.generateToken(claims));
        
        return loginVo;
    }


}
