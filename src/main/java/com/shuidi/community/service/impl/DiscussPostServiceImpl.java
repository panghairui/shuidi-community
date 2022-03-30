package com.shuidi.community.service.impl;

import com.shuidi.community.dao.DiscussPostDao;
import com.shuidi.community.entity.DiscussPost;
import com.shuidi.community.service.DiscussPostService;
import com.shuidi.community.util.SensitiveFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import java.util.List;

/**
 * @Description: 帖子服务实现
 * @Author: panghairui
 * @Date: 2022/3/28 4:31 下午
 */
@Service
public class DiscussPostServiceImpl implements DiscussPostService {

    @Autowired
    private DiscussPostDao discussPostDao;

    @Autowired
    private SensitiveFilter sensitiveFilter;

    @Override
    public List<DiscussPost> findDiscussPosts(long userId, int offset, int limit) {
        return discussPostDao.selectDiscussPosts(userId, offset, limit);
    }

    @Override
    public long findDiscussPostRows(long userId) {
        return discussPostDao.selectDiscussPostRows(userId);
    }

    @Override
    public int addDiscussPost(DiscussPost post) {

        if (post == null) {
            throw new IllegalArgumentException("参数不能为空！");
        }

        // 转义html标记
        post.setTitle(HtmlUtils.htmlEscape(post.getTitle()));
        post.setContent(HtmlUtils.htmlEscape(post.getContent()));

        // 过滤敏感词
        post.setTitle(sensitiveFilter.filter(post.getTitle()));
        post.setContent(sensitiveFilter.filter(post.getContent()));

        return discussPostDao.insertDiscussPost(post);
    }

}
