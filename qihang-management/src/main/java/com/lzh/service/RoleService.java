package com.lzh.service;

import com.baomidou.mybatisplus.spring.service.IService;
import com.lzh.common.DataPage;
import com.lzh.common.Result;
import com.lzh.dto.RoleDto;
import com.lzh.pojo.Role;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RoleService extends IService<Role> {
    DataPage<Role> getRolesByPage(RoleDto roleDto);

//    Integer deleteRole(Integer id);

    Integer addRole(Role role);

//    Role  findRoleById(Integer id);

//    Integer updateRole(Role role);

//    List<Role> findRoleList();
}
