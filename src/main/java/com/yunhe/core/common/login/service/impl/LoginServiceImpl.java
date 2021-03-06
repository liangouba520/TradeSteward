package com.yunhe.core.common.login.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yunhe.core.common.login.service.ILoginService;
import com.yunhe.systemsetup.dao.EmployMapper;
import com.yunhe.systemsetup.entity.Employ;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 *   实现登录功能的业务层的实现类
 * </p>
 *
 * @author 孔邹祥
 * @date 2019年1月9日
 */
@Service
public class LoginServiceImpl implements ILoginService {

    @Resource
    EmployMapper employMapper;

    @Override
    public Employ selectOneEmploy(String name) {

        return employMapper.selectOne(
                new QueryWrapper<Employ>().eq("em_username",name));

    }
}
