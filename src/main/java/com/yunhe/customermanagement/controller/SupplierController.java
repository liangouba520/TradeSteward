package com.yunhe.customermanagement.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yunhe.billmanagement.entity.FundProviderDebt;
import com.yunhe.billmanagement.service.IFundProviderDebtService;
import com.yunhe.customermanagement.entity.Supplier;
import com.yunhe.customermanagement.service.ISupplierService;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.OutputStream;

import java.util.List;


/**
 * <p>
 * 供应商管理 前端控制器
 * </p>
 *
 * @author 蔡慧鹏
 * @since 2019-01-02
 */
@RestController
@RequestMapping("/customermanagement/supplier")
public class SupplierController {
    @Resource
    ISupplierService supplierService;

    @Resource
    IFundProviderDebtService fundProviderDebtService;

    @RequestMapping("/editSupplier")
    public ModelAndView selectAllCustomer(Integer id, Model model) {
        Supplier supplier = supplierService.getById(id);
        model.addAttribute("supplier", supplier);
        return new ModelAndView ("customermanagement/editSupplier");
    }

    /**
     * <p>
     *     进入供应商添加页面
     * </p>
     * @author 杨明月
     * @param session 将最大ID存到session里
     * @return 进入addCustomer.html
     */
    @RequestMapping("/addSupplier")
    public ModelAndView addSupplier(HttpSession session) {
        int id = supplierService.maxId();
        int maxId = id+1;
        session.setAttribute("maxId",maxId);
        return new ModelAndView ("customermanagement/addSupplier");
    }

    @RequestMapping("/supp")
    @ResponseBody
    public ModelAndView selectAllCustomer1() {
        return new ModelAndView("customermanagement/supplier.html");
    }

    /**
     * <p>
     * 模糊查询
     * </p>
     *
     * @param supplier
     * @return 供应商列表
     */
    @RequestMapping("/selectPage")
    @ResponseBody
    public IPage<Supplier> selectPage(int current, int size, Supplier supplier) {
        System.out.println(current);
        return supplierService.selectPage(current, size, supplier);
    }

    /**
     *<p>
     *     修改供应商信息
     *</p>
     * @param supplier 供应商
     * @return list页面
     */
    @RequestMapping("/updateSupplier")
    @ResponseBody
    public Integer updateSupplier(Supplier supplier) {
        return supplierService.updateSupplier(supplier);
    }

    /**
     *<p>
     *     新增供应商
     *</p>
     * @param  supplier 新增供应商
     * @return list页面
     */
    @RequestMapping("/insertSupplier")
    @ResponseBody
    public Integer insertSupplier(Supplier supplier){
        return  supplierService.insertSupplier(supplier);
    }

    /**
     *<p>
     *    根据id删除供应商
     *</p>
     * @param id 根据id删除
     * @return list页面
     */

    @PostMapping("/deleteSupplier")
    @ResponseBody
    public Integer deleteSupplier(int id) {
        List<Supplier> supplierName = supplierService.list(new QueryWrapper<Supplier>().eq("id", id));
        Supplier supplier = supplierName.get(0);
        String fpdName = supplier.getSupCompname();
        int i =1;//1代表不能删,0代表可以删
        List<FundProviderDebt> list = fundProviderDebtService.list(new QueryWrapper<FundProviderDebt>().eq("fpd_name", fpdName));
        if(list.size()>0){
            i=1;
        }else {
            i=0;
            supplierService.deleteSupplier(id);
        }
        return i;
    }

    /**
     * <p>
     *     创建表头
     * </p>
     * @param workbook
     * @param sheet
     */
    private void createTitle(HSSFWorkbook workbook, HSSFSheet sheet){
        HSSFRow row = sheet.createRow(0);
        //设置列宽，setColumnWidth的第二个参数要乘以256，这个参数的单位是1/256个字符宽度
        sheet.setColumnWidth(2,12*256);
        sheet.setColumnWidth(3,17*256);

        //设置居中加粗
        HSSFCellStyle style = workbook.createCellStyle();
        HSSFFont font = workbook.createFont();
        font.setBold(true);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setFont(font);

        HSSFCell cell;
        cell = row.createCell(0);
        cell.setCellValue("序号");
        cell.setCellStyle(style);

        cell = row.createCell(1);
        cell.setCellValue("公司名");
        cell.setCellStyle(style);

        cell = row.createCell(2);
        cell.setCellValue("编号");
        cell.setCellStyle(style);

        cell = row.createCell(3);
        cell.setCellValue("应收欠款");
        cell.setCellStyle(style);

        cell = row.createCell(4);
        cell.setCellValue("电话");
        cell.setCellStyle(style);

        cell = row.createCell(5);
        cell.setCellValue("联系人");
        cell.setCellStyle(style);

        cell = row.createCell(6);
        cell.setCellValue("供应商状态");
        cell.setCellStyle(style);

        cell = row.createCell(7);
        cell.setCellValue("关联人员");
        cell.setCellStyle(style);



        cell = row.createCell(8);
        cell.setCellValue("邮箱");
        cell.setCellStyle(style);


        cell = row.createCell(9);
        cell.setCellValue("备注");
        cell.setCellStyle(style);

    }

    @SuppressWarnings({"unchecked","rawtypes"})
    @RequestMapping("getExcel")
    public String getExcel(HttpServletResponse response) throws IOException {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("供应商统计表");
        createTitle(workbook,sheet);
        List<Supplier> entities = (List<Supplier>) supplierService.selectAllExcel();

        //设置日期格式
        HSSFCellStyle style = workbook.createCellStyle();
        style.setDataFormat(HSSFDataFormat.getBuiltinFormat("m/d/yy h:mm"));

        //新增数据航，并且设置单元格数据
        int rowNum = 1;

        for (Supplier supplier:entities
        ) {
            HSSFRow row = sheet.createRow(rowNum);

            row.createCell(0).setCellValue(supplier.getId());
            row.createCell(1).setCellValue(supplier.getSupCompname());
            row.createCell(2).setCellValue(supplier.getSupNumber());
            row.createCell(3).setCellValue(supplier.getSupMoney());
            row.createCell(4).setCellValue(supplier.getSupTele());
            row.createCell(5).setCellValue(supplier.getSupFlag());
            row.createCell(6).setCellValue(supplier.getSupLinkman());
            row.createCell(7).setCellValue(supplier.getSupConn());
            row.createCell(8).setCellValue(supplier.getSupEmail());
            row.createCell(9).setCellValue(supplier.getSupRemarks());


            rowNum++;

        }

        OutputStream output = response.getOutputStream();
        response.reset();
        response.setHeader("Content-disposition","attachment; filename=供应商.xls");
        response.setContentType("application/msexcel");
        workbook.write(output);
        output.close();
        return null;
    }




}
