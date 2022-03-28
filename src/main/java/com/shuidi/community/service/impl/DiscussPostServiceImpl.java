package com.shuidi.community.service.impl;

import com.shuidi.community.dao.DiscussPostDao;
import com.shuidi.community.entity.DiscussPost;
import com.shuidi.community.service.DiscussPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Override
    public List<DiscussPost> findDiscussPosts(long userId, int offset, int limit) {
        return discussPostDao.selectDiscussPosts(userId, offset, limit);
    }

    @Override
    public long findDiscussPostRows(long userId) {
        return discussPostDao.selectDiscussPostRows(userId);
    }

}
