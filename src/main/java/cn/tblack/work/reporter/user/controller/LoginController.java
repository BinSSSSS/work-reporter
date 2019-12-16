package cn.tblack.work.reporter.user.controller;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.Email;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import cn.tblack.work.reporter.email.VMailContent;
import cn.tblack.work.reporter.email.sender.EmailSender;
import cn.tblack.work.reporter.enums.VCodeEmailTypes;
import cn.tblack.work.reporter.properties.WebConfigProperties;
import cn.tblack.work.reporter.properties.WebSystemProperties;
import cn.tblack.work.reporter.result.WebResult;
import cn.tblack.work.reporter.sys.entity.SysUser;
import cn.tblack.work.reporter.sys.entity.VerificationMail;
import cn.tblack.work.reporter.sys.service.SysUserRoleService;
import cn.tblack.work.reporter.sys.service.SysUserService;
import cn.tblack.work.reporter.sys.service.VerificationMailService;
import cn.tblack.work.reporter.util.CodeGenerator;
import cn.tblack.work.reporter.util.DatabaseTableIdGenerator;
import cn.tblack.work.reporter.util.MD5Utils;
import cn.tblack.work.reporter.util.WeightsUtils;

@RestController
public class LoginController {

	private static Logger log = LoggerFactory.getLogger(LoginController.class);

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private SysUserService userService;

	@Autowired
	private VerificationMailService vMailService;

	@Autowired
	private SysUserRoleService userRoleService;

	@Autowired
	private EmailSender emailSender;

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public WebResult login(SysUser user, HttpServletRequest request) {

		WebResult result = new WebResult();
		result.setSuccess(false);
		try {

			if (userService.login(user.getUsername(), user.getPassword())) {
				result.setMsg("登录成功");
				result.setSuccess(true);
				// 设置当前登陆用户的Authentication到SecurityContext中去。
				UsernamePasswordAuthenticationToken token =
						// 将用户名和密码注入到Authentication对象当中
						new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
				token.setDetails(new WebAuthenticationDetails(request));
				Authentication authentication = authenticationManager.authenticate(token);
				// 将当前用户的信息注入到SpringContextHolder当中
				SecurityContextHolder.getContext().setAuthentication(authentication);
			} else {
				result.setMsg("用户名或密码错误");
			}

		} catch (Exception e) {
			log.error("登录验证出现错误! 错误信息为: " + e.getMessage());
			e.printStackTrace();
			result.setMsg("登录失败!服务器正忙~");
		}

		return result;
	}

	@PostMapping(value = "/register")
	public WebResult register(SysUser user, String eCode) {

		log.info("创建的用户信息 为: " + user + ",邮件验证码为: " + eCode);
		WebResult result = new WebResult();

		// 在数据库中查询是否存在该用户和邮箱。 在前台每次填写完成之后，我们都会异步的进行检查是否存在该用户名或者是邮箱

		if (userService.findByEmailAddress(user.getEmail()) != null) {
			result.setMsg("该邮箱已经被注册~");
			result.setSuccess(false);
			return result;
		}

		if (userService.findByUsername(user.getUsername()) != null) {
			result.setMsg("该用户名已经被注册~");
			result.setSuccess(false);
			return result;
		}

		// 如果该用户没有被注册过，那么则检查传递的邮箱验证码是否正确
		VerificationMail vm = vMailService.findLastMail(user.getEmail(), VCodeEmailTypes.REGISTER_CODE); // 拿到最后一封发送给该邮箱的验证码对象

		// 检查vm是否已经过期
		Date date = vm.getDeadline();

		// 如果未过期的话，检查验证码是否相同
		if (date.getTime() > System.currentTimeMillis()) {

			// 如果验证码相同,注册该用户
			if (vm.getCode().equalsIgnoreCase(eCode)) {

				// 将密码进行加密
				user.setPassword(MD5Utils.encrypt(user.getPassword()));
				user.setId(DatabaseTableIdGenerator.createUserId(userService));
				try {

					userService.save(user);
					// 给该用户分配角色
					userRoleService.grantUserRole(user);
					result.setMsg("注册成功");
					result.setSuccess(true);
				} catch (Exception e) {
					log.error("注册用户失败:[  " + user + "], 失败原因为:" + e.getMessage());
					e.printStackTrace();
					result.setSuccess(false);
					result.setMsg("服务器出了点小差~稍后再试");
				}
			}
		}

		return result;

	}

	@PostMapping(value = "/send-ecode")
	public WebResult sendEmailCode(@Email String email) {

		WebResult result = new WebResult();

		log.info("传递的邮箱地址为: " + email);

		// 首先在数据库中查找该邮箱是否已经发送给邮件并拿到最后发送的邮件信息
		VerificationMail vm = vMailService.findLastMail(email, VCodeEmailTypes.REGISTER_CODE);

		Calendar current = Calendar.getInstance();
		boolean needSend = true;
		if (vm != null) {

			Date date = vm.getDeadline();

			// 如果该邮件未失效， 那么则设置还需要等待的时间到前台
			if (date.getTime() > current.getTimeInMillis()) {

				needSend = false;
			}
		}
		if (needSend) {

			String vcode = CodeGenerator.createSixNumberVerficationCode();
			VerificationMail vMail = new VerificationMail();

			vMail.setCreateTime(current.getTime());
			vMail.setType(VCodeEmailTypes.REGISTER_CODE);
			vMail.setDeadline(new Date(current.getTimeInMillis() + WebConfigProperties.VMAIL_SEND_INTERVAL));
			vMail.setCode(vcode);
			vMail.setRecipientMail(email);
			vMail.setWeights(WeightsUtils.timeWeights(current));

			log.info("生成的验证码对象为: " + vcode);
			// 发送邮件--
			Future<Boolean> success = emailSender.send(email, VMailContent.REGISTER_TITLE,
					VMailContent.getDefaultContent(vcode, WebConfigProperties.VMAIL_SEND_INTERVAL));

			// 如果发送成功，才向数据库内写入数据，否则等待
			try {
				if (success.get()) {
					// 需要对数据库进行插入操作
					vMailService.save(vMail);

					result.setMsg("发送成功");
					result.setSuccess(true);
				} else {
					// 邮件发送失败！检查邮箱地址是否正确
					result.setMsg("邮件发送失败");
					result.setSuccess(false);
				}
			} catch (ExecutionException e) {

				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * @~_~注销操作
	 * @param request
	 * @param response
	 * @param auth
	 * @param vm
	 * @return
	 */
	@RequestMapping(value = "/logout.html")
	public ModelAndView logout(HttpServletRequest request, HttpServletResponse response, Authentication auth,
			ModelAndView vm) {
		// 在SpringSecurity中对该用户做注销操作
		new SecurityContextLogoutHandler().logout(request, response, auth);

		// 清除Session内的全部数据
		request.getSession().removeAttribute("avatarUrl");

		vm.setViewName("redirect:/login.html");

		return vm;
	}

	@RequestMapping(value = "/web_domain", method = RequestMethod.POST)
	public String webSystemInfo() {

		return "© 2019 书栈版权所有: " + WebSystemProperties.DAMAIN + "---ICP备案号: " + WebSystemProperties.ICP;
	}
}
