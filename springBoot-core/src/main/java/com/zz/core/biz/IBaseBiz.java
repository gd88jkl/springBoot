/*
 * Project Name: spring-boot
 * File Name: IBaseBiz.java
 * Copyright: Copyright(C) 1985-2016 ZKTeco Inc. All rights reserved.
 */
package com.zz.core.biz;

import com.zz.core.domain.antDesign.AntDTable;
import com.zz.core.exception.BaseException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Map;

public interface IBaseBiz<T> {

	public List<T> findAll();

	public T findById(String id);

	public List<T> findAll(Iterable<String> ids);

    public List<T> findAll(Sort sort);

	public Page<T> findAll(Pageable pageable);

	public Page<T> findAll(int pageNo, int pageSize);

    Page<T> findAll(AntDTable table);

    Page<T> findAll(Specification<T> specification, Pageable pageable);

	public void delete(String id) throws BaseException;

	public void delete(T entity);

	public void delete(Iterable<? extends T> entities);

	public void deleteByIds(String[] ids);

	public void deleteAll();

	public T save(T entity);

	public List<T> save(Iterable<T> entities);

	public T saveAndFlush(T entity);

	public void flush();

	public boolean exists(String id);

}
