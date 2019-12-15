package cn.tblack.work.reporter.res.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
//@HasAdminRole
@RequestMapping("/res")
public class ResourcesController {

	
	@RequestMapping(value = "/index.html")
	public String index() {
		return "/system/resources/index";
	}
}
