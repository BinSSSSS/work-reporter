package cn.tblack.work.reporter.gen.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/gen-code")
public class GenCodeController {

	
	@RequestMapping("/index.html")
	public String index() {
		
		return "/gen/index";
	}
	
	
}
