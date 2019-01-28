package cn.tedu.springboot.simple.controller;

import org.apache.ibatis.javassist.compiler.SyntaxError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.tedu.springboot.simple.entity.User;
import cn.tedu.springboot.simple.service.IUserService;
import cn.tedu.springboot.simple.util.ResponseResult;

@RestController
@RequestMapping("/user")
public class UserController {
	@Autowired
	IUserService service ;
	
	@PostMapping("/reg")
	public ResponseResult<Void> handleReg(User user){
			service.reg(user);
			return new ResponseResult<>(1, "注册成功!");	 
	
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	@ExceptionHandler(RuntimeException.class)
	public ResponseResult<Void> handlerException(Exception e){
		return new ResponseResult<>(2, e.getMessage());
	}
}
