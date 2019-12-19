package cn.tblack.work.reporter.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @_~_~用于在响应的时候加上缓存控制
 * @author TD唐登
 * @Date:2019年12月19日
 * @Version: 1.0(测试版)
 */
@WebFilter(urlPatterns = "/*")
public class ResponseCacheFilter extends  HttpFilter{

	@Override
	public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		response.setHeader("Cache-Control", "max-age=600");
		System.err.println(response.getHeader("Cache-Control"));
		chain.doFilter(request, response);
	}

}
