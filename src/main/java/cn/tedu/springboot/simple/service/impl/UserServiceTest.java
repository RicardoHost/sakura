package cn.tedu.springboot.simple.service.impl;

import org.apache.ibatis.javassist.compiler.SyntaxError;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import cn.tedu.springboot.simple.entity.User;
import cn.tedu.springboot.simple.service.IUserService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {
	@Autowired
	IUserService service ;
	@Test
	public void reg() {
		
		try {
			User user = new User();
			
			user.setUsername("rot");
			user.setPassword("1234");
			service.reg(user);
			
			System.err.println("注册成功");
		} catch (RuntimeException e) {		
			
			System.err.println(e.getMessage());
			System.err.println(111);
		}
	}
}
