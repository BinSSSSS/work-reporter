package cn.tblack.work.reporter.social.auth;

import java.util.Collection;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import cn.tblack.work.reporter.social.enums.SocialPlatformType;

/**
 * @~_~社交登录权限信息类
 * @author TD唐登
 * @Date:2019年12月13日
 * @Version: 1.0(测试版)
 */
public class SocialLoginAuthentication extends UsernamePasswordAuthenticationToken{

	private String openId;
	private SocialPlatformType socialPlatform;

	private static final long serialVersionUID = 1L;

	public SocialLoginAuthentication(Object principal, String openId,SocialPlatformType socialPlatform) {
		super(principal, "");
		this.openId =  openId;
		this.socialPlatform = socialPlatform;
	}
	public SocialLoginAuthentication(Object principal, String openId,
			SocialPlatformType socialPlatform,
			Collection<? extends GrantedAuthority> authorities) {
		super(principal, "",authorities);
		this.openId =  openId;
		this.socialPlatform = socialPlatform;
	}
	public String getOpenId() {
		return openId;
	}
	public SocialPlatformType getSocialPlatform() {
		return socialPlatform;
	}
	@Override
	public String toString() {
		return "SocialLoginAuthentication [openId=" + openId + ", socialPlatform=" + socialPlatform + "]\n" + super.toString();
	}
	
	
}
