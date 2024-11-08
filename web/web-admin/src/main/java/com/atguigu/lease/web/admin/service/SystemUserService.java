package com.atguigu.lease.web.admin.service;

import com.atguigu.lease.model.entity.SystemUser;
import com.atguigu.lease.web.admin.vo.system.user.SystemUserItemVo;
import com.atguigu.lease.web.admin.vo.system.user.SystemUserQueryVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author HP
* @description 针对表【system_user(员工信息表)】的数据库操作Service
* @createDate 2024-10-30 18:33:59
*/
public interface SystemUserService extends IService<SystemUser> {

    IPage<SystemUserItemVo> pageByQueryVo(Page<SystemUserItemVo> systemUserItemVoPage, SystemUserQueryVo queryVo);

    SystemUserItemVo getSystemUserItemVoById(Long id);
}
