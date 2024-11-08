package com.atguigu.lease.web.admin.service;

import com.atguigu.lease.model.entity.GraphInfo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author HP
* @description 针对表【graph_info(图片信息表)】的数据库操作Service
* @createDate 2024-10-30 18:33:58
*/
public interface GraphInfoService extends IService<GraphInfo> {


    List<String> listIsDelete();
}
