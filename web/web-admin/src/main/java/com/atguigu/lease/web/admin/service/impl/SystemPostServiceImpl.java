package com.atguigu.lease.web.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.atguigu.lease.model.entity.SystemPost;
import com.atguigu.lease.web.admin.service.SystemPostService;
import com.atguigu.lease.web.admin.mapper.SystemPostMapper;
import org.springframework.stereotype.Service;

/**
* @author HP
* @description 针对表【system_post(岗位信息表)】的数据库操作Service实现
* @createDate 2024-10-30 18:33:59
*/
@Service
public class SystemPostServiceImpl extends ServiceImpl<SystemPostMapper, SystemPost>
    implements SystemPostService{

}




