package cn.tblack.work.reporter.schedule.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.tblack.work.reporter.annotation.NeedAnyRole;
import cn.tblack.work.reporter.page.LaYuiPage;
import cn.tblack.work.reporter.quartz.entity.ScheduleJob;
import cn.tblack.work.reporter.quartz.service.ScheduleJobService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags ="定时任务管理控制器")
@RestController
@RequestMapping("/schedule/job")
@NeedAnyRole
public class RestScheduleJobController {

	
	private static Logger log = LoggerFactory.getLogger(RestScheduleJobController.class);
	
	
	@Autowired
	private ScheduleJobService scheduleJobService;
	
	@ApiOperation("定时任务分页列表")
	@RequestMapping(value = "/page-list",method = {RequestMethod.POST,RequestMethod.GET})
	public LaYuiPage<ScheduleJob> getPageList(@RequestParam(name = "limit",defaultValue = "10") Integer pageSize,
			@RequestParam(name = "page",defaultValue = "1")Integer curPage){
		
		LaYuiPage<ScheduleJob> scheduleJobLPage = null;
		
		try {
			
			Pageable pageable = PageRequest.of(curPage - 1, pageSize);
			
			scheduleJobLPage  = scheduleJobService.findAll(pageable);
			
		}catch(Exception e) {
			e.printStackTrace();
			log.error("查询ScheuleJob数据库出错，出错原因为:" + e.getMessage());
		}
		
		return scheduleJobLPage;
		
	}
}
