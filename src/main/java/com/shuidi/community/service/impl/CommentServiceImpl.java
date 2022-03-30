package com.shuidi.community.service.impl;

import com.shuidi.community.dao.CommentDao;
import com.shuidi.community.entity.Comment;
import com.shuidi.community.service.CommentService;
import com.shuidi.community.service.DiscussPostService;
import com.shuidi.community.util.ActivationConstant;
import com.shuidi.community.util.SensitiveFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.HtmlUtils;

import java.util.List;
import java.util.Objects;

/**
 * @Description: 评论服务
 * @Author: panghairui
 * @Date: 2022/3/30 1:07 下午
 */
@Service
public class CommentServiceImpl implements CommentService, ActivationConstant {

    @Autowired
    private CommentDao commentDao;

    @Autowired
    private SensitiveFilter sensitiveFilter;

    @Autowired
    private DiscussPostService discussPostService;

    @Override
    public List<Comment> findCommentsByEntity(int entityType, long entityId, int offset, int limit) {
        return commentDao.selectCommentsByEntity(entityType, entityId, offset, limit);
    }

    @Override
    public int findCommentCount(int entityType, long entityId) {
        return commentDao.selectCountByEntity(entityType, entityId);
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public int addComment(Comment comment) {

        if (Objects.isNull(comment)) {
            throw new IllegalArgumentException("参数不能为空！");
        }

        // 添加评论
        comment.setContent(HtmlUtils.htmlEscape(comment.getContent()));
        comment.setContent(sensitiveFilter.filter(comment.getContent()));
        int rows = commentDao.insertComment(comment);

        // 更新帖子评论数量
        if (comment.getEntityType() == ENTITY_TYPE_POST) {
            int count = commentDao.selectCountByEntity(comment.getEntityType(), comment.getEntityId());
            discussPostService.updateCommentCount(comment.getEntityId(), count);
        }

        return rows;
    }
}
