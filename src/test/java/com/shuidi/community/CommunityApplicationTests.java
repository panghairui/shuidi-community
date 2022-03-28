package com.shuidi.community;

import com.shuidi.community.dao.UserDao;
import com.shuidi.community.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = CommunityApplication.class)
public class CommunityApplicationTests {

	@Autowired
	private UserDao userDao;

	@Test
	public void testUserMapper() {
		User user = new User();
		user.setUsername("test");
		user.setPassword("123456");
		user.setSalt("abs");
		user.setEmail("as");
		user.setCreateTime(new Date());
		int rows = userDao.insertUser(user);
		System.out.println(rows);
	}

}
