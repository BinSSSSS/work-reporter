package cn.tblack.work.reporter.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.tblack.work.reporter.annotation.NeedAnyRole;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "管理员视图控制器")
@Controller
@RequestMapping("/admin")
@NeedAnyRole("ROLE_ADMIN")
public class AdminController {

	@ApiOperation(value = "管理员欢迎页面")
	@GetMapping("/welcome.html")
	public String admidWelcome() {
		return "/index/admin/welcome";
	}
	
}
