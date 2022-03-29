package com.shuidi.community.entity;

import java.util.Date;

/**
 * @Description: 登录凭证
 * @Author: panghairui
 * @Date: 2022/3/29 4:47 下午
 */
public class LoginTicket {

    private long id;

    private long userId;

    private String ticket;

    /**
     * 状态
     */
    private int status;

    /**
     * 过期时间
     */
    private Date expired;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getExpired() {
        return expired;
    }

    public void setExpired(Date expired) {
        this.expired = expired;
    }
}
