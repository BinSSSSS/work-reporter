package cn.tblack.work.reporter.config;

import static cn.tblack.work.reporter.properties.WebConfigProperties.ALLOW_PATH;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.vote.AuthenticatedVoter;
import org.springframework.security.access.vote.RoleVoter;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.event.LoggerListener;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
import org.springframework.security.web.access.expression.WebExpressionVoter;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.session.InvalidSessionStrategy;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;

import cn.tblack.work.reporter.encoder.CustomMD5PasswordEncoder;
import cn.tblack.work.reporter.error.handler.LoginSuccessHandler;
import cn.tblack.work.reporter.error.handler.NoPermissionHandler;
import cn.tblack.work.reporter.security.WRAccessDecisionManager;
import cn.tblack.work.reporter.security.WRSecurityMetadataSource;
import cn.tblack.work.reporter.sys.service.WRUserDetailService;

@EnableWebSecurity
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true) // 开启@Pre*注解
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

	private static Logger log = LoggerFactory.getLogger(WebSecurityConfiguration.class);

	@Autowired
	private WRUserDetailService userDetailService; // 用于用户登录验证-

	@Autowired
	private WRSecurityMetadataSource mySecurityMetadataSource; //自定义的URL元数据处理

	@Autowired
	private InvalidSessionStrategy invalidSessionStrategy; // 非法Session策略

	@Autowired
	private SessionInformationExpiredStrategy sessionInformationExpiredStrategy; // session信息处理策略

	@Autowired
	private NoPermissionHandler noPermissionHandler;  //无权限处理类。

	@Override
	public void configure(WebSecurity web) throws Exception {
		
		//无需要拦截的静态资源=-= 由配置文件中给出定义=-=
		web.ignoring().antMatchers(ALLOW_PATH);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
//		super.configure(http);
		
		log.info("配置 拦截路径等信息");
		
		// // 允许访问静态资源
		http.csrf().disable()
		.authorizeRequests()
		.antMatchers("/login.html").permitAll()
		/*配置一个自定义的对象请求处理- 添加的三个对象作用分别为 1对URL元数据进行角色依赖注入， 2对URL访问权限进行验证。3用于权限管理*/
		.withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {

			@Override
			public <O extends FilterSecurityInterceptor> O postProcess(O fsi) {
				fsi.setSecurityMetadataSource(mySecurityMetadataSource);
				fsi.setAccessDecisionManager(accessDecisionManager());
				fsi.setAuthenticationManager(authenticationManagerBean());

				return fsi;
			}
		})
		.anyRequest().authenticated()
//		.and().formLogin()	
//		.loginPage("/login.html") // 设置登录页面
//		.loginProcessingUrl("/login") // 设置触发登录的url链接
//		.usernameParameter("username") // 登录页面的用户提交字段名
//		.passwordParameter("password") // 登录页面的密码提交字段名
//		.defaultSuccessUrl("/index.html") // 登录成功之后跳转到的页面
		.and()
		.exceptionHandling() // 异常处理器
		.accessDeniedHandler(noPermissionHandler) // 当请求被拒绝时进行的处理器
		.and().logout() // 登出处理
		.logoutUrl("/logout")
		.logoutSuccessUrl("/login.html") // 登出成功的url
		.and()
		.sessionManagement()		// session管理
		.invalidSessionStrategy(invalidSessionStrategy) //非法Session访问
		.maximumSessions(1) //最大的session数量
		.maxSessionsPreventsLogin(true)  //最大session登录
		.expiredSessionStrategy(sessionInformationExpiredStrategy);  //失效的session信息处理
		
		// // 允许iframe 嵌套
		http.headers().frameOptions().disable();

		/** 社交登录 **/
//        http.apply(imoocSocialSecurityConfig);

	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		
		//自定义的访问权限注入和密码加密器。
		auth.userDetailsService(userDetailService).passwordEncoder(passwordEncoder());
	}

	/** 密码加密 **/
	@Bean
	public CustomMD5PasswordEncoder passwordEncoder() {
		return new CustomMD5PasswordEncoder();
	}

	/** 登录成功后跳转的页面 用户或者管理员登录日志 */
	@Bean
	public LoginSuccessHandler loginSuccessHandler() {
		return new LoginSuccessHandler();
	}

	@Bean
	public LoggerListener loggerListener() {
		return new LoggerListener();
	}

//	@Bean
	/**
	 * @---用于打印出请求访问的日志信息。 
	 * @return
	 */
	public org.springframework.security.access.event.LoggerListener eventLoggerListener() {
		return new org.springframework.security.access.event.LoggerListener();
	}

	/** 类可以有许多provider（提供者）提供用户验证信息，这里笔者自己写了一个类LoinServiceImpl来获取用户信息。 **/
	@Bean(name = "authenticationManager")
	@Override
	public AuthenticationManager authenticationManagerBean() {
		// 这个是处理验证的，这里需要特别说明的是：这个类不单只这个拦截器用到，
		// 还有验证拦截器AuthenticationProcessingFilter也用到 了
		// ，而且实际上的登陆验证也是AuthenticationProcessingFilter拦截器调用
		// authenticationManager来处理的
		AuthenticationManager authenticationManager = null;
		try {
			authenticationManager = super.authenticationManagerBean();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return authenticationManager;
	}

	@Bean(name = "failureHandler")
	public SimpleUrlAuthenticationFailureHandler simpleUrlAuthenticationFailureHandler() {
		return new SimpleUrlAuthenticationFailureHandler("/403.html");
	}

	/* 表达式控制器 */
	@Bean(name = "expressionHandler")
	public DefaultWebSecurityExpressionHandler webSecurityExpressionHandler() {
		DefaultWebSecurityExpressionHandler webSecurityExpressionHandler = new DefaultWebSecurityExpressionHandler();
		return webSecurityExpressionHandler;
	}

	/* 表达式投票器 */
	@Bean(name = "expressionVoter")
	public WebExpressionVoter webExpressionVoter() {
		WebExpressionVoter webExpressionVoter = new WebExpressionVoter();
		webExpressionVoter.setExpressionHandler(webSecurityExpressionHandler());
		return webExpressionVoter;
	}
	
	/**
	 * @--访问决策器/ 该对象的作用是用于分析当前用户登录权限是否足够访问到某一个资源URL。
	 * @--类的decision方法将会在URL元数据注入之后进行调用决策。
	 * @return
	 */
	@Bean(name = "accessDecisionManager")
	public WRAccessDecisionManager accessDecisionManager() {
		List<AccessDecisionVoter<? extends Object>> decisionVoters = new ArrayList<AccessDecisionVoter<?>>();
		decisionVoters.add(new RoleVoter());
		decisionVoters.add(new AuthenticatedVoter());
		// 启用表达式投票器
		decisionVoters.add(webExpressionVoter());
		WRAccessDecisionManager accessDecisionManager = new WRAccessDecisionManager(decisionVoters);
		return accessDecisionManager;
	}
}
