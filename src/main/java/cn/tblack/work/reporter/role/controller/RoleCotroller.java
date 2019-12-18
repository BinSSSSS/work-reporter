package cn.tblack.work.reporter.role.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.tblack.work.reporter.annotation.NeedAnyRole;
import io.swagger.annotations.Api;


@Api(tags = "角色管理视图控制器")
@Controller
//@HasAdminRole
@RequestMapping("/role")
@NeedAnyRole
public class RoleCotroller {

	@GetMapping(value = "/index.html")
	public String index() {
		return "/system/role/index";
	}
}
