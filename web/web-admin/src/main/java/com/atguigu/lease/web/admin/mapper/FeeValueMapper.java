package com.atguigu.lease.web.admin.mapper;

import com.atguigu.lease.model.entity.FeeValue;
import com.atguigu.lease.web.admin.vo.fee.FeeValueVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
* @author HP
* @description 针对表【fee_value(杂项费用值表)】的数据库操作Mapper
* @createDate 2024-10-30 18:33:58
* @Entity com.atguigu.lease.model.entity.FeeValue
*/
public interface FeeValueMapper extends BaseMapper<FeeValue> {


    List<FeeValueVo> selectListById(Long id);
}




