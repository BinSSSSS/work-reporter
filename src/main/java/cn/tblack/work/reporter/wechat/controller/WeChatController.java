package cn.tblack.work.reporter.wechat.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/wechat")
public class WeChatController {

	@RequestMapping("/tag-manager.html")
	public String tagManager() {
		return "/weixin/tags";
	}
	
	@RequestMapping("/menu-manager.html")
	public String menuManager() {
		return "/weixin/menu";
	}
}
