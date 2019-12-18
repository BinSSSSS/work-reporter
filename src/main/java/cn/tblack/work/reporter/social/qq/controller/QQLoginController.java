package cn.tblack.work.reporter.social.qq.controller;

import static cn.tblack.work.reporter.constant.RequestAttributeNames.AVATAR_URL;
import static cn.tblack.work.reporter.constant.RequestAttributeNames.OPENID;
import static cn.tblack.work.reporter.constant.RequestAttributeNames.SOCIAL_PLATFORM;

import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import cn.tblack.work.reporter.social.auth.SocialLoginAuthentication;
import cn.tblack.work.reporter.social.enums.SocialPlatformType;
import cn.tblack.work.reporter.social.qq.model.QQUserInfo;
import cn.tblack.work.reporter.social.qq.service.QQAuthService;
import cn.tblack.work.reporter.sys.entity.TempUser;
import cn.tblack.work.reporter.sys.service.TempUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
@Api(tags = "第三方QQ登录控制器")
@Controller
@RequestMapping("/social/qq")
public class QQLoginController {


	private static Logger log = LoggerFactory.getLogger(QQLoginController.class);
	
	@Autowired
	private QQAuthService qqAuthService;
	
	
	@Autowired
	private TempUserService tempUserService;
	/**
	 * @~_~_~QQ登陆页面
	 * @return
	 */
	@ApiOperation(value = "QQ登录页面")
	@GetMapping("/login.html")
	public String loginFace() {
		
		return  "redirect:"  + qqAuthService.getAuthorizationUrl();
	}
	
	
	/**
	 * @_!_!处理QQ登录链接之后返回的用户信息。
	 * @return
	 */
	@ApiOperation(value = "QQ登录之后的回调处理")
	@ApiImplicitParam(name = "code", value = "code" ,dataTypeClass = String.class, required = true)
	@RequestMapping(value = "/callback",method = {RequestMethod.POST,RequestMethod.GET})
	public String qqLoginCallback(@RequestParam(value = "code",required = true)String code,
			HttpServletRequest request) {
		
		
//		log.info("进行QQ登录的回调");
		
		try {
			//通过返回的code对象进行处理
			String accessToken =  qqAuthService.getAccessToken(code);
			
			//查找openid
			if(accessToken != null) {
				String openid = qqAuthService.getOpenId(accessToken);
				
				//拿到用户信息
				if(openid != null) {
					QQUserInfo userInfo =  qqAuthService.getUserInfo(accessToken, openid);
//					log.info("当前登录的用户信息为: " +  userInfo);
					
					//如果之前用户未在本网站登录过的话。创建一个新的临时用户信息
					TempUser tuser = tempUserService.findByOpenIdAndSocialPlatform(openid, SocialPlatformType.QQ);
					if(tuser == null)
					{
						//将QQ用户添加到临时的用户数据库中
						tuser = new TempUser();
						tuser.setAvatarUrl(userInfo.getFigureurl_qq_1());
						tuser.setNickname(userInfo.getNickname());
						tuser.setOpenId(openid);
						tuser.setSocialPlatform(SocialPlatformType.QQ);						
						
					}
					tempUserService.save(tuser);
					
					//将登录用户保存在临时登录信息中-并在SecurityContext添加一个普通的用户权限
					if(userInfo != null && userInfo.getRet().equals("0")) {
						/**为QQ登录的用户分配游客权限*/
						Set<SimpleGrantedAuthority> authorities = new HashSet<>();
						authorities.add(new SimpleGrantedAuthority("ROLE_VISITOR"));
						
						// 设置当前登陆用户的Authentication到SecurityContext中去。
						SocialLoginAuthentication token =
								// 将用户名和密码注入到Authentication对象当中
								new SocialLoginAuthentication(userInfo.getNickname(),
										openid,SocialPlatformType.QQ,authorities);
						// 将当前用户的信息注入到SpringContextHolder当中
						SecurityContextHolder.getContext().setAuthentication(token);
						
						log.info("QQ用户[" + userInfo.getNickname() + " ]登录成功");
						/*在Session存放信息*/
						request.getSession().setAttribute(AVATAR_URL, userInfo.getFigureurl_qq_1());
						request.getSession().setAttribute(OPENID, openid);
						request.getSession().setAttribute(SOCIAL_PLATFORM, SocialPlatformType.QQ);
						
						return "redirect:/index.html";
					}
				}
			}
			
		}catch(Exception e) {
			e.printStackTrace();
			log.error("QQ登录失败!失败原因为: " +  e.getMessage());
			throw new RuntimeException("QQ登录失败！服务器正忙");
		}
		return "redirect:/error/404";
	}
}
