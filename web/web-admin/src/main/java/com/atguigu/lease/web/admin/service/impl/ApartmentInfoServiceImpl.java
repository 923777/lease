package com.atguigu.lease.web.admin.service.impl;

import com.atguigu.lease.common.exception.LeaseException;
import com.atguigu.lease.common.result.ResultCodeEnum;
import com.atguigu.lease.model.entity.*;
import com.atguigu.lease.model.enums.ItemType;
import com.atguigu.lease.web.admin.mapper.*;
import com.atguigu.lease.web.admin.service.*;
import com.atguigu.lease.web.admin.vo.apartment.ApartmentDetailVo;
import com.atguigu.lease.web.admin.vo.apartment.ApartmentItemVo;
import com.atguigu.lease.web.admin.vo.apartment.ApartmentQueryVo;
import com.atguigu.lease.web.admin.vo.apartment.ApartmentSubmitVo;
import com.atguigu.lease.web.admin.vo.fee.FeeValueVo;
import com.atguigu.lease.web.admin.vo.graph.GraphVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
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
* @description 针对表【apartment_info(公寓信息表)】的数据库操作Service实现
* @createDate 2024-10-30 18:33:58
*/
@Service
public class ApartmentInfoServiceImpl extends ServiceImpl<ApartmentInfoMapper, ApartmentInfo>
    implements ApartmentInfoService{
    @Autowired
    private ApartmentInfoMapper apartmentInfoMapper;
    @Autowired
    private GraphInfoMapper graphInfoMapper;
    @Autowired
    private LabelInfoMapper labelInfoMapper;
    @Autowired
    private FacilityInfoMapper facilityInfoMapper;
    @Autowired
    private FeeValueMapper feeValueMapper;
    @Autowired
    private GraphInfoService graphInfoService;
    @Autowired
    private ApartmentLabelService  apartmentLabelService;
    @Autowired
    private ApartmentFacilityService apartmentFacilityService;
    @Autowired
    private ApartmentFeeValueService apartmentFeeValueService;
    @Autowired
    private RoomInfoMapper roomInfoMapper;
    @Autowired
    private ProvinceInfoMapper provinceInfoMapper;
    @Autowired
    private CityInfoMapper cityInfoMapper;
    @Autowired
        private DistrictInfoMapper districtInfoMapper;


    @Override
    public IPage<ApartmentItemVo> pageItem(Page<ApartmentItemVo> apartmentItemVoPage, ApartmentQueryVo queryVo) {



        return apartmentInfoMapper.pageApartmentItemByQuery(apartmentItemVoPage, queryVo);
    }

    @Override
    public ApartmentDetailVo getDetailById(Long id) {
        ApartmentInfo apartmentInfo = this.getById(id);
        if (apartmentInfo == null) {
            return null;
        }
        //获取图片列表信息
        List<GraphVo> graphInfos = graphInfoMapper.seletctListByItemIdAndType(ItemType.APARTMENT, id);
        //获取标签列表信息
        List<LabelInfo> labelInfoList = labelInfoMapper.selectListByItemIdAndType(ItemType.APARTMENT, id);
        //获取配套信息
        List <FacilityInfo>list = facilityInfoMapper.selectListById( id);
        //获取杂费信息
        List<FeeValueVo> feeValueVoList = feeValueMapper.selectListById( id);
         ApartmentDetailVo apartmentDetailVo = new ApartmentDetailVo();

        BeanUtils.copyProperties(apartmentInfo, apartmentDetailVo);
        apartmentDetailVo.setGraphVoList(graphInfos);
        apartmentDetailVo.setLabelInfoList(labelInfoList);
        apartmentDetailVo.setFacilityInfoList(list);
        apartmentDetailVo.setFeeValueVoList(feeValueVoList);
        return apartmentDetailVo;
        //查出地址详情





    }

    @Override
    public void saveOrUpdateApa(ApartmentSubmitVo apartmentSubmitVo) {
        //删除公寓信息
            boolean isUpdate = apartmentSubmitVo.getId() != null;
        Long provinceId = apartmentSubmitVo.getProvinceId();
        Long cityId = apartmentSubmitVo.getCityId();
        Long districtId = apartmentSubmitVo.getDistrictId();

        ProvinceInfo provinceInfo = provinceInfoMapper.selectById(provinceId);
        CityInfo cityInfo = cityInfoMapper.selectById(cityId);
        DistrictInfo districtInfo = districtInfoMapper.selectById(districtId);


        apartmentSubmitVo.setProvinceName(provinceInfo.getName());
        apartmentSubmitVo.setCityName(cityInfo.getName());
        apartmentSubmitVo.setDistrictName(districtInfo.getName());

        super.saveOrUpdate(apartmentSubmitVo);
            if (isUpdate){
                //删除图片列表
                graphInfoService.remove (new LambdaQueryWrapper<GraphInfo>().eq(GraphInfo::getItemId, apartmentSubmitVo.getId()).eq(GraphInfo::getItemType, ItemType.APARTMENT));
                //删除标签列表
                apartmentLabelService.remove(new LambdaQueryWrapper<ApartmentLabel>().eq(ApartmentLabel::getApartmentId, apartmentSubmitVo.getId()));
                //删除配套设施列表
                apartmentFacilityService.remove(new LambdaQueryWrapper<ApartmentFacility>().eq(ApartmentFacility::getApartmentId, apartmentSubmitVo.getId()));
                //删除杂费列表
                apartmentFeeValueService.remove(new LambdaQueryWrapper<ApartmentFeeValue>().eq(ApartmentFeeValue::getApartmentId, apartmentSubmitVo.getId()));




            }
            //插入图片列表
            List<GraphVo> graphVoList = apartmentSubmitVo.getGraphVoList();
            if (!CollectionUtils.isEmpty(graphVoList)) {
                List<GraphInfo> graphInfos = new ArrayList<>();
                for (GraphVo graphVo : graphVoList) {
                     GraphInfo graphInfo = new GraphInfo();
                     graphInfo.setName(graphVo.getName());
                     graphInfo.setUrl(graphVo.getUrl());
                     graphInfo.setItemType(ItemType.APARTMENT);
                     graphInfo.setItemId(apartmentSubmitVo.getId());
                    graphInfos.add(graphInfo);
                }
                graphInfoService.saveBatch(graphInfos);

            }
            //插入标签列表
            List<Long> labelIds = apartmentSubmitVo.getLabelIds();
            if (!CollectionUtils.isEmpty(labelIds)) {
                List<ApartmentLabel> apartmentLabels = new ArrayList<>();
                for (Long labelId : labelIds) {
//                    ApartmentLabel apartmentLabel = new ApartmentLabel();
//                    apartmentLabel.setApartmentId(apartmentSubmitVo.getId());
//                    apartmentLabel.setLabelId(labelId);
//                    apartmentLabels.add(apartmentLabel);
                    apartmentLabels.add(ApartmentLabel.builder().apartmentId(apartmentSubmitVo.getId()).labelId(labelId).build());
                }

                apartmentLabelService.saveBatch(apartmentLabels);
            }
            //插入配套设施列表
        List<Long> facilityInfoIds = apartmentSubmitVo.getFacilityInfoIds();
            if(!CollectionUtils.isEmpty(facilityInfoIds)){
            List<ApartmentFacility> apartmentFacilities = new ArrayList<>();
            for (Long facilityId : facilityInfoIds) {
                apartmentFacilities.add(ApartmentFacility.builder().apartmentId(apartmentSubmitVo.getId()).facilityId(facilityId).build());
            }
        }
        //插入杂费列表
        List<Long> feeValueIds = apartmentSubmitVo.getFeeValueIds();
            if (!CollectionUtils.isEmpty(feeValueIds)) {
                List<ApartmentFeeValue> apartmentFeeValues = new ArrayList<>();
                for (Long feeId : feeValueIds) {
                    apartmentFeeValues.add(ApartmentFeeValue.builder().apartmentId(apartmentSubmitVo.getId()).feeValueId(feeId).build());
                }
                apartmentFeeValueService.saveBatch(apartmentFeeValues);
            }
    }

    @Override
    public void removeapartmentInfoById(Long id) {
        if (roomInfoMapper.selectCount(new LambdaQueryWrapper<RoomInfo>().eq(RoomInfo::getApartmentId,id))>0){
            throw new LeaseException(ResultCodeEnum.DELETE_ERROR);
        }
        super.removeById(id);
        //删除图片列表
        graphInfoService.remove(new LambdaQueryWrapper<GraphInfo>().eq(GraphInfo::getItemId, id).eq(GraphInfo::getItemType, ItemType.APARTMENT));
        //删除标签列表
        apartmentLabelService.remove(new LambdaQueryWrapper<ApartmentLabel>().eq(ApartmentLabel::getApartmentId, id));
        //删除配套设施列表
        apartmentFacilityService.remove(new LambdaQueryWrapper<ApartmentFacility>().eq(ApartmentFacility::getApartmentId, id));
        //删除杂费列表
        apartmentFeeValueService.remove(new LambdaQueryWrapper<ApartmentFeeValue>().eq(ApartmentFeeValue::getApartmentId, id));






    }
}




