package com.shuidi.community.dao;

import com.shuidi.community.entity.DiscussPost;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Description: 帖子数据访问层
 * @Author: panghairui
 * @Date: 2022/3/28 4:18 下午
 */
@Mapper
public interface DiscussPostDao {

    List<DiscussPost> selectDiscussPosts(long userId, int offset, int limit);

    long selectDiscussPostRows(@Param("userId") long userId);

    int insertDiscussPost(DiscussPost discussPost);

    DiscussPost selectDiscussPostById(long id);

    int updateCommentCount(long id, long commentCount);

}
