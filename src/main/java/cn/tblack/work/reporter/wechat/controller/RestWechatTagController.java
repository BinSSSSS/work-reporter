package cn.tblack.work.reporter.wechat.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/wechat/tags/")
public class RestWechatTagController {

	
	@RequestMapping("/page-list")
	public Object wechatTagsList() {
		
		return null;
	}
	
}
