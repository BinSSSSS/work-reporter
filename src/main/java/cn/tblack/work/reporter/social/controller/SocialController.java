package cn.tblack.work.reporter.social.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.tblack.work.reporter.annotation.HasAdminRole;

@Controller
@RequestMapping("/social")
@HasAdminRole
public class SocialController {

	@RequestMapping("/index.html")
	public String index() {
		
		return  "/social/setting";
	}
	
	@RequestMapping(value = "/qrcode.html")
	public String qrcode() {
		
		return "/qrcode/qrcode";
	}
}
