package com.atguigu.lease.web.admin.mapper;

import com.atguigu.lease.model.entity.LeaseTerm;
import com.atguigu.lease.model.entity.RoomLeaseTerm;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
* @author HP
* @description 针对表【room_lease_term(房间租期管理表)】的数据库操作Mapper
* @createDate 2024-10-30 18:33:59
* @Entity com.atguigu.lease.model.entity.RoomLeaseTerm
*/
public interface RoomLeaseTermMapper extends BaseMapper<RoomLeaseTerm> {

    List<LeaseTerm> roomLeaseTermById(Long id);
}




