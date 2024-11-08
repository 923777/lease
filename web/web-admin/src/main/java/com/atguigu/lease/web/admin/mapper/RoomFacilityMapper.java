package com.atguigu.lease.web.admin.mapper;

import com.atguigu.lease.model.entity.FacilityInfo;
import com.atguigu.lease.model.entity.RoomFacility;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
* @author HP
* @description 针对表【room_facility(房间&配套关联表)】的数据库操作Mapper
* @createDate 2024-10-30 18:33:58
* @Entity com.atguigu.lease.model.entity.RoomFacility
*/
public interface RoomFacilityMapper extends BaseMapper<RoomFacility> {

    List<FacilityInfo> roomFacilityById(Long id);
}




