package cn.tblack.work.reporter.social.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.tblack.work.reporter.annotation.NeedAnyRole;
import io.swagger.annotations.Api;

@Controller
@RequestMapping("/social")
//@HasAdminRole
@Api(tags = "社交管理视图控制器")
@NeedAnyRole
public class SocialController {

	@GetMapping("/index.html")
	public String index() {
		
		return  "/social/setting";
	}
	
	@GetMapping(value = "/qrcode.html")
	public String qrcode() {
		
		return "/qrcode/qrcode";
	}
}
