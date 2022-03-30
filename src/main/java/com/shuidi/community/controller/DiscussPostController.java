package com.shuidi.community.controller;

import com.shuidi.community.entity.Comment;
import com.shuidi.community.entity.DiscussPost;
import com.shuidi.community.entity.Page;
import com.shuidi.community.entity.User;
import com.shuidi.community.service.CommentService;
import com.shuidi.community.service.DiscussPostService;
import com.shuidi.community.service.UserService;
import com.shuidi.community.util.ActivationConstant;
import com.shuidi.community.util.CommunityUtil;
import com.shuidi.community.util.HostHolder;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

/**
 * @Description: 帖子视图层
 * @Author: panghairui
 * @Date: 2022/3/30 10:34 上午
 */
@Controller
@RequestMapping("/discuss")
public class DiscussPostController implements ActivationConstant {

    @Autowired
    private HostHolder hostHolder;

    @Autowired
    private UserService userService;

    @Autowired
    private CommentService commentService;

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

    @RequestMapping(path = "/detail/{discussPostId}", method = RequestMethod.GET)
    public String getDiscussPost(@PathVariable("discussPostId") long discussPostId, Model model, Page page) {

        // 帖子
        DiscussPost post = discussPostService.findDiscussPostById(discussPostId);
        model.addAttribute("post", post);
        // 作者
        User user = userService.findUserById(post.getUserId());
        model.addAttribute("user", user);

        // 评论的分页信息
        page.setLimit(5);
        page.setRows(post.getCommentCount());
        page.setPath("/discuss/detail/" + discussPostId);

        // 评论：给帖子的评论
        // 回复：给评论的评论
        List<Comment> comments = commentService.findCommentsByEntity(ENTITY_TYPE_POST, discussPostId, page.getOffset(), page.getLimit());
        // 评论的VO列表
        List<Map<String, Object>> commentVoList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(comments)) {
            for (Comment comment : comments) {
                // 评论VO
                Map<String, Object> commentVo = new HashMap<>();
                // 评论
                commentVo.put("comment", comment);
                // 作者
                commentVo.put("user", userService.findUserById(comment.getUserId()));

                // 回复列表
                List<Comment> replayList = commentService.findCommentsByEntity(ENTITY_TYPE_COMMENT, comment.getId(), 0, Integer.MAX_VALUE);
                // 回复的VO列表
                List<Map<String, Object>> replayVoList = new ArrayList<>();
                if (CollectionUtils.isNotEmpty(replayList)) {
                    for (Comment replay : replayList) {
                        Map<String, Object> replayVo = new HashMap<>();
                        // 回复
                        replayVo.put("reply", replay);
                        // 作者
                        replayVo.put("user", userService.findUserById(replay.getUserId()));
                        // 回复目标
                        User target = replay.getTargetId() == 0 ? null : userService.findUserById(replay.getTargetId());
                        replayVo.put("target", target);

                        replayVoList.add(replayVo);
                    }
                }
                commentVo.put("replys", replayVoList);

                // 回复数量
                int replyCount = commentService.findCommentCount(ENTITY_TYPE_COMMENT, comment.getId());
                commentVo.put("replyCount", replyCount);

                commentVoList.add(commentVo);
            }
        }

        model.addAttribute("comments", commentVoList);

        return "/site/discuss-detail";

    }

}
