package cn.tblack.work.reporter.email;

import org.springframework.boot.autoconfigure.mail.MailProperties;

/**
 * @定义着邮件发送的一些配置常量
 * @author TD唐登
 * @Date:2019年11月20日
 * @Version: 1.0(测试版)
 */
public class MyEmailProperties {
	
	/** smtp协议*/
	public static final String MAIL_SMTP_PROTOCOL = "smtp";
	
	/** pop3协议*/
	public static final String MAIL_POP3_PROTOCOL = "pop3";
	
	/** smtp协议QQ邮箱服务地址*/
	public static final String MAIL_SMTP_HOST_QQ = "smtp.qq.com";
	
	/** smtp协议163邮箱服务地址*/
	public static final String MAIL_SMTP_HOST_163 = "smtp.163.com";
	
	/** pop3协议QQ邮箱地址地址*/
	public static final String MAIL_POP_HOST_QQ = "pop.qq.com";
	
	/** pop3协议163邮箱地址*/
	public static final String MAIL_POP_HOST_163 = "pop.163.com";
	
	/** SSL安全协议pop3端口*/
	public static final String MAIL_SSL_POP3_PORT = "995";
	
	/** SSL安全协议smtp端口*/
	public static final String MAIL_SSL_SMTP_PORT = "465";
	
	/** 无SSL安全协议pop3端口*/
	public static final String MAIL_POP3_PORT = "110";
	
	/** 无SSL安全协议smtp端口*/
	public static final String MAIL_SMTP_PORT = "25";
	
	
	public static final String MAIL_RECEIVER_FLODER = "INBOX";


	/**在配置文件中配置的Mail相关属性*/
	private static MailProperties mailProperties;
	
	
	public static MailProperties getMailProperties() {
		return mailProperties;
	}


	public static void setMailProperties(MailProperties mailProperties) {
		MyEmailProperties.mailProperties = mailProperties;
	}


}
