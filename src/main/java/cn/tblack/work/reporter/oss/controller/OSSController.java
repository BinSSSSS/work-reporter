package cn.tblack.work.reporter.oss.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/oss")
public class OSSController {

	@RequestMapping("/index.html")
	public String index() {
		
		return "/system/ossconfig/oss";
	}
}
