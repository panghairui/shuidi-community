package com.shuidi.community.entity;

/**
 * @Description: 封装分页相关的信息
 * @Author: panghairui
 * @Date: 2022/3/28 5:02 下午
 */
public class Page {

    // 当前页码
    private int current = 1;

    // 显示上限
    private int limit = 10;

    // 数据总数(用于计算总的页数)
    private long rows;

    // 查询路径(复用分页链接)
    private String path;

    /**
     * 获取当前页的起始行
     */
    public int getOffset() {
        // current * limit - limit
        return (current - 1) * limit;
    }

    /**
     * 获取总页数
     */
    public long getTotal() {
        // rows / limit [+1]
        return (rows % limit == 0) ? rows / limit : rows / limit + 1;
    }

    /**
     * 获取起始页码
     */
    public int getFrom() {
        int from = current - 2;
        return Math.max(from, 1);
    }

    /**
     * 获取终止页码
     */
    public long getTo() {
        long to = current + 2;
        long total = getTotal();
        return Math.min(to, total);
    }

    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        if (current >= 1) {
            this.current = current;
        }
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        if (limit >= 1 && limit <= 100) {
            this.limit = limit;
        }
    }

    public long getRows() {
        return rows;
    }

    public void setRows(long rows) {
        if (rows >= 0) {
            this.rows = rows;
        }
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
