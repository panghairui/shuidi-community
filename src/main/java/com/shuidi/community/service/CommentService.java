package com.shuidi.community.service;

import com.shuidi.community.entity.Comment;

import java.util.List;

/**
 * @Description: 评论服务
 * @Author: panghairui
 * @Date: 2022/3/30 1:07 下午
 */
public interface CommentService {

    /**
     * 获取评论
     */
    List<Comment> findCommentsByEntity(int entityType, long entityId, int offset, int limit);

    /**
     * 获取评论数
     */
    int findCommentCount(int entityType, long entityId);

    /**
     * 增加评论
     */
    int addComment(Comment comment);

}
