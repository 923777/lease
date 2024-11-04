package com.atguigu.lease.web.admin.mapper;

import com.atguigu.lease.model.entity.LabelInfo;
import com.atguigu.lease.model.enums.ItemType;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
* @author HP
* @description 针对表【label_info(标签信息表)】的数据库操作Mapper
* @createDate 2024-10-30 18:33:58
* @Entity com.atguigu.lease.model.entity.LabelInfo
*/
public interface LabelInfoMapper extends BaseMapper<LabelInfo> {

    List<LabelInfo> selectListByItemIdAndType(ItemType itemType, Long id);
}




