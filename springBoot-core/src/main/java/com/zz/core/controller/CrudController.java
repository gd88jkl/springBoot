/*
 * Project Name: spring-boot
 * File Name: CRUDAction.java
 * Copyright: Copyright(C) 1985-2016 ZKTeco Inc. All rights reserved.
 */
package com.zz.core.controller;

import com.alibaba.fastjson.JSONObject;
import com.zz.core.biz.IBaseBiz;
import com.zz.core.exception.BaseException;
import com.zz.core.utils.StringUtils;
import com.zz.core.view.ResultMessage;
import com.zz.core.domain.antDesign.AntDTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public abstract class CrudController<T, BaseBiz extends IBaseBiz<T>> {

	@Autowired
	protected BaseBiz baseBiz;
	
	@RequestMapping(value = "/findOne",method = RequestMethod.POST)
	@ResponseBody
	public ResultMessage findOne(@RequestParam(value="id") String id) {
		ResultMessage resultMessage = new ResultMessage();
		resultMessage.setData(baseBiz.findById(id));
		return resultMessage;
	}
	
	@RequestMapping(value = "/findAll",method = RequestMethod.POST)
	@ResponseBody
	public ResultMessage findAll() {
		ResultMessage resultMessage = new ResultMessage();
		resultMessage.setData(baseBiz.findAll());
		return resultMessage;
	}
	
	@RequestMapping(value = "/loadData",method = RequestMethod.POST)
	@ResponseBody
	public ResultMessage loadData(@RequestBody AntDTable table) {
        ResultMessage resultMessage = new ResultMessage();
        resultMessage.setData(baseBiz.findAll(table));
        return resultMessage;
	}

    @RequestMapping(value = "/doSave", method = RequestMethod.POST)
    public ResultMessage doSave(T t) {
        ResultMessage resultMessage = new ResultMessage();
        resultMessage.setData(baseBiz.save(t));
        return resultMessage;
    }

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public ResultMessage save(@RequestParam(value="content") String content) {
		ResultMessage resultMessage = new ResultMessage();
        Class<?> entityClass = this.getEntityClass();
        T t = (T) JSONObject.parseObject(content, entityClass);
		resultMessage.setData(baseBiz.save(t));
		return resultMessage;
	}
	
	@RequestMapping(value = "/deleteById", method = RequestMethod.POST)
	@ResponseBody
	public ResultMessage deleteById(@RequestParam(value="id") String id) throws BaseException {
		ResultMessage resultMessage = new ResultMessage();
		baseBiz.delete(id);
		return resultMessage;
	}
	
	@RequestMapping(value = "/deleteByIds", method = RequestMethod.POST)
	@ResponseBody
	public ResultMessage deleteByIds(@RequestParam(value="ids") String ids) {
		ResultMessage resultMessage = new ResultMessage();
		String[] idsArr = StringUtils.StringToArray(ids, ",");
		baseBiz.deleteByIds(idsArr);
		return resultMessage;
	}

    private Class<?> getEntityClass() {
        Class<?> cl = this.getClass();
        // 得到泛型类型参数数组就是<>里面的值
        Type[] types = ((ParameterizedType) cl.getGenericSuperclass()).getActualTypeArguments();
        try {
            return ((Class<T>) types[0]).newInstance().getClass();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
