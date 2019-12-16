package cn.tblack.work.reporter.wechat.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/wechat/menu")
public class RestWechatMenuController {

	@RequestMapping("/list")
	public Object menuList() {
		
		return null;
	}
}
