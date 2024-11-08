package com.atguigu.lease.web.admin.service.impl;

import com.atguigu.lease.common.Utils.JwtUtil;
import com.atguigu.lease.common.constant.RedisConstant;
import com.atguigu.lease.common.exception.LeaseException;
import com.atguigu.lease.common.result.ResultCodeEnum;
import com.atguigu.lease.model.entity.SystemUser;
import com.atguigu.lease.model.entity.UserInfo;
import com.atguigu.lease.model.enums.BaseStatus;
import com.atguigu.lease.web.admin.mapper.SystemUserMapper;
import com.atguigu.lease.web.admin.service.LoginService;
import com.atguigu.lease.web.admin.service.SystemUserService;
import com.atguigu.lease.web.admin.vo.login.CaptchaVo;
import com.atguigu.lease.web.admin.vo.login.LoginVo;
import com.atguigu.lease.web.admin.vo.system.user.SystemUserInfoVo;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.wf.captcha.GifCaptcha;
import com.wf.captcha.SpecCaptcha;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.UUID;
import java.util.concurrent.TimeUnit;


@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    StringRedisTemplate redisTemplate;
    @Autowired
    SystemUserMapper systemUserMapper;

    @Override
    public CaptchaVo getCaptcha() {
        //图片对象
//        SpecCaptcha specCaptcha = new SpecCaptcha(130, 48, 5);
        GifCaptcha specCaptcha = new GifCaptcha(130, 48);
        //值
        String verCode = specCaptcha.text().toLowerCase();
        //键
        String key = UUID.randomUUID().toString();
        // 存入redis并设置过期时间为30分钟
        redisTemplate.opsForValue().set(key, verCode, RedisConstant.ADMIN_LOGIN_CAPTCHA_TTL_SEC, TimeUnit.SECONDS);
        String image = specCaptcha.toBase64();
        CaptchaVo captchaVo = new CaptchaVo(image, key);
        return captchaVo;
    }

    @Override
    public String login(LoginVo loginVo) {
        //验证码为空
       if(!StringUtils.hasText(loginVo.getCaptchaCode())){
           throw new LeaseException(ResultCodeEnum.APP_LOGIN_CODE_EMPTY);
       }
       //验证码过期
        if (redisTemplate.opsForValue().get(loginVo.getCaptchaKey())==null) {
            throw new LeaseException(ResultCodeEnum.APP_LOGIN_CODE_EXPIRED);
        }
        //校验用户是否存在
        Long l = systemUserMapper.selectCount(new LambdaUpdateWrapper<SystemUser>().eq(SystemUser::getUsername, loginVo.getUsername()));
        if(l==0){
            throw new LeaseException(ResultCodeEnum.ADMIN_ACCOUNT_NOT_EXIST_ERROR);

            }
        //校验用户是否被禁
        SystemUser systemUser = systemUserMapper.selectOne(new LambdaUpdateWrapper<SystemUser>().eq(SystemUser::getUsername, loginVo.getUsername()));
        if (systemUser.getStatus()== BaseStatus.DISABLE) {
            throw new LeaseException(ResultCodeEnum.ADMIN_ACCOUNT_DISABLED_ERROR);
        }
        String s = DigestUtils.md5Hex(loginVo.getPassword());
        System.out.println("s = " + s);
        //校验用户密码是否正确
        if (!systemUser.getPassword().equals(DigestUtils.md5Hex(loginVo.getPassword()))) {
            throw new LeaseException(ResultCodeEnum.ADMIN_ACCOUNT_ERROR);
        }
        return JwtUtil.createToken(systemUser.getId(), systemUser.getUsername());




    }

    @Override
    public SystemUserInfoVo getSystemUserInfoById(Long userId) {
        SystemUser systemUser = systemUserMapper.selectById(userId);
        SystemUserInfoVo systemUserInfoVo = new SystemUserInfoVo();
        systemUserInfoVo.setName(systemUser.getName());
        systemUserInfoVo.setAvatarUrl(systemUser.getAvatarUrl());
        return systemUserInfoVo;

    }
}
