package cn.tblack.work.reporter.util;

import cn.tblack.work.reporter.email.sender.EmailSender;

public class EmailSenderUtils {

	private static EmailSender emailSender;

	public static EmailSender getEmailSender() {
		return emailSender;
	}

	public static void setEmailSender(EmailSender emailSender) {
		EmailSenderUtils.emailSender = emailSender;
	}
}
