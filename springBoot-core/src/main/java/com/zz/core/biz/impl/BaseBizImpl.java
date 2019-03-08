/*
 * Project Name: spring-boot
 * File Name: BaseBizImpl.java
 * Copyright: Copyright(C) 1985-2016 ZKTeco Inc. All rights reserved.
 */
package com.zz.core.biz.impl;

import com.zz.core.biz.IBaseBiz;
import com.zz.core.domain.antDesign.AntDTable;
import com.zz.core.domain.antDesign.Sorter;
import com.zz.core.exception.BaseException;
import com.zz.core.exception.ExceptionCodes;
import com.zz.core.persistence.BaseJpaRepository;
import com.zz.core.domain.antDesign.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class BaseBizImpl<T, Repository extends BaseJpaRepository<T, String>> implements IBaseBiz<T> {

    @Autowired
    protected Repository repository;

    @Override
    public T findById(String id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public List<T> findAll() {
        return repository.findAll();
    }

    @Override
    public List<T> findAll(Iterable<String> ids) {
        return repository.findAllById(ids);
    }

    @Override
    public Page<T> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public Page<T> findAll(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        return repository.findAll(pageable);
    }

    @Override
    public List<T> findAll(Sort sort) {
        return repository.findAll(sort);
    }

    @Override
    public Page<T> findAll(AntDTable table) {
        Pagination pagination = table.getPagination();
        Pageable pageable = PageRequest.of(pagination.getCurrent(), pagination.getPageSize());
        Specification<T> specification = (Specification<T>) (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> restrictions = new ArrayList<>();
            Map<String, Object> filters = table.getFilters();
            addRestrictions(restrictions, filters, root, criteriaBuilder);
            if (!restrictions.isEmpty()) {
                criteriaQuery.where(criteriaBuilder.and(restrictions.toArray(new Predicate[restrictions.size() - 1])));
            }
            Sorter sorter = table.getSorter();
            String field = sorter.getField();
            if(field != null) {
                String order = sorter.getOrder();
                if(order != null && !order.isEmpty()) {
                    if(order.equals("asc")) {
                        criteriaQuery.orderBy(criteriaBuilder.asc(root.get(field)));
                    } else if(order.equals("desc")){
                        criteriaQuery.orderBy(criteriaBuilder.desc(root.get(field)));
                    }
                } else {
                    criteriaQuery.orderBy(criteriaBuilder.asc(root.get(field)));
                }
            }
            return null;
        };
        return repository.findAll(specification, pageable);
    }

    @Override
    public Page<T> findAll(Specification<T> specification, Pageable pageable) {
        return repository.findAll(specification, pageable);
    }

    @Override
    public void delete(String id) throws BaseException {
        try {
            repository.deleteById(id);
        } catch (Exception e) {
            throw new BaseException(BaseException.setMessage(ExceptionCodes.RECODE_NOT_EXIST, new String[]{id}));
        }
    }

    @Override
    public void delete(T entity) {
        repository.delete(entity);
    }

    @Override
    public void delete(Iterable<? extends T> entities) {
        repository.deleteAll(entities);
    }

    @Override
    public void deleteByIds(String[] ids) {
        if (ids != null && ids.length > 0) {
            for (String id : ids) {
                repository.deleteById(id);
            }
        }
    }

    @Override
    public void deleteAll() {
        repository.deleteAll();
    }

    @Override
    public T save(T entity) {
        return repository.save(entity);
    }

    @Override
    public List<T> save(Iterable<T> entities) {
        return repository.saveAll(entities);
    }

    @Override
    public T saveAndFlush(T entity) {
        return repository.saveAndFlush(entity);
    }

    @Override
    public void flush() {
        repository.flush();
    }

    @Override
    public boolean exists(String id) {
        return repository.existsById(id);
    }

    private void addRestrictions(List<Predicate> restrictions, Map<String, Object> filters, Root<T> root, CriteriaBuilder criteriaBuilder) {
        if (!filters.isEmpty()) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            dateFormat.setLenient(false);
            SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            dateTimeFormat.setLenient(false);
            for (Map.Entry<String, Object> entry : filters.entrySet()) {
                String key = entry.getKey();
                String field = key.contains("$") ? key.substring(0, key.lastIndexOf("$")) : key;
                String suffix = key.contains("$") ? key.substring(key.lastIndexOf("$") + 1) : "";
                Object value = entry.getValue();
                switch (suffix) {
                    case "gt":
                        if (value instanceof Number) {
                            restrictions.add(criteriaBuilder.gt(root.get(field), (Number) value));
                        } else if (value instanceof String) {
                            String _value = (String) value;
                            try {
                                Date date = dateTimeFormat.parse(_value);
                                restrictions.add(criteriaBuilder.greaterThan(root.get(field), date));
                                break;
                            } catch (ParseException ignored) {}
                            try {
                                Date date = dateFormat.parse(_value);
                                restrictions.add(criteriaBuilder.greaterThan(root.get(field), date));
                                break;
                            } catch (ParseException ignored) {}
                            restrictions.add(criteriaBuilder.greaterThan(root.get(field), _value));
                        }
                        break;
                    case "ge":
                        if (value instanceof Number) {
                            restrictions.add(criteriaBuilder.ge(root.get(field), (Number) value));
                        } else if (value instanceof String) {
                            String _value = (String) value;
                            try {
                                Date date = dateTimeFormat.parse(_value);
                                restrictions.add(criteriaBuilder.greaterThanOrEqualTo(root.get(field), date));
                                break;
                            } catch (ParseException ignored) {}
                            try {
                                Date date = dateFormat.parse(_value);
                                restrictions.add(criteriaBuilder.greaterThanOrEqualTo(root.get(field), date));
                                break;
                            } catch (ParseException ignored) {}
                            restrictions.add(criteriaBuilder.greaterThanOrEqualTo(root.get(field), _value));
                        }
                        break;
                    case "lt":
                        if (value instanceof Number) {
                            restrictions.add(criteriaBuilder.lt(root.get(field), (Number) value));
                        } else if (value instanceof String) {
                            String _value = (String) value;
                            try {
                                Date date = dateTimeFormat.parse(_value);
                                restrictions.add(criteriaBuilder.lessThan(root.get(field), date));
                                break;
                            } catch (ParseException ignored) {}
                            try {
                                Date date = dateFormat.parse(_value);
                                restrictions.add(criteriaBuilder.lessThan(root.get(field), date));
                                break;
                            } catch (ParseException ignored) {}
                            restrictions.add(criteriaBuilder.lessThan(root.get(field), _value));
                        }
                        break;
                    case "le":
                        if (value instanceof Number) {
                            restrictions.add(criteriaBuilder.le(root.get(field), (Number) value));
                        } else if (value instanceof String) {
                            String _value = (String) value;
                            try {
                                Date date = dateTimeFormat.parse(_value);
                                restrictions.add(criteriaBuilder.lessThanOrEqualTo(root.get(field), date));
                                break;
                            } catch (ParseException ignored) {}
                            try {
                                Date date = dateFormat.parse(_value);
                                restrictions.add(criteriaBuilder.lessThanOrEqualTo(root.get(field), date));
                                break;
                            } catch (ParseException ignored) {}
                            restrictions.add(criteriaBuilder.lessThanOrEqualTo(root.get(field), _value));
                        }
                        break;
                    case "in":
                        CriteriaBuilder.In<Object> in = criteriaBuilder.in(root.get(field));
                        //TODO
                        break;
                    case "like":
                        if (value instanceof String) {
                            if (!((String) value).isEmpty()) {
                                restrictions.add(criteriaBuilder.like(root.get(field), "%" + value + "%"));
                            }
                        }
                        break;
                    case "isNull":
                        restrictions.add(criteriaBuilder.isNull(root.get(key)));
                        break;
                    case "isNotNull":
                        restrictions.add(criteriaBuilder.isNotNull(root.get(key)));
                        break;
                    case "notEqual":
                        if (value instanceof String) {
                            String _value = (String) value;
                            if (!_value.isEmpty()) {
                                restrictions.add(criteriaBuilder.notEqual(root.get(field), _value));
                            }
                        } else {
                            restrictions.add(criteriaBuilder.notEqual(root.get(field), value));
                        }
                        break;
                    default:
                        if (value instanceof String) {
                            String _value = (String) value;
                            if (!_value.isEmpty()) {
                                restrictions.add(criteriaBuilder.equal(root.get(field), _value));
                            }
                        } else {
                            restrictions.add(criteriaBuilder.equal(root.get(field), value));
                        }
                }
            }
        }
    }

}
