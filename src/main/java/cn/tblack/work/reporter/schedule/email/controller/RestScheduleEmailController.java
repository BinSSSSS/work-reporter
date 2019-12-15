package cn.tblack.work.reporter.schedule.email.controller;

import static cn.tblack.work.reporter.constant.CustomBoolean.FALSE;
import static cn.tblack.work.reporter.constant.CustomBoolean.TRUE;
import static cn.tblack.work.reporter.properties.WebConfigProperties.UPLOAD_LOCATION;
import static cn.tblack.work.reporter.properties.WebConfigProperties.WRITE_BUFFER_SIZE;
import static cn.tblack.work.reporter.schedule.util.CronUtilsHelper.CRON_PARSER;

import java.io.File;
import java.util.Date;
import java.util.GregorianCalendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.cronutils.model.Cron;

import cn.tblack.work.reporter.quartz.entity.MailSender;
import cn.tblack.work.reporter.quartz.entity.Reminder;
import cn.tblack.work.reporter.quartz.entity.Schedule;
import cn.tblack.work.reporter.quartz.service.MailSenderService;
import cn.tblack.work.reporter.quartz.service.ReminderService;
import cn.tblack.work.reporter.quartz.service.ScheduleService;
import cn.tblack.work.reporter.result.ValidateResult;
import cn.tblack.work.reporter.result.WebResult;
import cn.tblack.work.reporter.schedule.ReminderScheduler;
import cn.tblack.work.reporter.schedule.job.ReminderTask;
import cn.tblack.work.reporter.schedule.util.CronUtilsHelper;
import cn.tblack.work.reporter.sys.entity.SysUser;
import cn.tblack.work.reporter.sys.service.SysUserService;
import cn.tblack.work.reporter.util.FileWriter;
import cn.tblack.work.reporter.util.WeightsUtils;

@RestController
@RequestMapping("/schedule/email")
public class RestScheduleEmailController {

	private static Logger log = LoggerFactory.getLogger(RestScheduleEmailController.class);

	@Autowired
	private MailSenderService mailSendService;
	@Autowired
	private ScheduleService scheduleService;
	@Autowired
	private ReminderService reminderService;
	@Autowired
	private ReminderScheduler reminderScheduler;
	@Autowired
	private SysUserService userService;
	

	@RequestMapping(value = "/verify-cron-expression", method = RequestMethod.POST)
	/**
	 * @验证某个Cron表达式是否合法
	 * @param cron
	 * @return
	 */
	public ValidateResult verifyCronExpression(String cron) {

		log.info("开始验证一个Cron表达式:  " + cron);

		ValidateResult result = new ValidateResult(false);
		try {

			Cron quartzCron = CRON_PARSER.parse(cron);
			quartzCron.validate();
			result.setValidate(true);
		} catch (Exception e) {
			log.error("用户[ " + SecurityContextHolder.getContext().getAuthentication().getName() + "]传递了一个非法的Cron表达式 ");
			log.error("出错信息为: " + e.getMessage());
		}

		return result;
	}

	@PostMapping(value = "/addReminder")
	public WebResult addReminder(
			MailSender mailSender, Schedule schedule, String delayCron,
			@RequestParam("attachment") MultipartFile file,
			Authentication auth) {
		WebResult result = new WebResult();
		
		try {
			
			//查找当前操作的用户对象
			SysUser user = userService.findByUsername(auth.getName());
			
			//拿到下次执行时间
			Date nextExecutionDate = CronUtilsHelper.nextExecutionDate(schedule.getCron());

			//创建一个提醒对象
			Reminder reminder = new Reminder();
			
			// 检查任务是否是一个重复执行的任务
			if (CronUtilsHelper.nextExecutionDateList(schedule.getCron(), nextExecutionDate).size() < 2) {

				// 表示是一个单次的提醒任务
				schedule.setRepeat(FALSE);
			} else {
				schedule.setRepeat(TRUE);
			}
			String fileName = file.getOriginalFilename();  //原始文件名
			// 如果存在附件的话，将该附件保存在本地
			if (file != null && !fileName.isEmpty()) {
				
				File dir = new File(UPLOAD_LOCATION + user.getUsername());

				// 如果当前文件夹不存在
				if (!dir.exists()) {
					// 创建文件夹
					dir.mkdirs();
				}
				//将文件名加上时间戳--
				fileName += "_" + WeightsUtils.secondTimeWeights(new GregorianCalendar());
				String savePath = dir.getAbsolutePath() + "/" + fileName;
				// 保存该文件
				FileWriter.writeToFile(file.getInputStream(), (int) file.getSize(), WRITE_BUFFER_SIZE,
						savePath);
				
				//设置该附件的地址
				mailSender.setAttachments(savePath);
				
				log.info("当前用户上传了一个附件: " + fileName + ",文件被保存在: " + savePath);
			}

			// 给数据赋值
			reminder.setDeprecated(FALSE);
			reminder.setCreateTime(new Date());
			reminder.setUserId(user.getId());
			reminder.setSchedule(schedule);
			mailSender.setSendTime(nextExecutionDate); // 计算到下次执行的时间并发送
			
			// 将数据插入到数据库中
			scheduleService.save(schedule);
			mailSendService.save(mailSender);
			reminder.setMailSender(mailSender);
			reminder.setSchedule(schedule);
			reminderService.save(reminder);
			
			
			// 直接创建一个ReminderTask任务
			ReminderTask task = new ReminderTask(reminder);

			// 表示的不是延时重复提醒- 那么则无需要创建DelayRemindJob对象
			if (delayCron == null) {

				reminderScheduler.addReminderJob(task);
			} else {

				// 是一个延时的调度任务，需要添加成为延时调度任务
				reminderScheduler.addDelayReminderJob(task, delayCron);
			}
			result.setMsg("添加成功");
			result.setSuccess(true);
		}catch(Exception e) {
			e.printStackTrace();
			log.error("添加一个提醒出错，出错信息为: " +  e.getMessage());
			result.setMsg("添加出错，服务器正忙~");
			result.setSuccess(false);
		}
		
		
		return result;
	}

}
