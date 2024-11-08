package com.atguigu.lease.web.admin.service.impl;

import com.atguigu.lease.web.admin.vo.system.user.SystemUserItemVo;
import com.atguigu.lease.web.admin.vo.system.user.SystemUserQueryVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.atguigu.lease.model.entity.SystemUser;
import com.atguigu.lease.web.admin.service.SystemUserService;
import com.atguigu.lease.web.admin.mapper.SystemUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
* @author HP
* @description 针对表【system_user(员工信息表)】的数据库操作Service实现
* @createDate 2024-10-30 18:33:59
*/
@Service
public class SystemUserServiceImpl extends ServiceImpl<SystemUserMapper, SystemUser>
    implements SystemUserService{@Autowired
    private SystemUserMapper systemUserMapper;

    @Override
    public IPage<SystemUserItemVo> pageByQueryVo(Page<SystemUserItemVo> systemUserItemVoPage, SystemUserQueryVo queryVo) {

        return systemUserMapper.pageByQueryVo(systemUserItemVoPage,queryVo);
    }

    @Override
    public SystemUserItemVo getSystemUserItemVoById(Long id) {

        return  systemUserMapper.getSystemUserItemVoById(id);
    }
}




