package com.yunhe.billmanagement.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yunhe.billmanagement.dao.FundClientDebtMapper;
import com.yunhe.billmanagement.entity.FundClientDebt;
import com.yunhe.billmanagement.service.IFundClientDebtService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 客户应收欠款表(ymy) 服务实现类
 * </p>
 *
 * @author 杨明月
 * @since 2019-01-02
 */
@Service
public class FundClientDebtServiceImpl extends ServiceImpl<FundClientDebtMapper, FundClientDebt> implements IFundClientDebtService {

    @Resource
    private FundClientDebtMapper fundClientDebtMapper;

    @Override
    public Map selectFcdPage(int current, int size) {
        System.out.println("每页条数："+size);
        System.out.println("当前页数："+current);
        Page page = new Page(current,size);
        Map map = new HashMap();
        List<FundClientDebt> list = fundClientDebtMapper.selectFcdPage(page);
        map.put("list",list);
        System.out.println("总条数："+page.getTotal());
        map.put("total",page.getTotal());
        map.put("pages",page.getPages());
        System.out.println("总页数："+page.getPages());
        return map;
    }

    @Override
    public int insertFcd(FundClientDebt fundClientDebt) {
        return fundClientDebtMapper.insert(fundClientDebt);
    }
}