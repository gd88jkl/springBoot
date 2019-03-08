package com.zz.core.domain.antDesign;

/**
 * @author yaozhou.chen
 * @create 2019-02-21 16:48
 */
public class Sorter {

    private String field;

    private String order;

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        switch (order) {
            case "ascend":
                this.order = "asc";
                break;
            case "descend":
                this.order = "desc";
                break;
            default:
                this.order = order;
        }
    }
}