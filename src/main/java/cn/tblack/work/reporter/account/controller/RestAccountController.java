package cn.tblack.work.reporter.account.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.tblack.work.reporter.annotation.HasAnyRole;
import cn.tblack.work.reporter.result.WebResult;
import cn.tblack.work.reporter.sys.entity.SysUser;
import cn.tblack.work.reporter.sys.service.SysUserService;
import cn.tblack.work.reporter.util.MD5Utils;

@RestController
@RequestMapping("/account")
@HasAnyRole
public class RestAccountController {
	
	private static Logger log = LoggerFactory.getLogger(RestAccountController.class);
	
	@Autowired
	private SysUserService userService;
	
	
	@RequestMapping("/change-password")
	public WebResult changePassword(Authentication auth,String password, String newPassword,
			HttpServletRequest request, HttpServletResponse response) {
		
		WebResult result  = new WebResult();
		
		SysUser user = userService.findByUsername(auth.getName());
		try {
			//如果原始密码和当前传递进来的密码相同的时候。才进行密码的修改。
			if(user.getPassword().equals(MD5Utils.encrypt(password))) {
				userService.updatePassword(user,newPassword);
				result.setSuccess(true);
				result.setMsg("密码修改成功~请重新登录");
				
				//转到注销操作--清空登录状态
				new SecurityContextLogoutHandler().logout(request, response, auth);
			}else {
				result.setMsg("原始密码不正确~");
				result.setSuccess(false);
			}
		}catch(Exception e) {
			log.error("修改密码出错--出错原因为: " +  e.getMessage());
			e.printStackTrace();
			result.setSuccess(false);
			result.setMsg("密码修改出错---服务器正忙~");	
		}
		
		return result;
	}
	
}
