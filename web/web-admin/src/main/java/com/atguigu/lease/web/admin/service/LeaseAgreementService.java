package com.atguigu.lease.web.admin.service;

import com.atguigu.lease.model.entity.LeaseAgreement;
import com.atguigu.lease.web.admin.vo.agreement.AgreementQueryVo;
import com.atguigu.lease.web.admin.vo.agreement.AgreementVo;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author HP
* @description 针对表【lease_agreement(租约信息表)】的数据库操作Service
* @createDate 2024-10-30 18:33:58
*/
public interface LeaseAgreementService extends IService<LeaseAgreement> {

    IPage<AgreementVo> selectByQueryVo(Page<AgreementVo> objectPage, AgreementQueryVo queryVo);

    AgreementVo getAgreementById(Long id);


}
