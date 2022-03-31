package com.shuidi.community.service.impl;

import com.shuidi.community.dao.MessageDao;
import com.shuidi.community.entity.Message;
import com.shuidi.community.service.MessageService;
import com.shuidi.community.util.SensitiveFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import java.util.List;

/**
 * @Description: 私信服务实现
 * @Author: panghairui
 * @Date: 2022/3/31 9:30 上午
 */
@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageDao messageDao;

    @Autowired
    private SensitiveFilter sensitiveFilter;

    @Override
    public List<Message> findConversations(long userId, int offset, int limit) {
        return messageDao.selectConversations(userId, offset, limit);
    }

    @Override
    public long findConversationCount(long userId) {
        return messageDao.selectConversationCount(userId);
    }

    @Override
    public List<Message> findLetters(String conversationId, int offset, int limit) {
        return messageDao.selectLetters(conversationId, offset, limit);
    }

    @Override
    public long findLetterCount(String conversationId) {
        return messageDao.selectLetterCount(conversationId);
    }

    @Override
    public long findLetterUnreadCount(long userId, String conversationId) {
        return messageDao.selectLetterUnreadCount(userId, conversationId);
    }

    @Override
    public int addMessage(Message message) {
        message.setContent(HtmlUtils.htmlEscape(message.getContent()));
        message.setContent(sensitiveFilter.filter(message.getContent()));
        return messageDao.insertMessage(message);
    }

    @Override
    public int readMessage(List<Long> ids) {
        return messageDao.updateStatus(ids, 1);
    }


}
