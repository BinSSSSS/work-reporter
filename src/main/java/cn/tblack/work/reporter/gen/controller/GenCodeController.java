package cn.tblack.work.reporter.gen.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.tblack.work.reporter.annotation.NeedAnyRole;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;


@Controller
@RequestMapping("/gen-code")
@Api(tags = "生成代码视图控制器")
@NeedAnyRole
public class GenCodeController {

	
	@ApiOperation(value = "生成代码页面")
	@GetMapping("/index.html")
	public String index() {
		
		return "/gen/index";
	}
	
	
}
