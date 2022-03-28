package com.shuidi.community.entity;

import java.util.Date;

/**
 * @Description: 帖子信息
 * @Author: panghairui
 * @Date: 2022/3/28 4:15 下午
 */
public class DiscussPost {

    private long id;

    private long userId;

    /**
     * 帖子标题
     */
    private String title;

    /**
     * 帖子内容
     */
    private String content;

    /**
     * 帖子类型 0-普通; 1-置顶;
     */
    private int type;

    /**
     * 帖子状态 0-正常; 1-精华; 2-拉黑;
     */
    private int status;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 评论数
     */
    private int commentCount;

    /**
     * 优先级
     */
    private double score;

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }
}
