package com.atguigu.lease.web.admin.service.impl;

import com.atguigu.lease.web.admin.vo.fee.FeeKeyVo;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.atguigu.lease.model.entity.FeeKey;
import com.atguigu.lease.web.admin.service.FeeKeyService;
import com.atguigu.lease.web.admin.mapper.FeeKeyMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author HP
* @description 针对表【fee_key(杂项费用名称表)】的数据库操作Service实现
* @createDate 2024-10-30 18:33:58
*/
@Service
public class FeeKeyServiceImpl extends ServiceImpl<FeeKeyMapper, FeeKey>
    implements FeeKeyService{
    @Autowired
    private FeeKeyMapper feeKeyMapper;

    @Override
    public List<FeeKeyVo> feeInfoList() {
        List<FeeKeyVo>  list =feeKeyMapper.feeInfoList();
        return list;
    }
}




