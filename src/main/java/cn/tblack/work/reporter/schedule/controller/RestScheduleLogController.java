package cn.tblack.work.reporter.schedule.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.tblack.work.reporter.page.LaYuiPage;
import cn.tblack.work.reporter.quartz.entity.ScheduleJobLog;
import cn.tblack.work.reporter.quartz.service.ScheduleJobLogService;

@RestController
@RequestMapping("/schedule/log")
public class RestScheduleLogController {

	private static Logger log =  LoggerFactory.getLogger(RestScheduleJobController.class);
	
	
	@Autowired
	private ScheduleJobLogService scheduleLogService;
	
	
	@RequestMapping(value = "/page-list")
	public LaYuiPage<ScheduleJobLog> getPageList(@RequestParam(name = "pageSize",defaultValue = "10") Integer pageSize,
			@RequestParam(name = "curPage",defaultValue = "1")Integer curPage){
		
		LaYuiPage<ScheduleJobLog> scheduleJobLPage = null;
		
		
		try {
			
			Pageable pageable = PageRequest.of(curPage - 1, pageSize);
			
			Page<ScheduleJobLog> pageimpl  = scheduleLogService.findAll(pageable);
			
			scheduleJobLPage = new LaYuiPage<>(pageimpl);
			
			scheduleJobLPage.setCount(Long.valueOf(scheduleLogService.count()).intValue());
		}catch(Exception e) {
			e.printStackTrace();
			log.error("查询ScheuleJobLog数据库出错，出错原因为:" + e.getMessage());
		}
		
		return scheduleJobLPage;
		
	}
}
