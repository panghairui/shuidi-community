package com.shuidi.community.service;

import com.shuidi.community.entity.DiscussPost;

import java.util.List;

/**
 * @Description: 帖子相关服务
 * @Author: panghairui
 * @Date: 2022/3/28 4:29 下午
 */
public interface DiscussPostService {

    /**
     * 获取帖子列表
     */
    List<DiscussPost> findDiscussPosts(long userId, int offset, int limit);

    /**
     * 获取帖子总数
     * @param userId
     * @return
     */
    long findDiscussPostRows(long userId);

    /**
     * 发布帖子
     */
    int addDiscussPost(DiscussPost post);

    /**
     * 查询帖子
     */
    DiscussPost findDiscussPostById(long id);

}
