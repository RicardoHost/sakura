package cn.tedu.springboot.simple.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController  
@RequestMapping("/hello")
public class HelloController {

		@GetMapping("/index")
		public String ShowIndex() {
			return "hello,spring boot.";
		}
}
