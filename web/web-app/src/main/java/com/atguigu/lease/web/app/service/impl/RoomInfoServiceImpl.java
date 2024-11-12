package com.atguigu.lease.web.app.service.impl;

import com.atguigu.lease.common.context.LoginUserContext;
import com.atguigu.lease.model.entity.*;
import com.atguigu.lease.model.enums.ItemType;
import com.atguigu.lease.model.enums.LeaseStatus;
import com.atguigu.lease.web.app.mapper.*;
import com.atguigu.lease.web.app.service.BrowsingHistoryService;
import com.atguigu.lease.web.app.service.GraphInfoService;
import com.atguigu.lease.web.app.service.LabelInfoService;
import com.atguigu.lease.web.app.service.RoomInfoService;
import com.atguigu.lease.web.app.vo.apartment.ApartmentItemVo;
import com.atguigu.lease.web.app.vo.attr.AttrValueVo;
import com.atguigu.lease.web.app.vo.fee.FeeValueVo;
import com.atguigu.lease.web.app.vo.graph.GraphVo;
import com.atguigu.lease.web.app.vo.room.RoomDetailVo;
import com.atguigu.lease.web.app.vo.room.RoomItemVo;
import com.atguigu.lease.web.app.vo.room.RoomQueryVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author liubo
 * @description 针对表【room_info(房间信息表)】的数据库操作Service实现
 * @createDate 2023-07-26 11:12:39
 */
@Service
@Slf4j
public class RoomInfoServiceImpl extends ServiceImpl<RoomInfoMapper, RoomInfo>
        implements RoomInfoService {
    @Autowired
    private RoomInfoMapper roomInfoMapper;
    @Autowired
    private GraphInfoMapper graphInfoMapper;
    @Autowired
        private  AttrValueMapper attrValueMapper;
    @Autowired
    private FacilityInfoMapper facilityInfoMapper;
    @Autowired
    private LabelInfoMapper labelInfoMapper;
    @Autowired
    private PaymentTypeMapper paymentTypeMapper;
    @Autowired
    private FeeValueMapper feeValueMapper;
    @Autowired
    private LeaseTermMapper leaseTermMapper;
    @Autowired
    private  ApartmentInfoMapper apartmentInfoMapper;
    @Autowired
    private GraphInfoService graphInfoService;
    @Autowired
    private LabelInfoService labelInfoService;
    @Autowired
    private LeaseAgreementMapper leaseAgreementMapper;
    @Autowired
    private BrowsingHistoryService browsingHistoryService;






    @Override
    public IPage<RoomItemVo> pageItem(Page<RoomItemVo> Page, RoomQueryVo queryVo) {


        return  roomInfoMapper.pageItem(Page, queryVo);
    }

    @Override
    public RoomDetailVo getRoomDetailById(Long id) {
        RoomInfo roomInfo = this.getById(id);
        //查询公寓
        ApartmentItemVo apartmentItemVo  = new ApartmentItemVo();
        ApartmentInfo apartmentInfo = apartmentInfoMapper.selectById(roomInfo.getApartmentId());
        graphInfoService.list(new LambdaQueryWrapper<GraphInfo>().eq(GraphInfo::getItemId, apartmentInfo.getId()).eq(GraphInfo::getItemType, ItemType.APARTMENT));
        List<LabelInfo> apartLabelList=      labelInfoMapper.getLabelInfoList(apartmentInfo.getId());
        List<GraphInfo> garphList = graphInfoService.list(new LambdaQueryWrapper<GraphInfo>().eq(GraphInfo::getItemId, apartmentInfo.getId()).eq(GraphInfo::getItemType, ItemType.APARTMENT));

        BeanUtils.copyProperties(apartmentInfo, apartmentItemVo);
        apartmentItemVo.setLabelInfoList(apartLabelList);
        apartmentItemVo.setGraphVoList(garphList);


        //查询图片列表
        List<GraphVo> list = graphInfoMapper.getGraphInfoList(id, ItemType.ROOM);
        //查询属性信息表
        List<AttrValueVo> attrValueVoList = attrValueMapper.getAttrValueList(id);
        //查询配套信息
        List<FacilityInfo> facilityInfoList = facilityInfoMapper.getFacilityInfoList(id);
        //查询标签列表
        List<LabelInfo> labelInfoList = labelInfoMapper.getLabelInfoList(id);
        //查询支付方式
        List<PaymentType> paymentTypeList = paymentTypeMapper.getPaymentTypeList(id);
        //查询杂费信息
        List<FeeValueVo> feeValueVoList = feeValueMapper.getFeeValueList(apartmentInfo.getId());
        //查询租期列表
        List<LeaseTerm> leaseTermList = leaseTermMapper.getLeaseTermList(id);

        RoomDetailVo roomDetailVo = new RoomDetailVo();
        BeanUtils.copyProperties(roomInfo, roomDetailVo);
        roomDetailVo.setApartmentItemVo(apartmentItemVo);
        roomDetailVo.setGraphVoList(list);
        roomDetailVo.setAttrValueVoList(attrValueVoList);
        roomDetailVo.setFacilityInfoList(facilityInfoList);
        roomDetailVo.setLabelInfoList(labelInfoList);
        roomDetailVo.setPaymentTypeList(paymentTypeList);
        roomDetailVo.setFeeValueVoList(feeValueVoList);
        roomDetailVo.setLeaseTermList(leaseTermList);

        //查询租约信息
        Long l = leaseAgreementMapper.selectCount(new LambdaQueryWrapper<LeaseAgreement>().eq(LeaseAgreement::getRoomId, id).in(LeaseAgreement::getStatus, 2, 5));
        if(l>0){
            roomDetailVo.setIsCheckIn(true);

        }else{
            roomDetailVo.setIsCheckIn(false);
        }
        browsingHistoryService.saveHistory(LoginUserContext.getLoginUser().getUserId(), id);


        return roomDetailVo;
    }

    @Override
    public IPage<RoomItemVo> pageItemByApartmentId(Page<RoomItemVo> Page, Long id) {
        IPage<RoomItemVo> page =  roomInfoMapper.pageItemByApartmentId(Page, id);

        return page;
    }
}




