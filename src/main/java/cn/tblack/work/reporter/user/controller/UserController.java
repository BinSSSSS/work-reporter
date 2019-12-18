package cn.tblack.work.reporter.user.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.tblack.work.reporter.annotation.NeedAnyRole;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;


/**
 * @-……-对于用戶管理的页面返回
 * @author TD唐登
 * @Date:2019年11月25日
 * @Version: 1.0(测试版)
 */
@Controller
@RequestMapping("/user")
//@HasAdminRole  
@Api(tags = "用户管理视图控制器")
@NeedAnyRole
public class UserController {
	
	/**用户管理页面*/
	@ApiOperation(value = "用户管理页面")
	@GetMapping("/index.html")
	public String userList() {

		return "/system/user/index";
	}
	@ApiOperation(value = "用户欢迎页面")
	@GetMapping("/welcome.html")
	public String userWelcome() {
		
		return "/index/other/welcome";
	}
	@ApiOperation(value = "用户后台页面头部布局")
	@GetMapping("/head.html")
	public String head() {
		
		return "/index/other/head";
	}
	@ApiOperation(value = "用户后台页面菜单")
	@GetMapping("/menu.html")
	public String menu() {
		return "/index/other/menu";
	}
}
