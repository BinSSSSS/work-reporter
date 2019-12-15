package cn.tblack.work.reporter.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.session.InvalidSessionStrategy;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;

import cn.tblack.work.reporter.session.strategy.ImoocExpiredSessionStrategy;
import cn.tblack.work.reporter.session.strategy.ImoocInvalidSessionStrategy;

/**
 * @用于定义 Spring Security 中 session管理策略的 Bean。
 * @author TD唐登
 * @Date:2019年11月19日
 * @Version: 1.0(测试版)
 */
@Configuration
public class SessionConfiguration {
	
	@Bean
	public SessionInformationExpiredStrategy sessionInfoExpiredStrategy() {
		return new ImoocExpiredSessionStrategy("/login.html");
	}

	@Bean
	public InvalidSessionStrategy invalidSessionStrategy() {
		return new ImoocInvalidSessionStrategy("/login.html");
	}
}
