package cn.tblack.work.reporter.util;

import java.util.UUID;


import cn.tblack.work.reporter.service.AbstractDBService;
import cn.tblack.work.reporter.sys.service.SysResourcesService;
import cn.tblack.work.reporter.sys.service.SysRoleService;
import cn.tblack.work.reporter.sys.service.SysUserService;

/**
 * @__~用来生成数据库内表的id数据
 * @author TD唐登
 * @Date:2019年11月28日
 * @Version: 1.0(测试版)
 */
public class DatabaseTableIdGenerator {

	/**
	 * @_+_通过UUID的方式来生成随机的用户id。传递一个用户服务类来检测该id是否存在，返回一个数据库内不存在的用户id
	 * @param userService
	 * @return
	 */
	public static String createUserId(SysUserService userService) {

		UUID uuid = null;
		String userId = null;
		while (true) {
			uuid = UUID.randomUUID();
			userId = removeIntervalSymbol(uuid.toString());
			if (!userService.existsById(userId))
				break;
		}

		return userId;
	}

	/**
	 * @_+_通过UUID的方式来生成随机的角色id。传递一个角色服务类来检测该id是否存在，返回一个数据库内不存在的角色id
	 * @param userService
	 * @return
	 */
	public static String createRoleId(SysRoleService roleService) {
		UUID uuid = null;
		String roleId = null;
		while (true) {
			uuid = UUID.randomUUID();
			roleId = removeIntervalSymbol(uuid.toString());
			if (!roleService.existsById(roleId))
				break;
		}

		return roleId;
	}
	/**
	 * @_+_通过UUID的方式来生成随机的资源id。传递一个资源服务类来检测该id是否存在，返回一个数据库内不存在的资源id
	 * @param userService
	 * @return
	 */
	public static String createResId(SysResourcesService resService) {
		UUID uuid = null;
		String resId = null;
		while (true) {
			uuid = UUID.randomUUID();
			resId = removeIntervalSymbol(uuid.toString());
			if (!resService.existsById(resId))
				break;
		}

		return resId;
	}
	
	
	/**
	 * @-去除UUID字符的-字符
	 * @param source
	 * @return
	 */
	private static String removeIntervalSymbol(String source) {

		StringBuffer buffer = new StringBuffer(source);

		int index = buffer.indexOf("-");

		while (index != -1) {
			buffer.replace(index, index + 1, "");
			index = buffer.indexOf("-");
		}

		return buffer.toString();
	}
	
	/**
	 * @!_!泛型方法，用于生成数据库内的表的id，该表的id为String类型，传递一个继承自 JpaRpository的Dao层对象进行生成
	 * @param service
	 * @return
	 */
	public static  <T extends AbstractDBService<String>> String  generatorTableId(T service){
		
		UUID uuid = null;
		String id = null;
		while (true) {
			uuid = UUID.randomUUID();
			id = removeIntervalSymbol(uuid.toString());
			if (!service.existsById(id))
				break;
		}
		
		return id;
	}
}
