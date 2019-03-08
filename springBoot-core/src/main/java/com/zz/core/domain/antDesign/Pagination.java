package com.zz.core.domain.antDesign;

/**
 * @author yaozhou.chen
 * @create 2019-02-21 15:24
 */
public class Pagination {

    private Integer current = 1;

    private Integer pageSize = 20;

    public Integer getCurrent() {
        return current > 0 ? current - 1 : 0;
    }

    public void setCurrent(Integer current) {
        this.current = current;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

}