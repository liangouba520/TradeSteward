package com.yunhe.cargomanagement.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.yunhe.cargomanagement.entity.PurComm;
import com.yunhe.cargomanagement.entity.PurchaseHistory;
import com.baomidou.mybatisplus.extension.service.IService;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 进货历史 服务类
 * </p>
 *
 * @author 史江浩
 * @since 2019-01-07
 */
public interface IPurchaseHistoryService extends IService<PurchaseHistory> {

    /**
     * ‘分页’查询进货历史
     * @param pageNum 当前页
     * @param pageSize 每页条数
     * @param purchaseHistory 进货历史实体类
     * @return 进货历史的数据
     */
    Map getPurchaseHistoryByPhNumber(int pageNum, int pageSize, PurchaseHistory purchaseHistory);


    /**
     * 新增进货历史
     * @param purchaseHistory 实体类接收前台ajax传值的数据
     * @return int
     */
    int insertPurchaseHistory(PurchaseHistory purchaseHistory);

    /**
     * 根据id删除进货历史
     * @param id 进货历史表id
     * @return int的数据
     */
    int deleteById(Serializable id);

    /**
     * 根据id修改进货历史
     * @param purchaseHistory 进货历史实体类数据
     * @return int
     */
    int updatePurchaseHistoryById(PurchaseHistory purchaseHistory);

    /**
     * 查询所有进货历史
     * @return 所有进货历史数据
     */
    List<PurchaseHistory> selectpurchaseHistory();

    /**
     * 进货历史详情
     * @param id 根据id查询
     * @return 进货历史详情
     */
    PurchaseHistory selectXiangList(int id);

    /**
     * 一对一查询进货历史关联商品
     * @return 商品信息
     */
    List<PurComm> selectComHistZhong(int id);

    /**
     * 根据进货历史编号查询进货历史id
     * @param phNumber 进货历史编号
     * @return 进货历史信息
     */
    PurchaseHistory selectPurchaseHistoryByNumber(String phNumber);

    /**
     * 修改进货历史的入库状态
     * @param phWarehousingStatus 进货历史编号
     * @return int
     */
    int updateHistoryState(String phWarehousingStatus ,int id);
}
