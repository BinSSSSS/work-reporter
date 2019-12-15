package cn.tblack.work.reporter.report.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/report/template")
public class RTableTemplateController {

	@RequestMapping("/index.html")
	public String index() {
		
		return "/report/rtemplate";
	}
}
