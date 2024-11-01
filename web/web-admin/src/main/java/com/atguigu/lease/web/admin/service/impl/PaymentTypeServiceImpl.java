package com.atguigu.lease.web.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.atguigu.lease.model.entity.PaymentType;
import com.atguigu.lease.web.admin.service.PaymentTypeService;
import com.atguigu.lease.web.admin.mapper.PaymentTypeMapper;
import org.springframework.stereotype.Service;

/**
* @author HP
* @description 针对表【payment_type(支付方式表)】的数据库操作Service实现
* @createDate 2024-10-30 18:33:58
*/
@Service
public class PaymentTypeServiceImpl extends ServiceImpl<PaymentTypeMapper, PaymentType>
    implements PaymentTypeService{

}




