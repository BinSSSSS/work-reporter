package cn.tblack.work.reporter.error.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @_!_!用于错误页面的响应
 * @author TD唐登
 * @Date:2019年12月11日
 * @Version: 1.0(测试版)
 */
@Api(tags = "错误页面视图控制器")
@Controller
@RequestMapping("/error")
public class ErrorController {

	
	@ApiOperation(value = "404未找到")
	@RequestMapping(value = "/404.html",method = {RequestMethod.POST,RequestMethod.GET})
	public String notFound() {
		
		return "/error/404";
	}
	
	@ApiOperation(value = "500服务器出错")
	@RequestMapping(value = "/500.html",method = {RequestMethod.POST,RequestMethod.GET})
	public String error() {
		
		return "/error/500";
	}
	@ApiOperation(value = "403无权限")
	@RequestMapping(value = "/403.html",method = {RequestMethod.POST,RequestMethod.GET})
	public String noPermission() {
		
		return "/error/403";
	}
}
