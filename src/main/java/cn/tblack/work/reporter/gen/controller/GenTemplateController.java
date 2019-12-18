package cn.tblack.work.reporter.gen.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.tblack.work.reporter.annotation.NeedAnyRole;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Controller
@RequestMapping("/gen-template")
@Api(tags = "生成代码模板管理视图控制器")
@NeedAnyRole
public class GenTemplateController {
	
	@ApiOperation(value = "生成代码模板视图页面")
	@GetMapping(value = "/index.html")
	public String index() {
		
		return "/gen/gentemp";
	}
}
