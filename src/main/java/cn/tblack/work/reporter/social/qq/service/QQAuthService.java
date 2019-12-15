package cn.tblack.work.reporter.social.qq.service;

import cn.tblack.work.reporter.social.qq.model.QQUserInfo;

/**
 * @--!_!用于提供第三方QQ用户登录的一个权限服务接口。接口中主要定义QQ用户登录时所需要的一些操作
 * @author TD唐登
 * @Date:2019年12月11日
 * @Version: 1.0(测试版)
 */
public interface QQAuthService {

	/**
	 * @_!_!根据Code生成一个访问Token
	 * @param code
	 * @return
	 */
	String getAccessToken(String code);
	
	/**
	 * @_!_!根据访问的Token来获取一个开放Id
	 * @param accessToken
	 * @return
	 */
	String getOpenId(String accessToken);
	
	/**
	 * @~_~通过Code刷新访问Token
	 * @param code
	 * @return
	 */
	String refreshToken(String code);
	
	/**
	 * @_~_~拿到授权的链接
	 * @return
	 */
	String getAuthorizationUrl();
	
	/**
	 * @~_~拿到访问登录用户的基本信息
	 * @param accessToken
	 * @param openId
	 * @return
	 */
	QQUserInfo getUserInfo(String accessToken, String openId);
}
