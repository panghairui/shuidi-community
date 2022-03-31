package com.shuidi.community.service.impl;

import com.shuidi.community.dao.LoginTicketDao;
import com.shuidi.community.dao.UserDao;
import com.shuidi.community.entity.LoginTicket;
import com.shuidi.community.entity.User;
import com.shuidi.community.service.UserService;
import com.shuidi.community.util.ActivationConstant;
import com.shuidi.community.util.CommunityUtil;
import com.shuidi.community.util.MailClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.*;

/**
 * @Description: 用户服务实现
 * @Author: panghairui
 * @Date: 2022/3/28 4:34 下午
 */
@Slf4j
@Service
public class UserServiceImpl implements UserService, ActivationConstant {

    @Autowired
    private UserDao userDao;

    @Autowired
    private MailClient mailClient;

    @Autowired
    private TemplateEngine templateEngine;

    @Autowired
    private LoginTicketDao loginTicketDao;

    @Value("${community.path.domain}")
    private String domain;

    @Value("${server.servlet.context-path}")
    private String contextPath;

    private final static String ERROR = "error";

    @Override
    public User findUserById(long id) {
        return userDao.selectById(id);
    }

    @Override
    public Map<String, Object> register(User user) {

        Map<String, Object> mp = new HashMap<>();

        // 校验参数
        String errorMsg = validParam(user, mp);
        if (StringUtils.isNotBlank(errorMsg)) {
            log.info("UserServiceImpl register errorMsg:{}", errorMsg);
            return mp;
        }

        // 验证账号
        User u = userDao.selectByName(user.getUsername());
        if (Objects.nonNull(u)) {
            mp.put("usernameMsg", "该账号已存在！");
            return mp;
        }

        // 验证邮箱
        u = userDao.selectByEmail(user.getEmail());
        if (Objects.nonNull(u)) {
            mp.put("emailMsg", "该邮箱已被注册！");
            return mp;
        }

        // 注册用户
        buildUserMsg(user);
        userDao.insertUser(user);

        // 给用户发送激活邮件
        Context context = new Context();
        context.setVariable("email", user.getEmail());
        String url = domain + contextPath + "/activation/" + user.getId() + "/" + user.getActivationCode();
        context.setVariable("url", url);
        String content = templateEngine.process("/mail/activation", context);
        mailClient.sendMail(user.getEmail(), "激活账号", content);

        return mp;
    }

    @Override
    public int activation(int userId, String code) {
        User user = userDao.selectById(userId);

        if (user.getStatus() == 1) {
            return ACTIVATION_REPEAT;
        } else if (user.getActivationCode().equals(code)) {
            userDao.updateStatus(userId, 1);
            return ACTIVATION_SUCCESS;
        } else {
            return ACTIVATION_FAILURE;
        }
    }

    @Override
    public Map<String, Object> login(String username, String password, int expiredSeconds) {

        Map<String, Object> mp = new HashMap<>();
        User user = userDao.selectByName(username);

        // 参数校验
        String msg = loginValid(user, username, password, mp);
        if (StringUtils.isNotBlank(msg)) {
            log.info("UserServiceImpl login error username:{} password:{}", username, password);
            return mp;
        }

        // 生成登录凭证
        LoginTicket loginTicket = new LoginTicket();
        loginTicket.setUserId(user.getId());
        loginTicket.setTicket(CommunityUtil.generateUUID());
        loginTicket.setStatus(0);
        loginTicket.setExpired(new Date(System.currentTimeMillis() + expiredSeconds * 1000L));
        loginTicketDao.insertLoginTicket(loginTicket);

        mp.put("ticket", loginTicket.getTicket());

        return mp;
    }

    @Override
    public void logout(String ticket) {
        loginTicketDao.updateStatus(ticket, 1);
    }

    @Override
    public LoginTicket findLoginTicket(String ticket) {
        return loginTicketDao.selectByTicket(ticket);
    }

    @Override
    public int updateHeader(long userId, String headerUrl) {
        return userDao.updateHeader(userId, headerUrl);
    }

    @Override
    public User findUserByName(String username) {
        return userDao.selectByName(username);
    }

    private String loginValid(User user, String username, String password, Map<String, Object> mp) {

        if (StringUtils.isBlank(username)) {
            mp.put("usernameMsg", "账号不能为空!");
            return ERROR;
        }

        if (StringUtils.isBlank(password)) {
            mp.put("passwordMsg", "密码不能为空!");
            return ERROR;
        }

        // 验证账号
        if (Objects.isNull(user)) {
            mp.put("usernameMsg", "该账号不存在!");
            return ERROR;
        }

        // 验证状态
        if (user.getStatus() == 0) {
            mp.put("usernameMsg", "该账号未激活!");
            return ERROR;
        }

        // 验证密码
        password = CommunityUtil.md5(password + user.getSalt());
        if (!user.getPassword().equals(password)) {
            mp.put("passwordMsg", "密码不正确!");
            return ERROR;
        }

        return "";
    }

    private void buildUserMsg(User user) {
        user.setSalt(CommunityUtil.generateUUID().substring(0, 5));
        user.setPassword(CommunityUtil.md5(user.getPassword() + user.getSalt()));
        user.setType(0);
        user.setStatus(0);
        user.setActivationCode(CommunityUtil.generateUUID());
        user.setHeaderUrl(String.format("http://images.nowcoder.com/head/%dt.png", new Random().nextInt(1000)));
        user.setCreateTime(new Date());
    }

    private String validParam(User user, Map<String, Object> mp) {

        if (Objects.isNull(user)) {
            throw new IllegalArgumentException("参数不能为空！");
        }

        if (StringUtils.isBlank(user.getUsername())) {
            mp.put("usernameMsg", "账号不能为空！");
            return "账号不能为空!";
        }

        if (StringUtils.isBlank(user.getPassword())) {
            mp.put("passwordMsg", "密码不能为空！");
            return "密码不能为空!";
        }

        if (StringUtils.isBlank(user.getEmail())) {
            mp.put("emailMsg", "邮箱不能为空！");
            return "邮箱不能为空!";
        }

        return "";

    }

}
