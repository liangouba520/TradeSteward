package com.yunhe.cargomanagement.controller;


import com.yunhe.basicdata.service.impl.CommodityListServiceImpl;
import com.yunhe.cargomanagement.entity.PurchaseHistory;
import com.yunhe.cargomanagement.entity.PurchaseOrder;
import com.yunhe.cargomanagement.service.IPurchaseHistoryService;
import com.yunhe.cargomanagement.service.IPurchaseOrderService;
import com.yunhe.core.util.DateUtil;
import com.yunhe.customermanagement.service.ISupplierService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 进货订单历史 前端控制器
 * </p>
 *
 * @author 史江浩
 * @since 2019-01-02
 */
@RestController
@RequestMapping("/cargomanagement/purchase-order")
public class PurchaseOrderController {

    @Resource
    private IPurchaseOrderService purchaseOrderService;

    /**
     * 进货历史 业务层
     */
    @Resource
    private IPurchaseHistoryService purchaseHistoryService;
    /**
     * 供应商列表
     */
    @Resource
    private ISupplierService supplierService;

    /**
     * 商品列表
     */
    @Resource
    CommodityListServiceImpl commodityListService;

    @RequestMapping("/Purlist")
    public ModelAndView test22(){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/cargomanagement/purorder-list");
        return mv;
    }


    /**
     * 跳转到进货订单历史页面
     * @return 页面
     */
    @RequestMapping("/Test")
    public ModelAndView hello(){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("testgetPage");
        return mv;
    }

    /**
     * 增加进货订单历史页面
     * @return 页面
     */
    @RequestMapping("/addPurchase")
    public ModelAndView getaddPurchase(HttpSession session){
        /*List<Supplier> suppliers = supplierService.selectList();
        session.setAttribute("suppliers",suppliers);*/
        ModelAndView mv = new ModelAndView();
        mv.setViewName("cargomanagement/Pur_order-add");
        return mv;
    }

    /**
     * 查询供应商
     * @return 供应商数据
     */
    @RequestMapping("/getSupplieradd")
    public Map getSupplieradd(){
        return supplierService.selectList();
    }


    /**
     * 查询商品
     * @return 商品列表
     */
   /* @RequestMapping("/getCommodadd")
    public Map getCommodadd(){
        return commodityListService.selectList();
    }*/
    /**
     * 进货订单历史分页
     * @param pageNum 前台传当前页
     * @param pageSize 前台传每页条数
     * @param purchaseOrder  进货订单历史数据
     * @return 进货订单历史分页数据  传到前台 ajax接收
     */
    @RequestMapping("/getPage")
    public Map getPurchaseByPoNumber(int pageNum, int pageSize, PurchaseOrder purchaseOrder) {
        System.out.println("供111***"+purchaseOrder.getPoSupName());
        Map purchaseByPoNumber = purchaseOrderService.getPurchaseByPoNumber(pageNum, pageSize, purchaseOrder);
        System.out.println(purchaseByPoNumber);
        return purchaseByPoNumber;
    }

    /**
     * 增加进货订单历史
     * @param purchaseOrder  进货订单历史实体类数据
     */
    @RequestMapping("/addPurchaseGoTo")
    public void insertPurchaseOrder(PurchaseOrder purchaseOrder){
        System.out.println(purchaseOrder.getPoNumber());
        System.out.println(purchaseOrder.getPoYingMoney());
        System.out.println(purchaseOrder);
        purchaseOrderService.insertPurchaseOrder(purchaseOrder);
    }

    /**
     * 根据id查询进货订单历史 单条数据
     * @param id 进货订单历史表id  前台传的
     * @return
     */
    @RequestMapping("/getPuById")
    public List<PurchaseOrder> getPurchaseById(int id){
        List<PurchaseOrder> list = purchaseOrderService.getPurchaseById(id);
        return list;
    }

    /**
     * 根据id删除进货订单历史
     * @param id  进货订单历史表id  前台传的
     */
    @RequestMapping("/deletePurchaseById")
    public int deletePurchById(int id){
        System.out.println("这条数据的id"+id);
        return purchaseOrderService.deleteById(id);
    }

    /**
     * 根据id修改进货订单历史
     * @param purchaseOrder 进货订单历史实体类数据
     */
    @RequestMapping("/updatePurchaseById")
    public void updategetPurchaseById(PurchaseOrder purchaseOrder){
        System.out.println("*******"+purchaseOrder.getId());
        purchaseOrderService.updategetPurchaseById(purchaseOrder);
    }

    /**
     * 根据id查询进货订单历史并把数据存到session到前台的input中
     * @return 跳转页面
     */
    @RequestMapping("/selectById")
    public ModelAndView selectPurchaseById(int id, HttpSession httpSession){
        System.out.println("前台传过来的id:"+id);
        PurchaseOrder purchaseOrder = purchaseOrderService.selectById(id);
        httpSession.setAttribute("purchase",purchaseOrder);
        ModelAndView mv = new ModelAndView();
        mv.setViewName("cargomanagement/article-add");
        return mv;
    }

    /**
     * 审核进货订单 并增加进货历史
     * @param purchaseOrder 进货订单
     * @return int
     */
    @RequestMapping("/updateHistState")
    public int updateHistState(PurchaseOrder purchaseOrder){
        System.out.println("/*/*/*/*/*/*"+purchaseOrder.getId());
        purchaseOrder.setPoState("已审核");
        purchaseOrderService.updateHistStateByid(purchaseOrder);
        System.out.println("aaaaaaaaaaaaaaaaaaa");
        PurchaseHistory purchaseHistory = new PurchaseHistory();
        purchaseHistory.setPhDate(purchaseOrder.getPoDate());
        purchaseHistory.setPhNumber("Y"+purchaseOrder.getPoNumber());
        purchaseHistory.setPhSupname(purchaseOrder.getPoSupName());
        purchaseHistory.setPhClname(purchaseOrder.getPoClName());
        purchaseHistory.setPhQuantity(purchaseOrder.getPoQuantityOfPurchase());
        purchaseHistory.setPhAmountPayable(purchaseOrder.getPoYingMoney());
        purchaseHistory.setPhAmountPaid(purchaseOrder.getPoYingMoney());
        purchaseHistory.setPhWarehouse("默认仓库");
        purchaseHistory.setPhBill(purchaseOrder.getPoBill());
        purchaseHistory.setPhJindate(DateUtil.curr());
        purchaseHistory.setPhManeyHu("现金");
        purchaseHistory.setPhExperiencedPerson(purchaseOrder.getPoExperiencedPerson());
        purchaseHistory.setPhSinglePerson("老板");
        purchaseHistory.setPhOtherExpenses("");
        purchaseHistory.setPhWarehousingStatus("未入库");
        purchaseHistory.setPhRemarks(purchaseOrder.getPoRemarks());
        purchaseHistoryService.insertPurchaseHistory(purchaseHistory);
        return 1;
    }


    public IPurchaseOrderService getPurchaseOrderService() {
        return purchaseOrderService;
    }

    public void setPurchaseOrderService(IPurchaseOrderService purchaseOrderService) {
        this.purchaseOrderService = purchaseOrderService;
    }
}
