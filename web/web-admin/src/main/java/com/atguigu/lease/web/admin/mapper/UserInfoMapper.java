package com.atguigu.lease.web.admin.mapper;

import com.atguigu.lease.model.entity.UserInfo;
import com.atguigu.lease.web.admin.vo.user.UserInfoQueryVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
* @author HP
* @description 针对表【user_info(用户信息表)】的数据库操作Mapper
* @createDate 2024-10-30 18:33:59
* @Entity com.atguigu.lease.model.entity.UserInfo
*/
public interface UserInfoMapper extends BaseMapper<UserInfo> {

   IPage<UserInfo> pageByQuerVo(Page<UserInfo> userInfoPage, UserInfoQueryVo queryVo);
}




