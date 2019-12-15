package cn.tblack.work.reporter.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.stereotype.Component;
import org.springframework.web.WebApplicationInitializer;

import cn.tblack.work.reporter.email.MyEmailProperties;
import cn.tblack.work.reporter.email.sender.EmailSender;
import cn.tblack.work.reporter.schedule.ReminderScheduler;
import cn.tblack.work.reporter.schedule.util.SchedulerUtils;
import cn.tblack.work.reporter.util.EmailSenderUtils;

/**
 * @ -用于初始化和注入Web应用的一些对象
 * @author TD唐登
 * @Date:2019年11月21日
 * @Version: 1.0(测试版)
 */
@Component
public class WebInitListener implements WebApplicationInitializer{

	private static Logger log = LoggerFactory.getLogger(WebInitListener.class);
	
	@Autowired
	private MailProperties mailProperties;
	
	private ReminderScheduler reminderScheduler;
	
	private EmailSender emailSender;
	
	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		
		log.info("注入一个MailProperties对象: " + mailProperties);
		log.info("注入一个ReminderScheduler对象: " + reminderScheduler);
		log.info("注入一个EmailSender对象: " + emailSender);
		
		MyEmailProperties.setMailProperties(mailProperties);
		SchedulerUtils.setReminderScheduler(reminderScheduler);
		EmailSenderUtils.setEmailSender(emailSender);
	}
}
