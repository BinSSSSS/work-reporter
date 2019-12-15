package cn.tblack.work.reporter.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
//@HasAdminRole
public class AdminController {

	@RequestMapping("/welcome.html")
	public String admidWelcome() {
		return "/index/admin/welcome";
	}
	
}
