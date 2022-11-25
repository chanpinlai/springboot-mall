package com.jakgcc.springbootmall.dto;

import com.jakgcc.springbootmall.constant.ProductCategory;

public class OrderRequestParams {
    Integer userId;
    Integer limit;
    Integer offset;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }
}
