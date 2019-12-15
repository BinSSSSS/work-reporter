package cn.tblack.work.reporter.email;

/**
 * @VerificationEmail的一些默认内容
 * @author TD唐登
 * @Date:2019年10月31日
 * @Version: 1.0(测试版)
 */
public class VMailContent {

	/**
	 * @~0~注册验证邮件的标题信息
	 */
	public static final String REGISTER_TITLE = "WorkReporter验证码";

	/**
	 * @^0^注册验证码邮件的头内容
	 */
	public static final String REGISTER_HEADER = "你收到了一份惊喜,当前的惊喜注册验证码为: ";

	/**
	 * @^~^验证码邮件的尾内容
	 */
	public static final String VMAIL_FOOTER = "<br><strong style='font-size: 14px'>--本邮件由 Work Reporter 发送。"
			+ "<br>如非本人操作，请忽略该验证码。"
			+ " 详情访问 <strong><a href='http://www.tblack.cn' style='text-decoration: none; color: blue;'>书栈</a></strong></strong>";

	/** 重置密码邮件标题 */
	public static final String PASSWORD_FORGET_TITLE = "重置你的WorkReporter账户密码";

	/** 重置密码的邮件内容头 */
	public static final String PASSWORD_FORGET_HEADER = "你正在进行密码重置操作,你的密码重置验证码为: ";

	/** 重置密码链接的前缀信息 */
	public static final String PASSWORD_FORGET_LINK_PREFIX = "点击链接进行重置密码:";

	/**
	 * @~0~	 * @~0~传递一个验证码和一个失效时间来组合成一个默认的验证码邮件内容
	 * @param interval
	 * @return
	 */
	public static String getDefaultContent(String vcode, Integer expiredTime) {

		String expired = getExactExpiredTime(expiredTime);
		
	
		return 
				"<div style='font-family: Consolas,幼圆; font-size: 16px;'>" +
				"<p>" + REGISTER_HEADER +  "<strong>" + vcode +"</strong>" + "</p>" +
				"<p>验证码将于" + expired  + "后失效。请尽快填写</p>" +
				VMAIL_FOOTER +
				"</div>";
	}

	/**
	 * @~0~传递一个失效的时间和一个验证码还有一个重置密码的链接来返回一个重置密码操作的邮件内容
	 * @param code
	 * @param expiredTime
	 * @return
	 */
	public static String passwordForgetContent(String vcode, Integer expiredTime,String resetLink) {
		String expired = getExactExpiredTime(expiredTime);  //拿到准确的失效时间
		
		return 
				"<div style='font-family: Consolas,幼圆; font-size: 16px;'>" +
				"<p>" + PASSWORD_FORGET_HEADER +  "<strong>" + vcode +"</strong>" + "</p>" +
				"<p>重置操作将于" + expired  + "后失效。请尽快进行操作。</p>" +
				"<p>" + PASSWORD_FORGET_LINK_PREFIX   + resetLink + "</p>"+ 
				VMAIL_FOOTER +
				"</div>";
	}
	
	/**
	 * @^0^计算准确的失效时间
	 * @param expiredTime
	 * @return
	 */
	private static String getExactExpiredTime(Integer expiredTime) {
		// 计算失效时间是秒还是分钟等-
		String timeUnit = "秒钟";
		int expired = 0;
		// 计算时间单位， 简单起见，只计算整除部分
		// 超过了小时
		if (expiredTime > 60 * 1000 * 60) {
			timeUnit = "小时";
			expired = expiredTime / (60 * 1000 * 60);
		}
		// 超过了分钟
		else if (expiredTime > 60 * 1000) {
			timeUnit = "分钟";
			expired = expiredTime / (60 * 1000);
		}
		return expired + timeUnit;
	}
}
