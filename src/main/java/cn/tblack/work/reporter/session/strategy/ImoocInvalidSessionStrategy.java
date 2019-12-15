package cn.tblack.work.reporter.session.strategy;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.web.session.InvalidSessionStrategy;

public class ImoocInvalidSessionStrategy extends AbstractSessionStrategy implements InvalidSessionStrategy {
    
	private static Logger log  = LoggerFactory.getLogger(ImoocInvalidSessionStrategy.class);
	
    public ImoocInvalidSessionStrategy(String invalidSessionUrl){
        super(invalidSessionUrl);
    }
    
    @Override
    public void onInvalidSessionDetected(HttpServletRequest request,HttpServletResponse response) throws IOException, ServletException{
    	log.info("传递的Session非法被捕获~ 请求路径：" + request.getRequestURL());
    	onSessionInvalid(request, response);
    }
}
