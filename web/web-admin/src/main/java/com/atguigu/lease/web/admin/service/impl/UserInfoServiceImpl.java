package com.atguigu.lease.web.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.atguigu.lease.model.entity.UserInfo;
import com.atguigu.lease.web.admin.service.UserInfoService;
import com.atguigu.lease.web.admin.mapper.UserInfoMapper;
import org.springframework.stereotype.Service;

/**
* @author HP
* @description 针对表【user_info(用户信息表)】的数据库操作Service实现
* @createDate 2024-10-30 18:33:59
*/
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo>
    implements UserInfoService{

}




