package cn.tblack.work.reporter.social.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.tblack.work.reporter.annotation.NeedAnyRole;
import io.swagger.annotations.Api;


@RestController
@RequestMapping("/social")
//@HasAdminRole
@Api(tags = "社交平台管理控制器",hidden =  true)
@NeedAnyRole
public class RestSocialController {

	
	@RequestMapping(value = "/platform/info",method = {RequestMethod.POST,RequestMethod.GET})
	Map<String,Object> getPlatformInfo(){
		
		return new HashMap<>();
	}
	
}
