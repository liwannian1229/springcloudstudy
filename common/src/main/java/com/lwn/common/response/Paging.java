package com.lwn.common.response;

import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.ArrayList;
import java.util.Collection;

@Data
@ApiModel("分页信息基础数据")
public class Paging<T> {


    private int pageIndex;
    private int pageSize;
    private long totalCount;
    private int pageCount;

    private Collection<T> data;

    public Paging() {
        data = new ArrayList<>();
    }

    /**
     * @param _pageIndex 页标,从1开始
     * @param _pageSize  页容量
     */
    public Paging(int _pageIndex, int _pageSize) {
        this.pageIndex = _pageIndex;
        this.pageSize = _pageSize;
        this.data = new ArrayList<>();
    }

    public Paging(PageInfo<T> phPage) {
        this.pageIndex = phPage.getPageNum();
        this.pageSize = phPage.getPageSize();
        this.totalCount = phPage.getTotal();
        this.pageCount = phPage.getPages();
        if (null == phPage.getList()) {
            this.data = new ArrayList<>();
        } else {
            this.data = phPage.getList();
        }
    }

}
