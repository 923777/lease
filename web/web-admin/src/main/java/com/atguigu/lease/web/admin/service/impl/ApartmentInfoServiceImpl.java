package com.atguigu.lease.web.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.atguigu.lease.model.entity.ApartmentInfo;
import com.atguigu.lease.web.admin.service.ApartmentInfoService;
import com.atguigu.lease.web.admin.mapper.ApartmentInfoMapper;
import org.springframework.stereotype.Service;

/**
* @author HP
* @description 针对表【apartment_info(公寓信息表)】的数据库操作Service实现
* @createDate 2024-10-30 18:33:58
*/
@Service
public class ApartmentInfoServiceImpl extends ServiceImpl<ApartmentInfoMapper, ApartmentInfo>
    implements ApartmentInfoService{

}




