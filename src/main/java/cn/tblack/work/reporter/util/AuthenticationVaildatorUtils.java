package cn.tblack.work.reporter.util;

import java.util.Collection;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import static cn.tblack.work.reporter.constant.RoleNameConstant.*;

/**
 * @-`-权限验证工具
 * @author TD唐登
 * @Date:2019年12月13日
 * @Version: 1.0(测试版)
 */
public class AuthenticationVaildatorUtils {

	/**
	 * @~_~是否具备游客权限
	 * @param auth
	 * @return
	 */
	public static boolean isVisitor(Authentication auth) {

		Collection<? extends GrantedAuthority> authority = auth.getAuthorities();

		for (GrantedAuthority grantedAuthority : authority) {
			if (grantedAuthority.getAuthority().equalsIgnoreCase(ROLE_VISITOR)) {
				return true;
			}
		}

		return false;
	}

	/**
	 * @~_~是否具备其他用户权限
	 * @param auth
	 * @return
	 */
	public static boolean isOtherRole(Authentication auth) {

		Collection<? extends GrantedAuthority> authority = auth.getAuthorities();

		for (GrantedAuthority grantedAuthority : authority) {
			if (grantedAuthority.getAuthority().equalsIgnoreCase(ROLE_OTHER)) {
				return true;
			}
		}

		return false;
	}

	/**
	 * @~_~是否具备普通用户权限
	 * @param auth
	 * @return
	 */
	public static boolean isUserRole(Authentication auth) {

		Collection<? extends GrantedAuthority> authority = auth.getAuthorities();

		for (GrantedAuthority grantedAuthority : authority) {
			if (grantedAuthority.getAuthority().equalsIgnoreCase(ROLE_USER)) {
				return true;
			}
		}

		return false;
	}

	/**
	 * @~_~是否具备管理员权限
	 * @param auth
	 * @return
	 */
	public static boolean isAdmin(Authentication auth) {

		Collection<? extends GrantedAuthority> authority = auth.getAuthorities();

		for (GrantedAuthority grantedAuthority : authority) {
			if (grantedAuthority.getAuthority().equalsIgnoreCase(ROLE_ADMIN)) {
				return true;
			}
		}

		return false;
	}

	/**
	 * @~_~是否具备指定的角色权限
	 * @param auth
	 * @return
	 */
	public static boolean isRoleUser(Authentication auth, String role) {

		Collection<? extends GrantedAuthority> authority = auth.getAuthorities();

		for (GrantedAuthority grantedAuthority : authority) {
			if (grantedAuthority.getAuthority().equalsIgnoreCase(role)) {
				return true;
			}
		}

		return false;
	}
}
