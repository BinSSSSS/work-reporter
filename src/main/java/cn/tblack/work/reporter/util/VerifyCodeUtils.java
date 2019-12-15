package cn.tblack.work.reporter.util;

import javax.servlet.http.HttpSession;

/**
 * @~*~用于验证验证码是否正确的工具类
 * @author TD唐登
 * @Date:2019年11月25日
 * @Version: 1.0(测试版)
 */
public class VerifyCodeUtils {
	
	/**用于添加到请求对象中的属性名*/
	public static final String VCODE_NAME = "vcode";
	
	
	public static boolean verifyCode(String vcode,HttpSession session) {
		
		if(vcode == null || vcode.isEmpty())
			return false;
		
		String code =  session.getAttribute(VCODE_NAME).toString(); 
		
		if(vcode.equalsIgnoreCase(code)) {
			return true;
		}
			
		return false;
	}
}
