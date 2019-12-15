package cn.tblack.work.reporter.role.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
//@HasAdminRole
@RequestMapping("/role")
public class RoleCotroller {

	@RequestMapping(value = "/index.html")
	public String index() {
		return "/system/role/index";
	}
}
