package com.youtube.ecommerce.service.Impl;

import com.youtube.ecommerce.dao.RoleDao;
import com.youtube.ecommerce.dto.RoleDto;
import com.youtube.ecommerce.entity.Role;
import com.youtube.ecommerce.mapper.MapperUtil;
import com.youtube.ecommerce.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private MapperUtil mapperUtil;

    public RoleDto createNewRole(RoleDto dto) {
        Role savedRole=roleDao.findById(dto.getRoleName()).get();
        roleDao.save(savedRole);
        return mapperUtil.convert(savedRole,new RoleDto());
    }
}
