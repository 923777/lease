package com.atguigu.lease.web.app.service.impl;

import com.atguigu.lease.model.entity.ApartmentInfo;
import com.atguigu.lease.model.entity.FacilityInfo;
import com.atguigu.lease.model.entity.GraphInfo;
import com.atguigu.lease.model.entity.LabelInfo;
import com.atguigu.lease.model.enums.ItemType;
import com.atguigu.lease.web.app.mapper.*;
import com.atguigu.lease.web.app.service.ApartmentInfoService;
import com.atguigu.lease.web.app.service.GraphInfoService;
import com.atguigu.lease.web.app.service.LabelInfoService;
import com.atguigu.lease.web.app.vo.apartment.ApartmentDetailVo;
import com.atguigu.lease.web.app.vo.apartment.ApartmentItemVo;
import com.atguigu.lease.web.app.vo.graph.GraphVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author liubo
 * @description 针对表【apartment_info(公寓信息表)】的数据库操作Service实现
 * @createDate 2023-07-26 11:12:39
 */
@Service
public class ApartmentInfoServiceImpl extends ServiceImpl<ApartmentInfoMapper, ApartmentInfo>
        implements ApartmentInfoService {

    @Autowired
    private  ApartmentInfoMapper apartmentInfoMapper;
    @Autowired
    private  GraphInfoMapper graphInfoMapper;
    @Autowired
    private LabelInfoMapper labelInfoMapper;
    @Autowired
    private  FacilityInfoMapper facilityInfoMapper;
    @Autowired
    private  RoomInfoMapper roomInfoMapper;
    @Autowired
    private GraphInfoService graphInfoService;


    @Override
    public ApartmentDetailVo getDetailById(Long id) {
        //1.查询ApartmentInfo
        ApartmentInfo apartmentInfo = apartmentInfoMapper.selectById(id);
        if (apartmentInfo == null) {
            return null;
        }

        //2.查询GraphInfo
        List<GraphVo> graphVoList = graphInfoMapper.getGraphInfoList( id,ItemType.APARTMENT);

        //3.查询LabelInfo
        List<LabelInfo> labelInfoList = labelInfoMapper.getLabelInfoList(id);

        //4.查询FacilityInfo
        List<FacilityInfo> facilityInfoList = facilityInfoMapper.getFacilityInfoList(id);

        //5.查询公寓最低房租
        BigDecimal minRent = roomInfoMapper.selectMinRentByApartmentId(id);

        ApartmentDetailVo appApartmentDetailVo = new ApartmentDetailVo();

        BeanUtils.copyProperties(apartmentInfo, appApartmentDetailVo);
        appApartmentDetailVo.setIsDelete(apartmentInfo.getIsDeleted() == 1);
        appApartmentDetailVo.setGraphVoList(graphVoList);
        appApartmentDetailVo.setLabelInfoList(labelInfoList);
        appApartmentDetailVo.setFacilityInfoList(facilityInfoList);
        appApartmentDetailVo.setMinRent(minRent);
        return appApartmentDetailVo;
    }

    @Override
    public ApartmentItemVo getApartmentItemVoById(Long id) {
        ApartmentInfo apartmentInfo = this.getById(id);
        List<LabelInfo> labelInfoList = labelInfoMapper.getLabelInfoList(id);
        List<GraphInfo> list = graphInfoService.list(new LambdaQueryWrapper<GraphInfo>().eq(GraphInfo::getItemId, id).eq(GraphInfo::getItemType, ItemType.APARTMENT));

        ApartmentItemVo apartmentItemVo = new ApartmentItemVo();
        BeanUtils.copyProperties(apartmentInfo, apartmentItemVo);
        apartmentItemVo.setLabelInfoList(labelInfoList) ;
        apartmentItemVo.setGraphVoList(list);
        BigDecimal minRent = roomInfoMapper.selectMinRentByApartmentId(id);
        apartmentItemVo.setMinRent(minRent);

        return apartmentItemVo;
    }
}




