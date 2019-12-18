package cn.tblack.work.reporter.schedule.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.tblack.work.reporter.annotation.NeedAnyRole;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "定时任务管理视图控制器")
@Controller
@RequestMapping("/schedule")
@NeedAnyRole
public class ScheduleController {
	
	@ApiOperation(value = "定时任务管理页面")
	@GetMapping("/task.html")
	public String task() {
		
		return "/quartz/job/index";
	}
	
	@ApiOperation(value = "定时任务日志管理页面")
	@GetMapping("/log.html")
	public String log() {
		return "/quartz/log/index";
	}
}

