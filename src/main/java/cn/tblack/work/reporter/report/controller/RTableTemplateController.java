package cn.tblack.work.reporter.report.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.tblack.work.reporter.annotation.NeedAnyRole;
import io.swagger.annotations.Api;

@Api(tags = "数据表模板",hidden =  true)
@Controller
@RequestMapping("/report/template")
@NeedAnyRole
public class RTableTemplateController {

	@RequestMapping("/index.html")
	public String index() {
		
		return "/report/rtemplate";
	}
}
