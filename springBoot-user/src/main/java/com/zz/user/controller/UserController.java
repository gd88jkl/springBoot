package com.zz.user.controller;

import com.zz.core.biz.IBaseBiz;
import com.zz.core.controller.CrudController;
import com.zz.core.domain.antDesign.AntDTable;
import com.zz.core.view.ResultMessage;
import com.zz.user.domain.User;
import com.zz.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;


/**
 * @author yaozhou.chen
 * @create 2019-01-17 16:02
 */
@RestController
@RequestMapping("/user")
public class UserController extends CrudController<User, IBaseBiz<User>> {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/test")
    @ResponseBody
    public ResultMessage loadData() {
        ResultMessage resultMessage = new ResultMessage();
        AntDTable antDTable = new AntDTable();
        Map<String,Object> filters = new HashMap<>();
        filters.put("name$like","1");
        antDTable.setFilters(filters);
        resultMessage.setData(baseBiz.findAll(antDTable));
        return resultMessage;
    }
}