package com.atguigu.lease.web.admin.service;

import com.atguigu.lease.model.entity.ApartmentInfo;
import com.atguigu.lease.web.admin.vo.apartment.ApartmentDetailVo;
import com.atguigu.lease.web.admin.vo.apartment.ApartmentItemVo;
import com.atguigu.lease.web.admin.vo.apartment.ApartmentQueryVo;
import com.atguigu.lease.web.admin.vo.apartment.ApartmentSubmitVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author HP
* @description 针对表【apartment_info(公寓信息表)】的数据库操作Service
* @createDate 2024-10-30 18:33:58
*/
public interface ApartmentInfoService extends IService<ApartmentInfo> {

    IPage<ApartmentItemVo> pageItem(Page<ApartmentItemVo> apartmentItemVoPage, ApartmentQueryVo queryVo);

    ApartmentDetailVo getDetailById(Long id);

    void saveOrUpdateApa(ApartmentSubmitVo apartmentSubmitVo);

    void removeapartmentInfoById(Long id);
}

