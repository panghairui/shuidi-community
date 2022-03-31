package com.shuidi.community.dao;

import com.shuidi.community.entity.Message;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Description: 私信数据访问层
 * @Author: panghairui
 * @Date: 2022/3/31 9:21 上午
 */
@Mapper
public interface MessageDao {

    /**
     * 查询当前用户的会话列表，针对每个会话只返回一条最新的
     */
    List<Message> selectConversations(long userId, int offset, int limit);

    /**
     * 查询当前用户会话数量
     */
    long selectConversationCount(long userId);

    /**
     * 查询某个会话所包含的私信列表
     */
    List<Message> selectLetters(String conversationId, int offset, int limit);

    /**
     * 查询某个会话所包含的私信数量
     */
    long selectLetterCount(String conversationId);

    /**
     * 查询未读私信的数量
     */
    long selectLetterUnreadCount(long userId, String conversationId);

    /**
     * 新增消息
     */
    int insertMessage(Message message);

    /**
     * 修改消息的状态
     */
    int updateStatus(List<Long> ids, int status);

}
