package com.shuidi.community.controller;

import com.shuidi.community.entity.DiscussPost;
import com.shuidi.community.entity.Page;
import com.shuidi.community.entity.User;
import com.shuidi.community.service.DiscussPostService;
import com.shuidi.community.service.UserService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.*;

/**
 * @Description: 首页
 * @Author: panghairui
 * @Date: 2022/3/28 4:42 下午
 */
@Controller
public class HomeController {

    @Autowired
    private UserService userService;

    @Autowired
    private DiscussPostService discussPostService;

    @RequestMapping(path = "/index", method = RequestMethod.GET)
    public String getIndexPage(Model model, Page page) {

        // 方法调用前，Spring 自动实例化 model 和 page ，并将 page 注入 model
        page.setPath("/index");
        page.setRows(discussPostService.findDiscussPostRows(0));

        // userId 为 0 表示查出所有帖子数据
        List<DiscussPost> list = discussPostService.findDiscussPosts(0, page.getOffset(), page.getLimit());
        List<Map<String, Object>> discussPosts = new ArrayList<>();

        // 填充返回给前端的数据
        if (CollectionUtils.isNotEmpty(list)) {
            for (DiscussPost discussPost : list) {

                Map<String, Object> mp = new HashMap<>();
                User user = userService.findUserById(discussPost.getUserId());

                mp.put("post", discussPost);
                mp.put("user", user);

                discussPosts.add(mp);

            }
        }

        model.addAttribute("discussPosts", discussPosts);
        return "/index";
    }

}
