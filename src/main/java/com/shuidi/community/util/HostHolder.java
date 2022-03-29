package com.shuidi.community.util;

import com.shuidi.community.entity.User;
import org.springframework.stereotype.Component;

/**
 * @Description: 持有用户信息，用于代替 session 对象
 * @Author: panghairui
 * @Date: 2022/3/29 8:13 下午
 */
@Component
public class HostHolder {

    private ThreadLocal<User> users = new ThreadLocal<>();

    public void setUser(User user) {
        users.set(user);
    }

    public User getUser() {
        return users.get();
    }

    public void clear() {
        users.remove();
    }

}
