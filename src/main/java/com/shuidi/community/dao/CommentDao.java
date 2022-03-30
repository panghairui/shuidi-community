package com.shuidi.community.dao;

import com.shuidi.community.entity.Comment;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Description: 评论数据访问层
 * @Author: panghairui
 * @Date: 2022/3/30 1:01 下午
 */
@Mapper
public interface CommentDao {

    List<Comment> selectCommentsByEntity(int entityType, long entityId, int offset, int limit);

    int selectCountByEntity(int entityType, long entityId);

    int insertComment(Comment comment);

}
