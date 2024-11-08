package com.atguigu.lease.web.admin.service.impl;

import com.atguigu.lease.model.entity.*;
import com.atguigu.lease.model.enums.ItemType;
import com.atguigu.lease.web.admin.mapper.*;
import com.atguigu.lease.web.admin.service.*;
import com.atguigu.lease.web.admin.vo.attr.AttrValueVo;
import com.atguigu.lease.web.admin.vo.graph.GraphVo;
import com.atguigu.lease.web.admin.vo.room.RoomDetailVo;
import com.atguigu.lease.web.admin.vo.room.RoomItemVo;
import com.atguigu.lease.web.admin.vo.room.RoomQueryVo;
import com.atguigu.lease.web.admin.vo.room.RoomSubmitVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author HP
 * @description 针对表【room_info(房间信息表)】的数据库操作Service实现
 * @createDate 2024-10-30 18:33:59
 */
@Service
public class RoomInfoServiceImpl extends ServiceImpl<RoomInfoMapper, RoomInfo>
        implements RoomInfoService {
    @Autowired
    private GraphInfoService graphInfoService;
    @Autowired
    private RoomAttrValueService roomAttrValueService;
    @Autowired
    private RoomFacilityService roomFacilityService;
    @Autowired
    private RoomLabelService roomLabelService;
    @Autowired
    private RoomPaymentTypeService roomPaymentTypeService;
    @Autowired
    private RoomLeaseTermService roomLeaseTermService;
    @Autowired
    ApartmentInfoService apartmentInfoService;

    //Mapper 装配

    @Autowired
    private RoomInfoMapper roomInfoMapper;
    @Autowired
    private GraphInfoMapper graphInfoMapper;
    @Autowired
    RoomAttrValueMapper roomAttrValueMapper;
    @Autowired
    RoomFacilityMapper roomFacilityMapper;
    @Autowired
    RoomLabelMapper roomLabelMapper;
    @Autowired
    RoomPaymentTypeMapper roomPaymentTypeMapper;
    @Autowired
    RoomLeaseTermMapper roomLeaseTermMapper;


    @Override
    public void updateRoom(RoomSubmitVo roomSubmitVo) {
        boolean isupdate = roomSubmitVo.getId() != null;
        super.saveOrUpdate(roomSubmitVo);
        if (isupdate) {
            //删除图片列表
            graphInfoService.remove(new LambdaUpdateWrapper<GraphInfo>().eq(GraphInfo::getId, roomSubmitVo.getId()).eq(GraphInfo::getItemType, ItemType.ROOM));
            //删除属性列表
            roomAttrValueService.remove(new LambdaUpdateWrapper<RoomAttrValue>().eq(RoomAttrValue::getRoomId, roomSubmitVo.getId()));
            //删除配套信息
            roomFacilityService.remove(new LambdaUpdateWrapper<RoomFacility>().eq(RoomFacility::getRoomId, roomSubmitVo.getId()));
            //删除标签
            roomLabelService.remove(new LambdaUpdateWrapper<RoomLabel>().eq(RoomLabel::getRoomId, roomSubmitVo.getId()));
            //删除支付方式
            roomPaymentTypeService.remove(new LambdaUpdateWrapper<RoomPaymentType>().eq(RoomPaymentType::getRoomId, roomSubmitVo.getId()));
            //删除可选租期列表
            roomLeaseTermService.remove(new LambdaUpdateWrapper<RoomLeaseTerm>().eq(RoomLeaseTerm::getRoomId, roomSubmitVo.getId()));
        }
        //插入图片信息
        List<GraphVo> graphVoList = roomSubmitVo.getGraphVoList();

        if (!CollectionUtils.isEmpty(graphVoList)) {
            List<GraphInfo> graphInfoList = new ArrayList<>();
            for (GraphVo graphVo : graphVoList) {
                GraphInfo graphInfo = new GraphInfo();
                graphInfo.setItemType(ItemType.ROOM);
                graphInfo.setName(graphVo.getName());
                graphInfo.setUrl(graphVo.getUrl());
                graphInfo.setItemId(roomSubmitVo.getId());
                graphInfoList.add(graphInfo);

            }
            graphInfoService.saveBatch(graphInfoList);
        }


        //插入属性信息
        List<Long> attrValueIds = roomSubmitVo.getAttrValueIds();
        if (!CollectionUtils.isEmpty(attrValueIds)) {
            List<RoomAttrValue> roomAttrValueList = new ArrayList<>();
            for (Long attrValueId : attrValueIds) {
                roomAttrValueList.add(RoomAttrValue.builder().roomId(roomSubmitVo.getId()).attrValueId(attrValueId).build());

            }
            roomAttrValueService.saveBatch(roomAttrValueList);
        }
        //插入配套信息
        List<Long> facilityInfoIds = roomSubmitVo.getFacilityInfoIds();
        if (!CollectionUtils.isEmpty(facilityInfoIds)) {
            List<RoomFacility> roomFacilityList = new ArrayList<>();
            for (Long facilityInfoId : facilityInfoIds) {
                RoomFacility roomFacility = RoomFacility.builder().roomId(roomSubmitVo.getId()).facilityId(facilityInfoId).build();
                roomFacilityList.add(roomFacility);
            }


            roomFacilityService.saveBatch(roomFacilityList);
        }
        //标签信息插入
        List<Long> labelInfoIds = roomSubmitVo.getLabelInfoIds();
        if (!CollectionUtils.isEmpty(labelInfoIds)) {
            List<RoomLabel> roomLabelList = new ArrayList<>();
            for (Long labelId : labelInfoIds) {
                roomLabelList.add(RoomLabel.builder().roomId(roomSubmitVo.getId()).labelId(labelId).build());
            }
            roomLabelService.saveBatch(roomLabelList);
        }
        //支付方式插入
        List<Long> paymentTypeIds = roomSubmitVo.getPaymentTypeIds();
        if (!CollectionUtils.isEmpty(paymentTypeIds)) {
            List<RoomPaymentType> roomPaymentTypes = new ArrayList<>();
            for (Long paymentTypeId : paymentTypeIds) {
                roomPaymentTypes.add(RoomPaymentType.builder().roomId(roomSubmitVo.getId()).paymentTypeId(paymentTypeId).build());
            }
            roomPaymentTypeService.saveBatch(roomPaymentTypes);
        }
        //可选租期插入
        List<Long> leaseTermIds = roomSubmitVo.getLeaseTermIds();
        if (!CollectionUtils.isEmpty(leaseTermIds)) {
            List<RoomLeaseTerm> roomLeaseTermList = new ArrayList<>();
            for (Long leaseTermId : leaseTermIds) {
                roomLeaseTermList.add(RoomLeaseTerm.builder().roomId(roomSubmitVo.getId()).leaseTermId(leaseTermId).build());
            }
            roomLeaseTermService.saveBatch(roomLeaseTermList);

        }

    }

    @Override
    public IPage<RoomItemVo> pageItem(Page<RoomItemVo> roomItemVoPage, RoomQueryVo queryVo) {
        return roomInfoMapper.pageItem(roomItemVoPage, queryVo);
    }

    @Override
    public RoomDetailVo getDetailById(Long id) {
        //获取所属公寓的信息
        RoomDetailVo roomDetailVo = new RoomDetailVo();
        //获取对应房间
        RoomInfo roomInfo = this.getById(id);
        //查询对应公寓
        apartmentInfoService.getOne(new LambdaQueryWrapper<ApartmentInfo>().eq(ApartmentInfo::getId, roomInfo.getApartmentId()));
        //查询属性列表

        List<AttrValueVo> list = roomAttrValueMapper.roomAttrValueById(id);
        //查询配套列表
        List<FacilityInfo> list1 = roomFacilityMapper.roomFacilityById(id);
        //查询标签
        List<LabelInfo> list2 = roomLabelMapper.roomLableById(id);
        //查询支付方式
        List<PaymentType> list3 = roomPaymentTypeMapper.roomPaymentTyoeById(id);
        //查询可选租期
        List<LeaseTerm> list4 = roomLeaseTermMapper.roomLeaseTermById(id);
        //查询图片
        List<GraphVo> list5 = graphInfoMapper.graphVoById(id, ItemType.ROOM);
        BeanUtils.copyProperties(roomInfo, roomDetailVo);
        roomDetailVo.setGraphVoList(list5);
        roomDetailVo.setAttrValueVoList(list);
        roomDetailVo.setFacilityInfoList(list1);
        roomDetailVo.setLabelInfoList(list2);
        roomDetailVo.setPaymentTypeList(list3);
        roomDetailVo.setLeaseTermList(list4);


        return roomDetailVo;
    }

    @Override
    public void removeAllById(Long id) {
        //删除房间信息
        this.removeById(id);
        //删除图片列表
        graphInfoService.remove(new LambdaUpdateWrapper<GraphInfo>().eq(GraphInfo::getItemId, id).eq(GraphInfo::getItemType, ItemType.ROOM));
        //删除属性
        roomAttrValueService.remove(new LambdaUpdateWrapper<RoomAttrValue>().eq(RoomAttrValue::getRoomId, id));
        //删除配套
        roomFacilityService.remove(new LambdaUpdateWrapper<RoomFacility>().eq(RoomFacility::getRoomId, id));
        //删除标签
        roomLabelService.remove(new LambdaUpdateWrapper<RoomLabel>().eq(RoomLabel::getRoomId, id));
        //删除支付方式
        roomPaymentTypeService.remove(new LambdaUpdateWrapper<RoomPaymentType>().eq(RoomPaymentType::getRoomId, id));
        //删除可选租期
        roomLeaseTermService.remove(new LambdaUpdateWrapper<RoomLeaseTerm>().eq(RoomLeaseTerm::getRoomId, id));

    }


}







