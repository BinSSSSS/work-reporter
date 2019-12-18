package cn.tblack.work.reporter.res.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.tblack.work.reporter.annotation.NeedAnyRole;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;


@Api(tags = "菜单管理视图控制器")
@Controller
//@HasAdminRole
@RequestMapping("/res")
@NeedAnyRole
public class ResourcesController {

	
	@ApiOperation(value = "菜单管理页面")
	@GetMapping(value = "/index.html")
	public String index() {
		return "/system/resources/index";
	}
}
