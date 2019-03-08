package com.zz.core.domain.antDesign;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yaozhou.chen
 * @create 2019-02-22 9:38
 */
public class AntDTable {

    private Pagination pagination = new Pagination();

    private Sorter sorter = new Sorter();

    private Map<String,Object> filters = new HashMap<>();

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }

    public Sorter getSorter() {
        return sorter;
    }

    public void setSorter(Sorter sorter) {
        this.sorter = sorter;
    }

    public Map<String, Object> getFilters() {
        return filters;
    }

    public void setFilters(Map<String, Object> filters) {
        this.filters = filters;
    }
}