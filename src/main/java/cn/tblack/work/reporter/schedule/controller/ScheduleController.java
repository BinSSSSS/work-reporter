package cn.tblack.work.reporter.schedule.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/schedule")
public class ScheduleController {
	
	@RequestMapping("/task.html")
	public String task() {
		
		return "/quartz/job/index";
	}
	
	@RequestMapping("/log.html")
	public String log() {
		return "/quartz/log/index";
	}
}

