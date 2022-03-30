package com.shuidi.community;

import com.shuidi.community.util.SensitiveFilter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Description: 敏感词测试
 * @Author: panghairui
 * @Date: 2022/3/30 10:01 上午
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = CommunityApplication.class)
public class SensitiveTest {

    @Autowired
    private SensitiveFilter sensitiveFilter;

    @Test
    public void testSensitive() {
        String text = "这里可以赌博，可以嫖娼，可以吸毒";
        text = sensitiveFilter.filter(text);
        System.out.println(text);
    }

}
