package cn.tblack.work.reporter.controller;

import static cn.tblack.work.reporter.constant.RequestAttributeNames.TOKEN;
import static cn.tblack.work.reporter.constant.RequestAttributeNames.UID;
import static cn.tblack.work.reporter.constant.RequestAttributeNames.USERNAME;
import static cn.tblack.work.reporter.properties.WebConfigProperties.VMAIL_RESET_PASSWORD_EXPIRED;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.concurrent.Future;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;

import cn.hutool.extra.qrcode.QrCodeUtil;
import cn.tblack.work.reporter.email.VMailContent;
import cn.tblack.work.reporter.email.sender.EmailSender;
import cn.tblack.work.reporter.enums.VCodeEmailTypes;
import cn.tblack.work.reporter.result.WebResult;
import cn.tblack.work.reporter.sys.entity.SysUser;
import cn.tblack.work.reporter.sys.entity.VerificationMail;
import cn.tblack.work.reporter.sys.service.SysUserService;
import cn.tblack.work.reporter.sys.service.VerificationMailService;
import cn.tblack.work.reporter.util.CodeGenerator;
import cn.tblack.work.reporter.util.DateUtils;
import cn.tblack.work.reporter.util.DesUtils;
import cn.tblack.work.reporter.util.VerifyCodeUtils;
import cn.tblack.work.reporter.util.WeightsUtils;
/**
 * @-=-一些帮助用户相关的方法控制器。-如忘记密码-账号查询等=-=
 * @author TD唐登
 * @Date:2019年11月23日
 * @Version: 1.0(测试版)
 */
@RestController
public class RestHelperController {

	private static Logger log = LoggerFactory.getLogger(RestHelperController.class);

	@Autowired
	private SysUserService userService;

	@Autowired
	private EmailSender emailSender;

	@Autowired
	private VerificationMailService vMailService;

