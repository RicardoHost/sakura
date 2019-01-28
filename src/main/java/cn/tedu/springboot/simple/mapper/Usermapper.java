package cn.tedu.springboot.simple.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import cn.tedu.springboot.simple.entity.User;

@Mapper
public interface Usermapper {
	
	Integer addnew(User user);
	List<User> findAll();
	User findByUsername(String usernmae);
}
