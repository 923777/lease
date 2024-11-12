package com.atguigu.lease.web.app.service.impl;

import com.atguigu.lease.model.entity.*;
import com.atguigu.lease.model.enums.ItemType;
import com.atguigu.lease.web.app.mapper.*;
import com.atguigu.lease.web.app.service.LeaseAgreementService;
import com.atguigu.lease.web.app.vo.agreement.AgreementDetailVo;
import com.atguigu.lease.web.app.vo.agreement.AgreementItemVo;
import com.atguigu.lease.web.app.vo.graph.GraphVo;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author liubo
 * @description 针对表【lease_agreement(租约信息表)】的数据库操作Service实现
 * @createDate 2023-07-26 11:12:39
 */
@Service
public class LeaseAgreementServiceImpl extends ServiceImpl<LeaseAgreementMapper, LeaseAgreement>
        implements LeaseAgreementService {
    @Autowired LeaseAgreementMapper leaseAgreementMapper;
    @Autowired ApartmentInfoMapper apartmentInfoMapper;
    @Autowired RoomInfoMapper roomInfoMapper;
    @Autowired GraphInfoMapper graphInfoMapper;
    @Autowired LeaseTermMapper leaseTermMapper;
    @Autowired PaymentTypeMapper paymentTypeMapper;


    @Override
    public List<AgreementItemVo> listAgreementItemByPhone(String username) {
        return leaseAgreementMapper.listAgreementItemByPhone(username);
    }

    @Override
    public AgreementDetailVo getAgreementDetailById(Long id) {

        //1.查询租约信息
        LeaseAgreement leaseAgreement = leaseAgreementMapper.selectById(id);
        if (leaseAgreement == null) {
            return null;
        }

        //2.查询公寓信息
        ApartmentInfo apartmentInfo = apartmentInfoMapper.selectById(leaseAgreement.getApartmentId());

        //3.查询房间信息
        RoomInfo roomInfo = roomInfoMapper.selectById(leaseAgreement.getRoomId());

        //4.查询公寓图片
        List<GraphVo> apartmentGraphVoList = graphInfoMapper.getGraphInfoList( leaseAgreement.getApartmentId(),ItemType.APARTMENT);

        //5.查询房间图片
        List<GraphVo> roomGraphVoList = graphInfoMapper.getGraphInfoList(leaseAgreement.getRoomId(),ItemType.ROOM);

        //6.查询租期信息
        LeaseTerm leaseTerm = leaseTermMapper.selectLeaseTermById(leaseAgreement.getLeaseTermId());

        //7.查询支付方式
        PaymentType paymentType = paymentTypeMapper.selectPaymentTypeById(leaseAgreement.getPaymentTypeId());

        AgreementDetailVo agreementDetailVo = new AgreementDetailVo();
        BeanUtils.copyProperties(leaseAgreement, agreementDetailVo);
        agreementDetailVo.setApartmentName(apartmentInfo.getName());
        agreementDetailVo.setRoomNumber(roomInfo.getRoomNumber());
        agreementDetailVo.setApartmentGraphVoList(apartmentGraphVoList);
        agreementDetailVo.setRoomGraphVoList(roomGraphVoList);
        agreementDetailVo.setPaymentTypeName(paymentType.getName());
        agreementDetailVo.setLeaseTermMonthCount(leaseTerm.getMonthCount());
        agreementDetailVo.setLeaseTermUnit(leaseTerm.getUnit());

        return agreementDetailVo;
    }
}




