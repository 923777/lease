package com.atguigu.lease.web.admin.service;

import com.atguigu.lease.model.entity.UserInfo;
import com.atguigu.lease.web.admin.vo.user.UserInfoQueryVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author HP
* @description 针对表【user_info(用户信息表)】的数据库操作Service
* @createDate 2024-10-30 18:33:59
*/
public interface UserInfoService extends IService<UserInfo> {


//    IPage<UserInfo> pageByQuerVo(Page<UserInfo> userInfoPage, UserInfoQueryVo queryVo);
}
