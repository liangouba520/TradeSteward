package com.yunhe.customermanagement.dao;


import com.yunhe.customermanagement.entity.Customer;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;


/**
 * <p>
 * 客户管理 Mapper 接口
 * </p>
 *
 * @author 蔡慧鹏
 * @since 2019-01-02
 */

public interface CustomerMapper extends BaseMapper<Customer> {
/**
 * <p>
 *     导出excel
 *</p>
 * @return 客户列表
 */
List<Customer> sellectAllExcel();

    /**
     * <p>
     *      查询最大ID
     * </p>
     *@author 杨明月
     * @return zuidaID
     */
    int maxId();
}

