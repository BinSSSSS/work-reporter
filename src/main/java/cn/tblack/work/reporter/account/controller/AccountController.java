package cn.tblack.work.reporter.account.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.tblack.work.reporter.annotation.NeedAnyRole;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "账户相关视图控制器")
@Controller
@RequestMapping("/account")
@NeedAnyRole
public class AccountController {

	
	@ApiOperation(value = "修改密码视图")
	@GetMapping("/renew-password.html")
	public String accountSettings() {
		
		return "/index/account";
	}
	
	/**
	 * @=-=注销操作。
	 * @return
	 */
	@ApiOperation(value = "注销操作")
	@RequestMapping(value = "/logout",method = {RequestMethod.GET,RequestMethod.POST})
	public String logout(Authentication auth, HttpServletRequest request , HttpServletResponse response) {
		
		//在SpringSecurity中对该用户做注销操作
		new SecurityContextLogoutHandler().logout(request, response, auth);
		//清除Session内的数据
		request.getSession().removeAttribute("avatarUrl");
		
		//重定向到登录页面--
		return "redirect:/login.html";
	}
}
