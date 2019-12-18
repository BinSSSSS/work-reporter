package cn.tblack.work.reporter.auth.aop;

import java.util.Collection;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import cn.tblack.work.reporter.annotation.NeedAnyRole;
import cn.tblack.work.reporter.constant.RoleNameConstant;

/**
 * @~——~用于权限验证的切面
 * @author TD唐登
 * @Date:2019年12月18日
 * @Version: 1.0(测试版)
 */
@Aspect
@Component
public class AuthValidatorAspect {

	/**
	 * ~_~对控制层的方法进行切入
	 */
	@Pointcut("(execution (* cn.tblack.work.reporter.*.controller.*.*(..)) || "
			+ "execution (* cn.tblack.work.reporter.*.*.controller.*.*(..)))")
	public void pointcut() {

	}

	@Before("pointcut()")
	public void before(JoinPoint joinPoint){


		Class<? extends Object> clazz = joinPoint.getTarget().getClass();

		NeedAnyRole anyRole = clazz.getAnnotation(NeedAnyRole.class);

		// 如果不是带有@NeedAnyRole注解的类。无需要处理
		if (anyRole == null)
			return;

		// 拿到需要的角色对象
		String[] value = anyRole.value();

		// 拿到当前访问对象的信息
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();


		if (auth == null || auth.getAuthorities().isEmpty()) {

			// 抛出无权限访问异常
			throw new AccessDeniedException("当前用户未登录或者角色信息为空");
			
		}

		Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();

		// 如果允许所有的角色访问的话，那么只需要检查当前的访问的用户角色是否是匿名的即可。
		if (value == null || (value.length == 1 && value[0].isEmpty())) {
			for (GrantedAuthority authority : authorities) {
				// 匿名的角色对象不允许访问
				if (authority.getAuthority().equalsIgnoreCase(RoleNameConstant.ROLE_ANONYMOUS)) {
					// 抛出无权限访问异常
					throw new AccessDeniedException("当前登录的角色为匿名用户，不允许访问");
				}
			}
			return ;
		}
		// 否则则需要检查当前访问的用户是否拥有需要的角色权限
		for (GrantedAuthority authority : authorities) {
			for (String role : value) {
				if (authority.getAuthority().equalsIgnoreCase(role))
					return;
			}
		}

		// 抛出无权限访问异常
		throw new AccessDeniedException("当前登录用户不具备访问权限");

	}

}
