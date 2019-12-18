package cn.tblack.work.reporter.schedule.email.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.tblack.work.reporter.annotation.NeedAnyRole;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "定是邮件提醒视图控制器")
@Controller
@RequestMapping("/schedule/email")
@NeedAnyRole
public class ScheduleEmailController {

	
	/**
	 * @~——~添加定时邮件提醒页面
	 * @return
	 */
	@ApiOperation(value = "添加定时邮件提醒页面")
	@GetMapping(value = "/index.html")
	public String index() {
		
		return "/quartz/email/add-reminder";
	}
	
	@ApiOperation(value = "定是邮件提醒列表页面")
	@GetMapping(value = "/list.html")
	public String reminderList() {
		
		return "/quartz/email/reminder-list";
	}
}
