package cn.tblack.work.reporter.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;

import cn.tblack.work.reporter.sys.entity.SysResources;
import cn.tblack.work.reporter.sys.entity.SysRole;
import cn.tblack.work.reporter.sys.service.SysResRoleService;
import cn.tblack.work.reporter.sys.service.SysResourcesService;

@Component
public class WRSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

	private static Logger log =  LoggerFactory.getLogger(WRSecurityMetadataSource.class);
	
	private UrlMatcher urlMatcher = new AntUrlPathMatcher();
	/** 将所有的角色和url的对应关系缓存起来 **/
	@Autowired
	private SysResourcesService resourcesService;
	
	@Autowired
	private SysResRoleService resRoleService;

	/**
	 * 参数是要访问的url，返回这个url对于的所有权限（或角色）
	 */
	@Override
	public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
		
		// 将参数转为url
		String url = ((FilterInvocation) object).getRequestUrl();
		
//		log.info("当前访问的Url为: " +  url);
		// url 要去除参数。 否则会过滤不掉
		url = url.split("[?]")[0];
		
		// 匹配所有的url，并对角色去重
		Set<String> roles = new HashSet<String>();
		try {
			//拿到的是当前URL访问所需要的角色信息。 从数据库中查找
			List<SysResources> allRes = resourcesService.findAllResourcesHasRole();
			for (SysResources res : allRes) {
				if (urlMatcher.pathMatchesUrl(res.getResUrl(), url)) {
					
					/*如今存在一个问题，那就是在使用JoinTable进行关联字段查询的属性不会被注入到Redis缓存中。
					 *@所以暂时性的解决办法：如果当前资源的角色信息为空，那么则再次手动查找一次。*/
					if(res.getRoles() == null) {
						res.setRoles(resRoleService.findResRoleByResId(res.getId()));
					}
					if(res.getRoles() != null) {
						for (SysRole role : res.getRoles()) {
							roles.add(role.getRoleKey());
						}
					}
				}
			}
		} catch (Exception e) {
			log.error("访问指定URL资源的数据库出错，出错信息为: " + e.getMessage());
			e.printStackTrace();
		}
		Collection<ConfigAttribute> cas = new ArrayList<ConfigAttribute>();
		for (String role : roles) {
			ConfigAttribute ca = new SecurityConfig(role);
			cas.add(ca);
		}
		//如果当前资源未设置一个访问的角色，那么则自动分配一个用户角色。
//		if(cas.isEmpty()) {
//			cas.add( new SecurityConfig("ROLE_USER"));
//		}
		return cas;
	}

	@Override
	public Collection<ConfigAttribute> getAllConfigAttributes() {
		return null;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return true;
	}
}
