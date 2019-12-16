package cn.tblack.work.reporter.user.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import cn.tblack.work.reporter.sys.entity.SysUser;
import cn.tblack.work.reporter.sys.entity.TempUser;
import cn.tblack.work.reporter.sys.service.TempUserService;

/**
 * @_~_~用于不同登录方式的用户注入工具
 * @author TD唐登
 * @Date:2019年12月13日
 * @Version: 1.0(测试版)
 */
@Component
public class UserInjectionUtils {

	@Autowired
	private TempUserService tempUserService;

	/**
	 * @~_~从多个登录平台进行用户信息的注入，如果是本网站内的用户，直接返回。 @~——~否则则查找临时用户数据库进行注入
	 * @return
	 */
	public SysUser injectSysUser(SysUser user,Authentication auth) {
		if (user != null)
			return user;

		user = new SysUser();
		TempUser tuser = tempUserService.findTempUserByAuthentication(auth);

		if (tuser != null) {
			user = tempUserService.converterToSysUser(tuser);
		} else {
			throw new RuntimeException("用户[" + auth.getName() + "]添加提醒失败。在数据库无法找到该用户信息");
		}
		
		return user;
	}
}
