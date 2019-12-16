package cn.tblack.work.reporter.schedule.email.controller;

import static cn.tblack.work.reporter.constant.CustomBoolean.FALSE;
import static cn.tblack.work.reporter.constant.CustomBoolean.TRUE;

import org.quartz.JobKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.tblack.work.reporter.email.sender.EmailSender;
import cn.tblack.work.reporter.page.LaYuiPage;
import cn.tblack.work.reporter.quartz.entity.Reminder;
import cn.tblack.work.reporter.quartz.service.MailSenderService;
import cn.tblack.work.reporter.quartz.service.ReminderService;
import cn.tblack.work.reporter.result.WebResult;
import cn.tblack.work.reporter.schedule.ReminderScheduler;
import cn.tblack.work.reporter.sys.entity.SysUser;
import cn.tblack.work.reporter.sys.service.SysUserService;
import cn.tblack.work.reporter.user.util.UserInjectionUtils;

/**
 * @__!_!用于进行提醒管理的控制器
 * @author TD唐登
 * @Date:2019年12月10日
 * @Version: 1.0(测试版)
 */
@RestController
@RequestMapping("/reminder/email")
public class RestReminderController {

	private static Logger log = LoggerFactory.getLogger(RestReminderController.class);

	@Autowired
	private EmailSender emailSender;

	@Autowired
	private ReminderService reminderService;

	@Autowired
	private MailSenderService mailSenderService;

	@Autowired
	private SysUserService userService;

	@Autowired
	private ReminderScheduler reminderScheduler;

	@Autowired
	private UserInjectionUtils userInjectionUtils;

	@RequestMapping(value = "/page-list")
	public LaYuiPage<Reminder> getPageList(@RequestParam(name = "page", defaultValue = "1") Integer page,
			@RequestParam(name = "limit", defaultValue = "10") Integer limit,
			@RequestParam(name = "dbName", defaultValue = "") String dbName) {

		LaYuiPage<Reminder> reminderPage = null;

		log.info("进行分页查询");
		try {

			Pageable pageable = PageRequest.of(page - 1, limit);
			
			reminderPage = reminderService.findAll(pageable);

//			log.info(reminderPage.getData().toString());
			

		} catch (Exception e) {
			e.printStackTrace();
			log.error("查询Reminder分页列表出错，出错原因为: " + e.getMessage());
		}

		return reminderPage;

	}

	@RequestMapping(value = "/update")
	public WebResult updateTemplate(@RequestBody Reminder reminder) {

		WebResult result = new WebResult();

		log.info("传递的reminder信息为: " + reminder);
		try {

			Reminder orgReminder = reminderService.findById(reminder.getId());

			// 如果需要更新的对象已经不存在了，那么则返回出错信息。
			if (orgReminder == null || mailSenderService.findById(reminder.getMailSender().getId()) == null) {
				result.setMsg("要更新的对象不存在");
				result.setSuccess(false);
				return result;
			}

			// 动态的更新， 为空的数据不进行更新。
			// 目前对提醒的更新只能是关于发送内容方面的，所以只需要更新MailReminder对象即可。
//			reminderService.save(reminder);
			mailSenderService.save(reminder.getMailSender());
			result.setMsg("更新成功!");
			result.setSuccess(true);

		} catch (Exception e) {
			e.printStackTrace();
			log.error("更新Id[" + reminder.getId() + "]Reminder信息出错，出错原因为:" + e.getMessage());
			result.setMsg("更新失败！服务器正忙~");
			result.setSuccess(false);
		}

		return result;

	}

	@RequestMapping(value = "/delete")
	public WebResult deleteTemplate(String ids, Authentication auth) {

		WebResult result = new WebResult();

		String[] idArr = ids.split(",");

		try {

			SysUser user = userService.findByUsername(auth.getName());

			//如果该用户是第三方登录，则会进行信息的注入
			user = userInjectionUtils.injectSysUser(user, auth);

			for (String id : idArr) {
				reminderService.deleteById(Integer.valueOf(id));
				// 在当前的调度任务中进行删除本调度任务
				reminderScheduler.killReminderJob(new JobKey("" + id, user.getId()));
				log.info("用户 [ " + user.getUsername() + "]删除了一个提醒对象 " + id);
			}

			result.setMsg("删除成功!");
			result.setSuccess(true);

		} catch (Exception e) {
			e.printStackTrace();
			log.error("删除id为[" + ids + "]的Reminder的对象失败！失败原因为: " + e.getMessage());
			result.setMsg("删除对象失败，服务器正忙~");
			result.setSuccess(false);
		}

		return result;
	}