	/**
	 * @~=-=~检查传递的userToken是否有效-=-。 该userToken可以是邮箱·也可以是一个用户名
	 * @~=-=~通过该userToken来具体的判断当前账户是否存在
	 * @param userToken
	 * @return
	 */
	@RequestMapping("/password-forget")
	public WebResult forgetPassword(String userToken, String vcode, HttpServletRequest request) {

		WebResult result = new WebResult();

		if (!VerifyCodeUtils.verifyCode(vcode, request.getSession())) {
			result.setMsg("验证码错误");
			result.setSuccess(false);
			return result;
		}

		// 忘记密码的处理流程=~= 1. 检查传递的账号是否存在~~=~~
		// 2.存在既对该账号进行发送一封验证邮件-=-=表示确实是本人操作=-=邮件内有一个验证码信息和一个跳转到修改密码页面的链接。
		// 进入该链接进行密码的修改-=-填写正确该验证码信息和新的密码则可以完成找回密码操作-=-
		// 该链接绑定的是一个分配的用户Token。该Token绑定到了一个指定的用户。 并存在失效时间。
		try {
			SysUser user = userService.findByUsername(userToken);

			if (user == null) {
				user = userService.finByEmailAddress(userToken);
			}

			if (user == null) {
				result.setSuccess(false);
				result.setMsg("当前用户或者邮箱不存在");
			}
			// 发送一个带有能够重置密码的链接到忘记密码用户的邮箱
			else {

				// 表示了当前的时间戳
				Calendar currentTime = GregorianCalendar.getInstance();
				// 生成一个当前时间戳的令牌对象
				Long token = WeightsUtils.timeWeights(currentTime);

				JSONObject json = new JSONObject();

				json.put(UID, user.getId());
				json.put(TOKEN, token);
				
				String encryptInfo = null;

				encryptInfo = new DesUtils().encrypt(json.toString());

				// 拼接url对象
				StringBuffer requestUrl = request.getRequestURL();
				int index = requestUrl.indexOf("//"); // http://****/首先查找//. 之后再次查找 /

				if (index != -1) {
					index = requestUrl.indexOf("/", index + 3);
				}
				// 拿到当前web应用的地址
				String hostAddress = requestUrl.substring(0, index);

				String url = hostAddress + "/password-reset.html?token=" + encryptInfo // MD5Utils.encrypt(token + "")
						+ "&username=" + user.getUsername();

				// 拼接重置密码链接
				String resetLink = "<a href='" + url + "'>" + "<strong style='color:blue;'>点击进行重置密码</strong>" + "</a>";
				
				//生成验证码对象
				String code = CodeGenerator.createSixNumberVerficationCode();
				
				// 创建一个VerificationMail对象
				VerificationMail vMail = new VerificationMail();
				vMail.setRecipientMail(user.getEmail());
				vMail.setCode(code);
				vMail.setCreateTime(currentTime.getTime());
				vMail.setDeadline(new Date(currentTime.getTimeInMillis() + VMAIL_RESET_PASSWORD_EXPIRED));
				vMail.setType(VCodeEmailTypes.PASSWORD_FORGET_CODE);
				vMail.setWeights(token);
				
				
				// 将该验证码信息保存在数据库当中				
				log.info("生成的主机地址为: " + hostAddress);
				log.info("为用户[" + user.getUsername() +"]生成的重置密码链接为: " + resetLink);
				log.info("为用户[" + user.getUsername() +"]生成的重置密码验证码为: " + code);				
				log.info("正在进行用户[ " + user.getUsername() + " ]的忘记密码操作~");
				vMailService.save(vMail);

				// 发送验证码信息到指定邮箱中
				Future<Boolean> future = emailSender.send(user.getEmail(), VMailContent.PASSWORD_FORGET_TITLE,
						VMailContent.passwordForgetContent(code, VMAIL_RESET_PASSWORD_EXPIRED, resetLink));

				if (future.isDone()) {

				}
				result.setMsg("已经给重置操作的用户邮箱发送了重置邮件！请查收并进行下一步的操作~");
				result.setSuccess(true);
			}
		} catch (Exception e) {
			log.error("将数据插入到数据库内失败~失败原因为: " + e.getMessage());
			e.printStackTrace();
			result.setSuccess(false);
			result.setMsg("发送邮件失败！请稍后重试~");
		}

		return result;

	}
	/**
	 * @-……-返回一个重置密码的页面
	 * @param token
	 * @param username
	 * @param mv
	 * @return
	 */
	@RequestMapping(value = "/password-reset.html", method = RequestMethod.GET)
	public ModelAndView passwordResetPage(String token, String username, ModelAndView mv) {

		try {
			if (token.isEmpty() || username.isEmpty()) {

				mv.setViewName("redirect:/error/404.html");
			}
			// 在数据库中查找该用户对象的最后一次重置密码的邮件
			else {
				JSONObject json = JSONObject.parseObject(new DesUtils().decrypt(token));
				SysUser user = userService.findByUsername(username);
				// 检查用户不为空
				if (user != null) {
					String email = user.getEmail();

					// 拿到该用户发送的最后一封忘记密码邮件
					VerificationMail vMail = vMailService.findLastMail(email, VCodeEmailTypes.PASSWORD_FORGET_CODE);

					// 如果该用户确实是进行了重置密码操作
					if (vMail != null) {
						Date deadline = vMail.getDeadline();

						// 当前的重置没有失效并且传递的Token是有效的Token。 为了防止修改请求参数来重置其他用户的密码
						if (DateUtils.isExpired(deadline)
								&& json.getString(UID).equals(user.getId())) {

							mv.addObject(USERNAME, username);
							mv.addObject(TOKEN, token);
							mv.setViewName("/reset");
						} else {
							mv.setViewName("redirect:/expired.html");
						}
					}
					// 该用户没有进行重置密码操作
					else {
						mv.setViewName("redirect:/error/404.html");
					}
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
			log.error("重置密码操作出现错误.错误信息为:" + e.getMessage());
			mv.setViewName("redirect:/error/404.html");
			mv.addObject("error", e);
		}
		
		return mv;
	}

	/**
	 * @*&*具体的重置密码操作
	 * @param token
	 * @param password
	 * @param mailCode
	 * @return
	 */
	@RequestMapping(value = "/reset-password", method = RequestMethod.POST)
	public WebResult resetPassword(String token, String password, String mailCode) {

		WebResult result = new WebResult();

		String decryptInfo = null;

		try {
			decryptInfo = new DesUtils().decrypt(token);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("用户传递的加密数据信息有错~");
			result.setMsg("请求错误。请检查请求信息是否正确");
			result.setSuccess(false);
			return result;
		}

		// 拿到加密的数据~
		JSONObject json = JSONObject.parseObject(decryptInfo);

		SysUser user = userService.findById(json.getString(UID));

		// 当前用户存在的话。
		if (user != null) {

			// 查询验证码邮件对象
			VerificationMail vMail = vMailService.findLastMail(user.getEmail(), VCodeEmailTypes.PASSWORD_FORGET_CODE);

			if (vMail != null) {
				Date deadline = vMail.getDeadline();

				// 验证码未过期
				if (DateUtils.isExpired(deadline)) {

					if (mailCode.equals(mailCode)) {
						// 进行密码的更改
						try {
							userService.updatePassword(user, password);
							result.setMsg("重置密码成功~");
							result.setSuccess(true);
						} catch (Exception e) {
							result.setMsg("重置密码失败！服务器正忙~");
							result.setSuccess(false);
							e.printStackTrace();
							log.error("修改用户 [ " + user.getUsername() + "]的密码失败，失败原因为:" + e.getMessage());
						}
					} else {
						result.setMsg("验证码错误~请重新输入~");
						result.setSuccess(false);
					}
				} else {
					result.setMsg("重置操作已经失效，请重新操作~");
					result.setSuccess(false);
				}
			} else {
				result.setMsg("该用户未进行重置密码操作~");
				result.setSuccess(false);
			}
		} else {
			result.setMsg("当前用户不存在");
			result.setSuccess(false);
		}

		return result;
	}
	
	
	/**
	 * @throws IOException 
	 * @!_!生成二维码信息
	 */
	@RequestMapping(value = "/qrcode/gen")
	public void genQRCode(String url, Integer width,Integer height,HttpServletResponse response) throws IOException {
		

		if(!url.startsWith("http://") &&  !url.startsWith("https://")) {
			url = "http://" + url;
		}
		ServletOutputStream out = response.getOutputStream();
		BufferedImage image = QrCodeUtil.generate(url, width, height);
		
		
		response.setHeader("Cache-Control", "no-store");
		response.setHeader("Pragma", "no-cache");
		response.setDateHeader("Expires", 0);
		response.setContentType("image/jpeg");

		ImageIO.write(image, "jpg", out);
		out.flush();
		out.close();
	}
}
