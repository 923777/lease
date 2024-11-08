package com.atguigu.lease.web.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.atguigu.lease.model.entity.GraphInfo;
import com.atguigu.lease.web.admin.service.GraphInfoService;
import com.atguigu.lease.web.admin.mapper.GraphInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author HP
* @description 针对表【graph_info(图片信息表)】的数据库操作Service实现
* @createDate 2024-10-30 18:33:58
*/
@Service
public class GraphInfoServiceImpl extends ServiceImpl<GraphInfoMapper, GraphInfo>
    implements GraphInfoService{
    @Autowired
    private GraphInfoMapper graphInfoMapper;

    @Override
    public List<String> listIsDelete() {
        List<String> list =graphInfoMapper.listIsDelete();
        return list;
    }
}




