package com.shuidi.community;

import com.shuidi.community.util.MailClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Description: 邮件测试
 * @Author: panghairui
 * @Date: 2022/3/29 9:31 上午
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = CommunityApplication.class)
public class MailTest {

    @Autowired
    private MailClient mailClient;

    @Test
    public void textMail() {
        mailClient.sendMail("3206837094@qq.com", "TEST", "邮件测试");
    }

}
