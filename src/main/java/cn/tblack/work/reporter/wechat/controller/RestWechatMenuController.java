package cn.tblack.work.reporter.wechat.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
@Api(tags = "微信菜单控制器",hidden = true)
@RestController
@RequestMapping("/wechat/menu")
public class RestWechatMenuController {

	@RequestMapping("/list")
	public Object menuList() {
		
		return null;
	}
}
