package com.atguigu.lease.web.admin.service.impl;

import com.atguigu.lease.web.admin.vo.agreement.AgreementQueryVo;
import com.atguigu.lease.web.admin.vo.agreement.AgreementVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.atguigu.lease.model.entity.LeaseAgreement;
import com.atguigu.lease.web.admin.service.LeaseAgreementService;
import com.atguigu.lease.web.admin.mapper.LeaseAgreementMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
* @author HP
* @description 针对表【lease_agreement(租约信息表)】的数据库操作Service实现
* @createDate 2024-10-30 18:33:58
*/
@Service
public class LeaseAgreementServiceImpl extends ServiceImpl<LeaseAgreementMapper, LeaseAgreement>
    implements LeaseAgreementService{
    @Autowired
    private LeaseAgreementMapper leaseAgreementMapper;

    @Override
    public IPage<AgreementVo> selectByQueryVo(Page<AgreementVo> Page, AgreementQueryVo queryVo) {
        IPage<AgreementVo>  list =leaseAgreementMapper.selectByQueryVo(Page,queryVo);
        return list;
    }

    @Override
    public AgreementVo getAgreementById(Long id) {

        return leaseAgreementMapper.getAgreementById(id);
    }
}




