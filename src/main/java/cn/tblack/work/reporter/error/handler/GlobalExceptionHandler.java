package cn.tblack.work.reporter.error.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * @~_~全局异常处理器。用于处理Controller控制层抛出的异常
 * @author TD唐登
 * @Date:2019年12月11日
 * @Version: 1.0(测试版)
 */
@ControllerAdvice
public class GlobalExceptionHandler {
	
	private Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	@Autowired
	private NoPermissionHandler noPermissionHandler;
	
	/**
	 * @_!_!用于无权限访问的异常
	 * @param e
	 * @return
	 * @throws ServletException 
	 * @throws IOException 
	 */
	@ExceptionHandler(value = AccessDeniedException.class)
	public void noPermissionHandler(HttpServletRequest request, HttpServletResponse response,
			AccessDeniedException e)  {
		
		log.info("请求对象: " + request + ",响应对象: " +  response +",异常对象: " +  e);
		try {
			noPermissionHandler.handle(request, response, e);
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (ServletException e1) {
			e1.printStackTrace();
		}
	}
	
	/**
	 * @_!_!全局未处理的异常
	 * @return
	 */
	@ExceptionHandler(value = Exception.class)
	public String globalException(Exception e) {
		
		log.error("引发了异常对象: " + e);
		e.printStackTrace();
		
		return "redirect:/error/500.html";
	}
}
