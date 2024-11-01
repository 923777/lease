package com.atguigu.lease.web.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.atguigu.lease.model.entity.RoomInfo;
import com.atguigu.lease.web.admin.service.RoomInfoService;
import com.atguigu.lease.web.admin.mapper.RoomInfoMapper;
import org.springframework.stereotype.Service;

/**
* @author HP
* @description 针对表【room_info(房间信息表)】的数据库操作Service实现
* @createDate 2024-10-30 18:33:59
*/
@Service
public class RoomInfoServiceImpl extends ServiceImpl<RoomInfoMapper, RoomInfo>
    implements RoomInfoService{

}




