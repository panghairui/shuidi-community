package com.shuidi.community.dao;

import com.shuidi.community.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Description: 用户数据访问层
 * @Author: panghairui
 * @Date: 2022/3/28 11:15 上午
 */
@Mapper
public interface UserDao {

    User selectById(long id);

    User selectByName(String userName);

    User selectByEmail(String email);

    int insertUser(User user);

    int updateStatus(long id, int status);

    int updateHeader(long id, String headerUrl);

    int updatePassword(long id, String password);

}
