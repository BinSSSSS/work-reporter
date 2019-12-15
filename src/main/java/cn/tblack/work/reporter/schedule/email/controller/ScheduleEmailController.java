package cn.tblack.work.reporter.schedule.email.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/schedule/email")
public class ScheduleEmailController {

	
	/**
	 * @~——~添加定时邮件提醒页面
	 * @return
	 */
	@RequestMapping(value = "/index.html")
	public String index() {
		
		return "/quartz/email/add-reminder";
	}
	
	@RequestMapping(value = "/list.html")
	public String reminderList() {
		
		return "/quartz/email/reminder-list";
	}
}
