package com.yunhe.billmanagement.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yunhe.billmanagement.entity.FundClientDebt;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 客户应收欠款表(ymy) 服务类
 * </p>
 *
 * @author 杨明月
 * @since 2019-01-02
 */
public interface IFundClientDebtService extends IService<FundClientDebt> {


    /**
     * 客户应收欠款表
     * @param current 当前页
     * @param size 每页条数
     * @return 客户应收欠款表:分页的结果集
     */
    IPage selectFcdPage(int current, int size);

    /**
     * 客户应收欠款表
     * @param current 当前页
     * @param size 每页条数
     * @return 客户应收欠款表:分页的结果集
     */
    Map selectFcdPage(int current, int size, FundClientDebt fundClientDebt);

    /**
     * <P>
     *    通过id查找该客户是否欠款
     * </P>
     * @return 集合
     */
    FundClientDebt selectIdFcdExit(String fcdName);

    /**
     * <P>
     *     增加数据
     * </P>
     * @param fundClientDebt 新增收款的参数存在一个对象里
     * @return  客户应收欠款表：增加是否成功
     */
    int insertFcd(FundClientDebt fundClientDebt);

    /**
     * <P>
     *     修改数据
     * </P>
     * @param fundClientDebt 修改的参数存在一个对象里
     * @return  客户应收欠款表：修改是否成功
     */
    int updateFcd(FundClientDebt fundClientDebt);
    /**
     * <P>
     *     查询所有记录
     * </P>
     * @return 客户应收欠款的条数
     */
    List<FundClientDebt> selectFcd();

    /**
     * <P>
     *     客户应收欠款表
     * </P>
     * @return 查询所有款项之和
     */
    Map<String,Object> selectFcdMap();
}