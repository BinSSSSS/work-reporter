package cn.tblack.work.reporter.error.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @_!_!用于错误页面的响应
 * @author TD唐登
 * @Date:2019年12月11日
 * @Version: 1.0(测试版)
 */
@Controller
@RequestMapping("/error")
public class ErrorController {

	
	@RequestMapping(value = "/404.html")
	public String notFound() {
		
		return "/error/404";
	}
	
	@RequestMapping("/500.html")
	public String error() {
		
		return "/error/500";
	}
	
	@RequestMapping("/403.html")
	public String noPermission() {
		
		return "/error/403";
	}
}
