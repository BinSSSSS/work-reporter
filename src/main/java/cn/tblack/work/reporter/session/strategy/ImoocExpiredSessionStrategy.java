package cn.tblack.work.reporter.session.strategy;

import java.io.IOException;

import javax.servlet.ServletException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;

public class ImoocExpiredSessionStrategy extends AbstractSessionStrategy implements SessionInformationExpiredStrategy {
    
	private static Logger log  = LoggerFactory.getLogger(ImoocExpiredSessionStrategy.class);
	
    public ImoocExpiredSessionStrategy(String invalidSessionUrl){
        super(invalidSessionUrl);
    }
    
    @Override
    public void onExpiredSessionDetected(SessionInformationExpiredEvent event) throws IOException, ServletException{
    	
    	log.info("Session失效被捕获~ Session Id:" + event.getSessionInformation().getSessionId());
    	onSessionInvalid(event.getRequest(), event.getResponse());
    }
    
    @Override
    protected boolean isConcurrency(){
        return true;
    }
}