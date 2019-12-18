package cn.tblack.work.reporter.oss.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.tblack.work.reporter.annotation.NeedAnyRole;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "存储桶配置视图控制器")
@Controller
@RequestMapping("/oss/config")
@NeedAnyRole
public class OssConfigController {

	
	@ApiOperation(value = "存储桶配置页面")
	@GetMapping("/index.html")
	public String index() {
		
		return "/system/ossconfig/config";
	}
}
