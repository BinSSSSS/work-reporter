package cn.tblack.work.reporter.social.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.tblack.work.reporter.annotation.HasAdminRole;

@RestController
@RequestMapping("/social")
@HasAdminRole
public class RestSocialController {

	
	@RequestMapping("/platform/info")
	Map<String,Object> getPlatformInfo(){
		
		return new HashMap<>();
	}
	
}
