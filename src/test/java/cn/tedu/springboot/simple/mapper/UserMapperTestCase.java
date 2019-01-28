package cn.tedu.springboot.simple.mapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import cn.tedu.springboot.simple.entity.User;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserMapperTestCase {

		@Autowired
		Usermapper usermapper;
		@Test
		public void addnew() {
			User user = new User();
			user.setUsername("boot");
			user.setPassword("8888");
			Integer rows= usermapper.addnew(user);
			System.err.println(rows);
		}
		@Test
		public void findByName() {
			User user = usermapper.findByUsername("root");
			System.err.println(user);
		}
}
