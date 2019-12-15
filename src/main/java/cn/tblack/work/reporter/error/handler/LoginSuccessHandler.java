package cn.tblack.work.reporter.error.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;


/**
 * @-登录成功的处理器。
 * @author TD唐登
 * @Date:2019年11月21日
 * @Version: 1.0(测试版)
 */
public class LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    
	private static Logger log = LoggerFactory.getLogger(LoginSuccessHandler.class);
    private static final String IPADRESS_TYPE_UNKNOWN = "unknown";
    
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,HttpServletResponse response,Authentication authentication) throws IOException, ServletException{
    	
    	// 获得授权后可得到用户信息 可使用SUserService进行数据库操作
    	UserDetails userDetails = (UserDetails) authentication.getPrincipal();
    	log.info("当前用户登录成功! 用户信息为: " + userDetails);
        request.getSession().setAttribute("userSession", userDetails);
    }
    
    public String getIpAddress(HttpServletRequest request){
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || IPADRESS_TYPE_UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || IPADRESS_TYPE_UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || IPADRESS_TYPE_UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || IPADRESS_TYPE_UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || IPADRESS_TYPE_UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}