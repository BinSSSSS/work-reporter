package cn.tblack.work.reporter.report.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.tblack.work.reporter.annotation.NeedAnyRole;
import io.swagger.annotations.Api;

@Api(tags = "数据表报告管理",hidden =  true)
@Controller
@RequestMapping("/report/table-manager")
@NeedAnyRole
public class RTableManagerController {

	
	@RequestMapping("/index.html")
	public String index() {
		
		return "/report/deptment";
	}
}
