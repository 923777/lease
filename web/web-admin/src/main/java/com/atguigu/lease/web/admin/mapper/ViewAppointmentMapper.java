package com.atguigu.lease.web.admin.mapper;

import com.atguigu.lease.model.entity.ViewAppointment;
import com.atguigu.lease.web.admin.vo.appointment.AppointmentQueryVo;
import com.atguigu.lease.web.admin.vo.appointment.AppointmentVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
* @author HP
* @description 针对表【view_appointment(预约看房信息表)】的数据库操作Mapper
* @createDate 2024-10-30 18:33:59
* @Entity com.atguigu.lease.model.entity.ViewAppointment
*/
public interface ViewAppointmentMapper extends BaseMapper<ViewAppointment> {

    IPage<AppointmentVo> pageAppointmentByQuery(Page<AppointmentVo> page, AppointmentQueryVo queryVo);
}




