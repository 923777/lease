package com.atguigu.lease.web.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.atguigu.lease.model.entity.LeaseAgreement;
import com.atguigu.lease.web.admin.service.LeaseAgreementService;
import com.atguigu.lease.web.admin.mapper.LeaseAgreementMapper;
import org.springframework.stereotype.Service;

/**
* @author HP
* @description 针对表【lease_agreement(租约信息表)】的数据库操作Service实现
* @createDate 2024-10-30 18:33:58
*/
@Service
public class LeaseAgreementServiceImpl extends ServiceImpl<LeaseAgreementMapper, LeaseAgreement>
    implements LeaseAgreementService{

}




