package cn.tblack.work.reporter.gen.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.tblack.work.reporter.annotation.HasAnyRole;

@Controller
@RequestMapping("/gen-template")
@HasAnyRole
public class GenTemplateController {
	
	@RequestMapping(value = "/index.html")
	public String index() {
		
		return "/gen/gentemp";
	}
}
