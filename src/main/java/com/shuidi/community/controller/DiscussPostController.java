package com.shuidi.community.controller;

import com.shuidi.community.entity.DiscussPost;
import com.shuidi.community.entity.User;
import com.shuidi.community.service.DiscussPostService;
import com.shuidi.community.util.CommunityUtil;
import com.shuidi.community.util.HostHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.Objects;

/**
 * @Description: 帖子视图层
 * @Author: panghairui
 * @Date: 2022/3/30 10:34 上午
 */
@Controller
@RequestMapping("/discuss")
public class DiscussPostController {

    @Autowired
    private HostHolder hostHolder;

    @Autowired
    private DiscussPostService discussPostService;

    @ResponseBody
    @RequestMapping(path = "/add", method = RequestMethod.POST)
    public String addDiscussPost(String title, String content) {
        User user = hostHolder.getUser();
        if (Objects.isNull(user)) {
            return CommunityUtil.getJSONString(403, "你还没有登录哦！");
        }

        DiscussPost post = new DiscussPost();
        post.setUserId(user.getId());
        post.setTitle(title);
        post.setContent(content);
        post.setCreateTime(new Date());
        discussPostService.addDiscussPost(post);

        // 报错的情况，未来统一处理
        return CommunityUtil.getJSONString(0, "发布成功！");
    }

}
