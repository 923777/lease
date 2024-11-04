package com.atguigu.lease.web.admin.mapper;

import com.atguigu.lease.model.entity.GraphInfo;
import com.atguigu.lease.model.enums.ItemType;
import com.atguigu.lease.web.admin.vo.graph.GraphVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
* @author HP
* @description 针对表【graph_info(图片信息表)】的数据库操作Mapper
* @createDate 2024-10-30 18:33:58
* @Entity com.atguigu.lease.model.entity.GraphInfo
*/
public interface GraphInfoMapper extends BaseMapper<GraphInfo> {

    List<GraphVo> seletctListByItemIdAndType(ItemType itemType, Long id);
}




