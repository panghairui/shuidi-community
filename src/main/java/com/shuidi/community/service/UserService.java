package com.shuidi.community.service;

import com.shuidi.community.entity.User;

import java.util.Map;

/**
 * @Description: 用户服务
 * @Author: panghairui
 * @Date: 2022/3/28 4:33 下午
 */
public interface UserService {

    /**
     * 查询用户数据
     */
    User findUserById(long id);

    /**
     * 用户注册
     */
    Map<String, Object> register(User user);

    /**
     * 激活账号
     */
    int activation(int userId, String code);

    /**
     * 用户登录
     */
    Map<String, Object> login(String username, String password, int expiredSeconds);

    /**
     * 退出登录
     */
    void logout(String ticket);

}