	@RequestMapping(value = "/get")
	public Reminder getTemplate(Integer id) {

		Reminder reminder = null;

		try {

			reminder = reminderService.findById(id);

		} catch (Exception e) {
			e.printStackTrace();
			log.error("查询Id为:[" + id + "]的Reminder出错，出错信息为: " + e.getMessage());
		}
		return reminder;
	}

	@RequestMapping(value = "/pause", method = RequestMethod.POST)
	/**
	 * @-=-=暂停正在执行的调度任务
	 * @param id
	 * @return
	 */
	public WebResult pauseReminder(String ids, Authentication auth) {

		WebResult result = new WebResult();

		try {
			String[] idArr = ids.split(",");
			SysUser user = userService.findByUsername(auth.getName());
			
			//如果该用户是第三方登录，则会进行信息的注入
			user = userInjectionUtils.injectSysUser(user, auth);
			
			for (String id : idArr) {
				// 从数据库中更改reminder的状态
				reminderService.updateDeprecated(Integer.valueOf(id), TRUE);

				// 在当前的调度任务中进行暂停本调度任务--这里需要判断- 本调度任务是否是一个延时的调度任务
				// 如果任务调度中心找不到该调度任务，那么则调度任务可能是一个延时调度任务
				// 暂时直接使用了全部的调度任务判断是否是一个延时调度任务
				reminderScheduler.pauseDelayReminderJob(new JobKey("" + id, user.getId()));
				log.info("用户 [ " + user.getUsername() + "]暂停了一个提醒对象 " + id);
			}

			result.setMsg("操作成功");
			result.setSuccess(true);
		} catch (Exception e) {
			log.error("暂停一个提醒对象失败:  " + ids + "； 出错信息: " + e.getMessage());
			result.setMsg("操作失败");
			result.setSuccess(false);
			e.printStackTrace();
		}

		return result;
	}

	@RequestMapping(value = "/start", method = RequestMethod.POST)
	/**
	 * @-=-=开始一个已经暂停了的调度任务
	 * @param id
	 * @return
	 */
	public WebResult startReminder(String ids, Authentication auth) {

		WebResult result = new WebResult();

		try {
			String[] idArr = ids.split(",");
			// 拿到当前用户
			SysUser user = userService.findByUsername(auth.getName());
			
			//如果该用户是第三方登录，则会进行信息的注入
			user = userInjectionUtils.injectSysUser(user, auth);
			
			for (String id : idArr) {
				// 从数据库中更改reminder的状态
				reminderService.updateDeprecated(Integer.valueOf(id), FALSE);
				reminderScheduler.startDelayReminderJob(new JobKey("" + id, user.getId()));

				log.info("用户 [ " + user.getUsername() + "]开始了一个提醒对象 " + id);
			}

			result.setMsg("操作成功");
			result.setSuccess(true);
		} catch (Exception e) {
			log.error("开始一个提醒对象失败:  " + ids + "； 出错信息: " + e.getMessage());
			result.setMsg("操作失败");
			result.setSuccess(false);
			e.printStackTrace();
		}

		return result;
	}

	/**
	 * @-=-=-发送一个测试的邮件到指定的邮箱内
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/run", method = RequestMethod.POST)
	public WebResult sendTestReminder(String ids) {

		WebResult result = new WebResult();

		try {
			String[] idArr = ids.split(",");

			for (String id : idArr) {
				// 拿到当前提醒
				Reminder reminder = reminderService.findById(Integer.valueOf(id));

				// 向该提醒的内容类发送一封测试邮件
				emailSender.send(reminder.getMailSender());

				log.info("向邮箱地址 [ " + reminder.getMailSender().getSendTo() + "]发送了一封测试邮件 " + id);
			}

			result.setMsg("发送成功");
			result.setSuccess(true);
		} catch (Exception e) {
			log.error("发送测试邮件:  " + ids + "； 出错信息: " + e.getMessage());
			result.setMsg("发送失败");
			result.setSuccess(false);
			e.printStackTrace();
		}

		return result;
	}
}
