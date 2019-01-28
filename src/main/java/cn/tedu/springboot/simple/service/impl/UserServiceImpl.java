package cn.tedu.springboot.simple.service.impl;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.tedu.springboot.simple.entity.User;
import cn.tedu.springboot.simple.mapper.Usermapper;
import cn.tedu.springboot.simple.service.IUserService;

@Service
public class UserServiceImpl implements IUserService{
	@Autowired 
	private   Usermapper usermapper;

	@Override
	public void reg(User user) {
		String name = user.getUsername();
		User data=usermapper.findByUsername(name);
		
		//从数据库中查找该用户名
		//判断用户名是否存在
		if(data==null) {		
			//如果不存在，返回null
			usermapper.addnew(user);		
		}else {
			//如果存在
			throw new RuntimeException("注册失败！你所注册的用户"+name+"已被注册!");
		}	
	}
	
}
