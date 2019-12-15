package cn.tblack.work.reporter.user.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * @-……-对于用戶管理的页面返回
 * @author TD唐登
 * @Date:2019年11月25日
 * @Version: 1.0(测试版)
 */
@Controller
@RequestMapping("/user")
//@HasAdminRole  
public class UserController {
	
	/**用户管理页面*/
	@RequestMapping("/index.html")
	public String userList() {

		return "/system/user/index";
	}
	
	@RequestMapping("/welcome.html")
	public String userWelcome() {
		
		return "/index/other/welcome";
	}
	
	@RequestMapping("/head.html")
	public String head() {
		
		return "/index/other/head";
	}
	
	@RequestMapping("/menu.html")
	public String menu() {
		return "/index/other/menu";
	}
}
