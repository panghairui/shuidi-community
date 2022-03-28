package com.shuidi.community.service.impl;

import com.shuidi.community.dao.UserDao;
import com.shuidi.community.entity.User;
import com.shuidi.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description: 用户服务实现
 * @Author: panghairui
 * @Date: 2022/3/28 4:34 下午
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public User findUserById(long id) {
        return userDao.selectById(id);
    }
}
