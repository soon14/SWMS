package com.swms.utils.utils;

public class PaginationContext {

    private static final ThreadLocal<Integer> PAGE_NUM = new ThreadLocal<>();

    /**
     * pageSize: 每页记录条数
     */
    private static final ThreadLocal<Integer> PAGE_SIZE = new ThreadLocal<>();

    /**
     * pageNum ：get、set、remove
     */
    public static int getPageNum() {
        return PAGE_NUM.get() == null ? 0 : PAGE_NUM.get();
    }

    public static void setPageNum(int pageNumValue) {
        PAGE_NUM.set(pageNumValue);
    }

    public static void removePageNum() {
        PAGE_NUM.remove();
    }

    /**
     * pageSize ：get、set、remove
     */
    public static int getPageSize() {
        return PAGE_SIZE.get() == null ? Integer.MAX_VALUE : PAGE_SIZE.get();
    }

    public static void setPageSize(int pageSizeValue) {
        PAGE_SIZE.set(pageSizeValue);
    }

    public static void removePageSize() {
        PAGE_SIZE.remove();
    }
}
