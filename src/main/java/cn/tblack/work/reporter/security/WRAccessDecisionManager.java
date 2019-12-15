package cn.tblack.work.reporter.security;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.access.vote.AbstractAccessDecisionManager;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import static cn.tblack.work.reporter.constant.RoleNameConstant.*;

/**
 * @_!_!权限决策器。用于将FilterInvocationSecurityMetadataSource中注入的角色权限信息进行验证
 * @~——~如果访问的用户具有权限，则通过验证，否则抛出AccessDeniedException
 * @author TD唐登
 * @Date:2019年12月12日
 * @Version: 1.0(测试版)
 */
public class WRAccessDecisionManager extends AbstractAccessDecisionManager {

	private static Logger log = LoggerFactory.getLogger(WRAccessDecisionManager.class);
	
	public WRAccessDecisionManager(List<AccessDecisionVoter<? extends Object>> decisionVoters) {
		super(decisionVoters);
	}

	@Override
	public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes)
			throws AccessDeniedException, InsufficientAuthenticationException {
		
//		log.info("访问权限开始验证: "  + authentication);
		// 检查用户是否够权限访问资源
		// 参数authentication是从spring的全局缓存SecurityContextHolder中拿到的，里面是用户的权限信息
		// 参数object是url
		// 参数configAttributes所需的权限
		if (configAttributes == null) {
			return;
		}
		//遍历所需要的权限信息。
		Iterator<ConfigAttribute> ite = configAttributes.iterator();
		//如果存在一个匹配的话， 那么则表示存在权限访问
		while (ite.hasNext()) {
			
			ConfigAttribute ca = ite.next();
			
			/**访问需要的角色信息*/
			String needRole = ((SecurityConfig) ca).getAttribute(); 
			
			/**将访问需要的角色信息与当前请求范围的用户角色信息进行比较。 如果该用户为管理员，则无需要验证*/
			for (GrantedAuthority ga : authentication.getAuthorities()) {	

				/**比较访问资源所需要具备的权限信息*/
				if (needRole.equalsIgnoreCase(ga.getAuthority()) || 
						ROLE_ADMIN.equalsIgnoreCase(ga.getAuthority())) {		
					return;
				}
			}
		}
		log.error("访问的用户不具备权限，抛出异常");
		/**如果登录用户没有具备权限，那么则抛出异常*/
		throw new AccessDeniedException("当前用户权限过低~");
	}

	@Override
	public boolean supports(ConfigAttribute attribute) {
		return true;
	}

	/**
	 * Iterates through all <code>AccessDecisionVoter</code>s and ensures each can
	 * support the presented class.
	 * <p>
	 * If one or more voters cannot support the presented class, <code>false</code>
	 * is returned.
	 *
	 * @param clazz the type of secured object being presented
	 * @return true if this type is supported
	 */
	@Override
	public boolean supports(Class<?> clazz) {
		return true;
	}
}
