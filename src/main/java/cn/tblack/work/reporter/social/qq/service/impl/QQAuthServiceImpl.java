package cn.tblack.work.reporter.social.qq.service.impl;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import cn.tblack.work.reporter.social.qq.model.QQUserInfo;
import cn.tblack.work.reporter.social.qq.service.QQAuthService;

@Service
public class QQAuthServiceImpl implements QQAuthService{

	private static Logger log = LoggerFactory.getLogger(QQAuthServiceImpl.class);
	
	@Autowired
	private RestTemplate restTemplate;
	
	/**用于QQ登录的链接*/
	private static final String AUTHORIZATION_URL =  
			"https://graph.qq.com/oauth2.0/authorize?response_type=code&client_id=%s&redirect_uri=%s&scope=%s";
	
	/**用于登录成功或失败的重定向链接*/
	private static final String REDIRECT_URL =  "http://tblack.cn:7777/social/qq/callback";
	
	/**获取访问Token的链接*/
	private static final String ACCESS_TOKEN_URL = 
			"https://graph.qq.com/oauth2.0/token?grant_type=authorization_code&client_id=%s&client_secret=%s&code=%s&redirect_uri=%s";
	
	/**拿到用户的OpenId的链接*/
	private static final String OPEN_ID_URL = 
			"https://graph.qq.com/oauth2.0/me?access_token=%s";
	
	/**拿到用户信息的链接*/
	private static final String USER_INFO_URL = 
			"https://graph.qq.com/user/get_user_info?access_token=%s&oauth_consumer_key=%s&openid=%s";
	
	/**SCOPE范围能够拿到用户的信息。其他还能够设置add_topic,add_one_blog,add_album,upload_pic等*/
	private static final String SCOPE = "get_user_info";
	
	/**QQ互联的应用ID*/
	@Value("${social.qq.appId}")
	private String appId;  
	
	/**QQ互联的应用密匙*/
	@Value("${social.qq.appSecret}")
	private String appSecret;
	
	@Override
	public String getAccessToken(String code) {
		
		String url =  String.format(ACCESS_TOKEN_URL, appId,appSecret,code,REDIRECT_URL);
	
		String response  = getResponseString(url);
		
//		log.info("getAccessToken的响应信息为: " +  response);
	
		//将响应数据按照格式组建为一个Map对象
		Map<String,String> params = toParamMap(response);
		
//		log.info("getAccessToken的响应Map为: " +  params);
		
		//如果map对象中不存在access_token返回null
		return params.get("access_token");
	}

	@Override
	public String getOpenId(String accessToken) {
		
		String url =  String.format(OPEN_ID_URL,accessToken);
		
		String response =  getResponseString(url);

		log.info("拿到的OpenId信息:" + response);
		JSONObject json = getJsonObject(response);
		
		@SuppressWarnings("unchecked")
		Map<String,String> params = json.toJavaObject(Map.class);
//		log.info("Map对象: " + params);
		
		return params.get("openid");
	}

	@Override
	public String refreshToken(String code) {
		return null;
	}

	@Override
	public String getAuthorizationUrl() {
		String url =  String.format(AUTHORIZATION_URL, appId,REDIRECT_URL,SCOPE);
		
		return url;
	}

	@Override
	public QQUserInfo getUserInfo(String accessToken, String openId) {
		
		String url =  String.format(USER_INFO_URL, accessToken,appId,openId);
		
		String response = getResponseString(url);
		
//		log.info("拿到的用户返回信息为: " +  response);
		
		JSONObject json = getJsonObject(response);

		QQUserInfo userInfo =  json.toJavaObject(QQUserInfo.class);

		
		return userInfo;
	}
	
	/**
	 * @_!__!通过一个后台响应的数据来按照HTTP请求参数格式进行拆分并组装为一个Map对象。
	 * @param response
	 * @return
	 */
	private Map<String,String> toParamMap(String response){
		
		Map<String,String> params =  new HashMap<>();
		
		if(response == null || response.isEmpty())
			return params;
		
		//响应数据按照key=value&key=value的形式返回/继续拆分为key=value的形式
		//如果传递的是 key : value 的形式，则不会拆分
		String[] paramArr	 =  response.split("&");
		
		for (String param : paramArr) {
			
			//直接按照异常抛出捕获的形式，省去多余的判断。如果用户传递的是错误的形式，会成为一个空的map对象
			try {
				//查找到=的位置。
				int index =  param.indexOf("=");
				if(index == -1) {
					index =  param.indexOf(":");
				}
				//=前半部分作为键，后半部分作为值
				params.put(param.substring(0,index), param.substring(index + 1));
			}catch(Exception e) {
				
			}
		}
		
		return params;
	}
	
	private String getResponseString(String url) {
		
		//将访问链接编码为一个URI对象
		URI uri = URI.create(url);
		
		//拿到响应回来的访问Token数据
		return  restTemplate.getForObject(uri, String.class);
		 
	}
	
	private static  JSONObject getJsonObject(String response) {
		
		int start = response.indexOf("{");
		int end = response.indexOf("}");
		
		
		if(start != -1 &&  end != -1) {
			response = response.substring(start, end + 1);
		}
		
		return JSON.parseObject(response);
	}
	
	public static void main(String[] args) {
		
		System.err.println(getJsonObject("callback( {client_id:\"101833743\",\"openid\":\"B0DCF713786F862CD976EC918443BC5C\"} )"));;
		
	}
	
}
