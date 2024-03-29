package com.lwn.common.util;


import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageHelper;
import com.lwn.common.enums.OrderType;
import com.lwn.common.request.PageCondition;
import com.lwn.common.response.Paging;

import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

public class QueryHelper {

    public static String createFullLikeFilterText(String filterText) {
        String validFilter = null;

        if (null != filterText) {
            validFilter = "%" + StrUtil.trim(filterText) + "%";
        }

        return validFilter;
    }

    public static void setupPageCondition(Map<String, String> mapping, PageCondition pageCondition) {
        PageHelper.startPage(pageCondition.getPageIndex(), pageCondition.getPageSize());

        String sortString = getSortString(mapping, pageCondition);
        if (StrUtil.isNotBlank(sortString)) {
            PageHelper.orderBy(sortString);
        }
    }

    public static void setupPageCondition(PageCondition pageCondition, String sort, OrderType orderType) {
        if (StrUtil.isBlank(sort) || null == orderType) {

            throw new RuntimeException("order error");
        }
        PageHelper.startPage(pageCondition.getPageIndex(), pageCondition.getPageSize());
        String sortString = String.format("%s %s", sort, orderType.getValue());
        PageHelper.orderBy(sortString);
    }

    public static void setupPageCondition(PageCondition pageCondition) {
        PageHelper.startPage(pageCondition.getPageIndex(), pageCondition.getPageSize());
    }

    public static void setupOrder(String sort, OrderType orderType) {
        String sortString = String.format("%s %s", sort, orderType.getValue());
        PageHelper.orderBy(sortString);
    }

    public static <I, R> Paging<R> getPaging(List<I> data, Class<R> clazz) {
        com.github.pagehelper.PageInfo<I> pageInfo
                = new com.github.pagehelper.PageInfo<>(data);
        Paging<R> paging = new Paging<>();
        paging.setData(BeanFiller.target(clazz).acceptList(data));
        paging.setTotalCount(pageInfo.getTotal());
        paging.setPageSize(pageInfo.getPageSize());
        paging.setPageIndex(pageInfo.getPageNum());
        paging.setPageCount(pageInfo.getPages());

        return paging;
    }

    public static <I, R> Paging<R> getPaging(List<I> data, Class<R> clazz, BiConsumer<R, I> consumer) {
        com.github.pagehelper.PageInfo<I> pageInfo
                = new com.github.pagehelper.PageInfo<>(data);
        Paging<R> paging = new Paging<>();
        paging.setData(BeanFiller.target(clazz).acceptListDefault(data, consumer));
        paging.setTotalCount(pageInfo.getTotal());
        paging.setPageSize(pageInfo.getPageSize());
        paging.setPageIndex(pageInfo.getPageNum());
        paging.setPageCount(pageInfo.getPages());

        return paging;
    }

    public static <I> Paging<I> getPaging(List<I> data) {
        com.github.pagehelper.PageInfo<I> pageInfo = new com.github.pagehelper.PageInfo<>(data);

        return new Paging<>(pageInfo);
    }

    private static String getSortString(Map<String, String> mapping, PageCondition pageCondition) {
        if (null != pageCondition.getSort()) {
            String orderBy = mapping.get(pageCondition.getSort());
            if (null != orderBy) {
                return String.format("%s %s", orderBy, pageCondition.getOrder().getValue());
            }
        }

        return "";
    }

}
