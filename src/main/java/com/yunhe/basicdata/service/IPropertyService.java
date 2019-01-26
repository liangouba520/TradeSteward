package com.yunhe.basicdata.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yunhe.basicdata.entity.Property;
import com.yunhe.basicdata.entity.Propertyval;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 属性设置 服务类
 * </p>
 *
 * @author 唐凯宽，李恒奎
 * @since 2019-01-05
 */
public interface IPropertyService extends IService<Property> {
    /**
     *  查询属性列表
     * @return 属性列表
     */
 public Map selectproperty();

    /**
     * 增加属性
     * @param property 传入的属性值
     */
 public void addproperty(Property property);

    /**
     * 删除商品属性
     * @param id 传过来要删除的id
     */
 public void deletepropoerty(int id);

    /**
     * 编辑商品属性
     * @param property 传过来的属性值
     */
 public void updateproperty(Property property);

    /**
     * 查找属性值属性名
     * @return 属性值属性名
     */
    List<Map<String,Object>> selectAll();

    /**
     * 根据id修改属性名
     * @param property
     * @return
     */
    int updatename(Property property);

    Property selectbyid(int id);

    int insertvalue(Propertyval propertyval);
}
