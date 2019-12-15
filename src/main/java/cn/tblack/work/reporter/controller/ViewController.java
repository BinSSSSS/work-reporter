package cn.tblack.work.reporter.controller;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ViewController {

	private static Logger log = LoggerFactory.getLogger(ViewController.class);

	@RequestMapping("/")
	public String home() {
		return "index.html";
	}

	@RequestMapping("/welcome.html")
	public String welcome(Authentication authentication) {

		if (authentication == null)
			return "redirect:login.html";

		if (isAdminRole(authentication)) {
			return "/admin/welcome.html";
		}

		return "/user/welcome.html";
	}

	@RequestMapping("/login.html")
	public String loginHtml() {
		return "/login";
	}

	@RequestMapping("/index.html")
	/**
	 * @--需要通过当前用户的权限来进行返回某一个特定的index页面。 @return
	 */

	public String index(Authentication authentication) {

		log.info("Authentication: " + authentication);

		// 如果当前访问的用户是未登录状态的话。那么则直接返回登录页面
		if (authentication == null) {
			return "redirect:login.html";
		}

		// 根据不同的权限来返回不同的index页面
		boolean isAdmin = isAdminRole(authentication);

		// 当是管理员登录的时候- 返回的是一个admin

		if (isAdmin)
			return "/index/admin/index";

		return "/index/other/index";

	}

	@RequestMapping(value = "/swagger-ui.html")
	public String swagger() {
		return "";
	}

	private boolean isAdminRole(Authentication authentication) {

		Collection<? extends GrantedAuthority> authroties = authentication.getAuthorities();

		Set<String> roles = new HashSet<>();
		for (GrantedAuthority authorze : authroties) {
			roles.add(authorze.getAuthority());
		}
		// 当是管理员登录的时候- 
		if (roles.contains("ROLE_ADMIN"))
			return true;

		return false;
	}
}
