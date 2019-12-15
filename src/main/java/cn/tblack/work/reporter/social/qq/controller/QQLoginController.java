package cn.tblack.work.reporter.social.qq.controller;

import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import cn.tblack.work.reporter.social.qq.model.QQUserInfo;
import cn.tblack.work.reporter.social.qq.service.QQAuthService;

@Controller
@RequestMapping("/social/qq")
public class QQLoginController {


	private static Logger log = LoggerFactory.getLogger(QQLoginController.class);
	
	@Autowired
	private QQAuthService qqAuthService;
	
	/**
	 * @~_~_~QQ登陆页面
	 * @return
	 */
	@RequestMapping("/login.html")
	public String loginFace() {
		
		return  "redirect:"  + qqAuthService.getAuthorizationUrl();
	}
	
	
	/**
	 * @_!_!处理QQ登录链接之后返回的用户信息。
	 * @return
	 */
	@RequestMapping(value = "/callback")
	public String qqLoginCallback(@RequestParam(value = "code",required = true)String code,
			HttpServletRequest request) {
		
		
		log.info("进行QQ登录的回调");
		
		try {
			//通过返回的code对象进行处理
			String accessToken =  qqAuthService.getAccessToken(code);
			
			//查找openid
			if(accessToken != null) {
				String openid = qqAuthService.getOpenId(accessToken);
				
				//拿到用户信息
				if(openid != null) {
					QQUserInfo userInfo =  qqAuthService.getUserInfo(accessToken, openid);
					log.info("当前登录的用户信息为: " +  userInfo);
					
					//将登录用户保存在临时登录信息中-并在SecurityContext添加一个普通的用户权限
					if(userInfo != null && userInfo.getRet().equals("0")) {
						/**为QQ登录的用户分配一个普通的用户权限*/
						Set<SimpleGrantedAuthority> authorities = new HashSet<>();
						authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
						
						// 设置当前登陆用户的Authentication到SecurityContext中去。
						UsernamePasswordAuthenticationToken token =
								// 将用户名和密码注入到Authentication对象当中
								new UsernamePasswordAuthenticationToken(userInfo.getNickname(),"",authorities);
						// 将当前用户的信息注入到SpringContextHolder当中
						SecurityContextHolder.getContext().setAuthentication(token);
						
						log.info("QQ用户[" + userInfo.getNickname() + " ]登录成功");
						request.getSession().setAttribute("avatarUrl", userInfo.getFigureurl_qq_1());
						
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
