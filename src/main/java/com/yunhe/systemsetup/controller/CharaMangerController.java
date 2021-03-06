package com.yunhe.systemsetup.controller;



import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yunhe.systemsetup.entity.CharaManger;
import com.yunhe.systemsetup.service.impl.CharaMangerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import org.springframework.ui.Model;

import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;

import java.util.Map;

/**
 * <p>
 * 角色管理 前端控制器
 * </p>
 *
 * @author heyuji
 * @since 2019-01-02
 */
@RestController
@RequestMapping("/systemsetup/chara-manger")
public class CharaMangerController {
    @Autowired
    public CharaMangerServiceImpl charaMangerService;

    //映射到页面中
    @RequestMapping("/chara")
    public ModelAndView toChara(){
        return new ModelAndView("/systemsetup/chararole");
    }

    //查询所有的角色管理
    @RequestMapping("/charalist")
    public Map selectAll(Integer pageSize, Integer pageNum){
        Page page = new Page();
        page.setSize(pageSize);
        page.setCurrent(pageNum);
        List<CharaManger> list= charaMangerService.selectAll(page);
        Map map = new HashMap();
        map.put("total",page.getTotal());
        map.put("page",page);
        map.put("list",list);
        map.put("totalPage",page.getPages());
        System.out.println(list);
        return map;
    }

    /**
     *
     * @return ModelAndView
     * 返回增加角色的页面
     */
    @RequestMapping(value = "/addchara")
    public ModelAndView toAdd(){
        return new ModelAndView("/systemsetup/chararole-add");
    }
    /**
     * 检查角色名称是否存在
     *
     */
    @RequestMapping(value = "/checkchara")
    public Boolean checkchara(CharaManger charaManger){
        Boolean a=charaMangerService.checkRole(charaManger);
        System.out.println(a);
        return a;
    }

    /**
     *
     *
     * @param charaManger
     * @return void
     * 增加角色
     */
    @RequestMapping(value = "/addrole")
    public int addRole(CharaManger charaManger){
       return charaMangerService.insertRole(charaManger);
    }

    //返回修改页面
    @RequestMapping("/chara_edit")
    public ModelAndView toCharaEdit(int id, HttpSession session){
        CharaManger charamanger = charaMangerService.getById(id);
        System.out.println(charamanger);
        session.setAttribute("chara",charamanger);
        return new ModelAndView("/systemsetup/chararole-edit");
    }

    /**
     * 提交修改页面
     * @param
     * @param charaManger
     * @return
     */
    @RequestMapping("/subchara")
    public Boolean  subCharaEdit(CharaManger charaManger){
        System.out.println("这是个charaManger");
       // System.out.println(charaMangerService.subCharaMage(charaManger));
        //return charaMangerService.updateById(charaManger);
        return charaMangerService.updateById(charaManger);
    }
    //删除角色
    @RequestMapping("/deleteRole")
    public Boolean deleteRole(CharaManger charaManger){
        Boolean a=charaMangerService.removeById(charaManger.getId());
        System.out.println(a);
        return a;
    }

    public CharaMangerServiceImpl getCharaMangerService() {
        return charaMangerService;
    }

    public void setCharaMangerService(CharaMangerServiceImpl charaMangerService) {
        this.charaMangerService = charaMangerService;
    }
}
