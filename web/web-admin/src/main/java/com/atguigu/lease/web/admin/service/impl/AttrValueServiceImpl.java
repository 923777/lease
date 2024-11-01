package com.atguigu.lease.web.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.atguigu.lease.model.entity.AttrValue;
import com.atguigu.lease.web.admin.service.AttrValueService;
import com.atguigu.lease.web.admin.mapper.AttrValueMapper;
import org.springframework.stereotype.Service;

/**
* @author HP
* @description 针对表【attr_value(房间基本属性值表)】的数据库操作Service实现
* @createDate 2024-10-30 18:33:58
*/
@Service
public class AttrValueServiceImpl extends ServiceImpl<AttrValueMapper, AttrValue>
    implements AttrValueService{

}




