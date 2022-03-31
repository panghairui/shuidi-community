package com.shuidi.community.service;

import com.shuidi.community.entity.Message;

import java.util.List;

/**
 * @Description: 私信服务
 * @Author: panghairui
 * @Date: 2022/3/31 9:28 上午
 */
public interface MessageService {

    List<Message> findConversations(long userId, int offset, int limit);

    long findConversationCount(long userId);

    List<Message> findLetters(String conversationId, int offset, int limit);

    long findLetterCount(String conversationId);

    long findLetterUnreadCount(long userId, String conversationId);

    int addMessage(Message message);

    int readMessage(List<Long> ids);

}
