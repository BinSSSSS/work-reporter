package cn.tblack.work.reporter.wechat.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;

@Api(tags = "微信标签控制器",hidden = true)
@RestController
@RequestMapping("/wechat/tags/")
public class RestWechatTagController {

	
	@RequestMapping("/page-list")
	public Object wechatTagsList() {
		
		return null;
	}
	
}
