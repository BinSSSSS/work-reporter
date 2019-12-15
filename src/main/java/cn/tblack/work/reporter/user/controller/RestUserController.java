package cn.tblack.work.reporter.user.controller;

import java.io.IOException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.tblack.work.reporter.page.LaYuiPage;
import cn.tblack.work.reporter.result.WebResult;
import cn.tblack.work.reporter.sys.entity.SysUser;
import cn.tblack.work.reporter.sys.entity.SysUserRole;
import cn.tblack.work.reporter.sys.service.SysUserRoleService;
import cn.tblack.work.reporter.sys.service.SysUserService;
import cn.tblack.work.reporter.util.excel.ExcelUtil;

/**
 * @--=^-^=-- 对于用户管理的操作
 * 
 * @author TD唐登
 * @Date:2019年11月25日
 * @Version: 1.0(测试版)
 */
@RestController
@RequestMapping("/user")
//@HasAdminRole
public class RestUserController {

	private static Logger log = LoggerFactory.getLogger(RestController.class);

	@Autowired
	private SysUserService userService;

	@Autowired
	private SysUserRoleService userRoleService;

	/**
	 * @--=-=--查询用户列表
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	@RequestMapping(value = "/user-list")
	public LaYuiPage<SysUser> userList(@RequestParam(name = "page", defaultValue = "0") Integer pageNo,
			@RequestParam(name = "limit", defaultValue = "10") Integer pageSize,
			@RequestParam(name = "searchText") String searchText) {

		Pageable pageable = PageRequest.of(pageNo - 1, pageSize);

		List<SysUser> userList = findUserByMultiCritria(searchText, pageable);

		log.info("最终组合在一起的数据为: " + userList);

		return new LaYuiPage<SysUser>(new PageImpl<SysUser>(userList, pageable, userList.size()));
	}

	/**
	 * @=-=批量的用户删除操作
	 * @param ids
	 * @return
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public WebResult deleteUser(String ids) {
		WebResult result = new WebResult();

		String[] delIds = ids.split(",");

		if (delIds == null || delIds.length == 0) {
			result.setMsg("删除的用户不存在~");
			result.setSuccess(false);
			return result;
		}

		// 在数据库内进行数据的删除操作
		StringBuffer buffer = new StringBuffer();
		result.setSuccess(false);
		try {
			for (String id : delIds) {
				try {
					userService.deleteById(id);
				} catch (Exception e) {
					log.info("删除用户失败~用户id为: " + id + "，错误信息为: " + e.getMessage());
					e.printStackTrace();
					buffer.append("删除用户失败~用户id为: " + id + "。\n");
					result.setMsg(buffer.toString());
				}

			}
			if (buffer.toString().isEmpty()) {
				result.setMsg("全部删除成功~");
				result.setSuccess(true);
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		return result;
	}

	/**
	 * @=-=根据id查询某一个用户
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/get")
	public SysUser getUser(String id) {

		SysUser user = null;
		if (id == null || id.isEmpty()) {
			return user;
		}
		try {
			user = userService.findById(id);
			// 将敏感信息设置为空
			user.setPassword("");
			user.setCredentialsSalt("");
			user.setRoleIds();
		} catch (Exception e) {
			log.error("查询用户出现错误，错误信息为: " + e.getMessage());
			e.printStackTrace();
		}
		log.info("查询到的用户信息为: " + user);

		return user;
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public WebResult updateUser(@RequestBody SysUser user, HttpServletRequest request) {

		WebResult result = new WebResult();

		log.info("传递的用户信息为: " + user);

		try {
			// 首先在数据库内查找当前对象的id是否存在。如果该id不存在，则不进行更新操作
			SysUser sUser = userService.findById(user.getId());
			if (sUser == null) {
				result.setMsg("需要更新的用户不存在");
				result.setSuccess(false);
				return result;
			}
			sUser.setRoleIds();

			// 密码不能修改
			user.setPassword(sUser.getPassword());
			// 无论前台是否传递了一个用户名对象，后台更新的时候忽略掉用户名-防止用户错误的提交修改用户名
			user.setUsername(sUser.getUsername());
			// 隐私信息都不能修改
			user.setCredentialsSalt(sUser.getCredentialsSalt());
			userService.save(user);

			// 重新给该用户分配权限~
			List<String> orgRoleIdList = sUser.getRoleIds();
			List<String> newRoleIdList = user.getRoleIds();

			// 对新的角色进行检测~如果新的角色信息之前不存在的话，那么则进行添加操作。并在之前的角色列表中进行移除操作
			for (String newRole : newRoleIdList) {

				boolean hasRole = false; // 表示当前用户是否已经由该新的角色信息

				for (String orgRole : orgRoleIdList) {
					// 当前用户角色存在的话，删除该角色。
					if (newRole.equals(orgRole)) {
						orgRoleIdList.remove(orgRole);
						hasRole = true;
						break;
					}
				}
				// 当前角色之前不存在。添加该角色权限到数据库
				if (!hasRole) {
					SysUserRole sysUserRole = new SysUserRole();
					sysUserRole.setRoleId(newRole);
					sysUserRole.setUserId(user.getId());
					userRoleService.save(sysUserRole);
				}
			}

			// 在上一步操作之后，所有新的角色信息已经完成分配。 如果当前仍然存在原始角色信息的话，那么则删除原始的角色信息
			for (String orgRole : orgRoleIdList) {
				userRoleService.revokeUserRole(user.getId(), orgRole);
			}

			result.setMsg("更新成功");
			result.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			log.info("更新用户[ " + user.getUsername() + "]的数据失败~失败原因为: " + e.getMessage());
			result.setMsg("更新失败~服务器正忙");
			result.setSuccess(false);
		}

		return result;
	}

	/**
	 * @~-~使用 excel 文件的方式将传递的用户信息进行导出并传递给前台进行下载。
	 */
	@RequestMapping(value = "/export")
	public void exportUserByExcel(String searchText, HttpServletResponse response, HttpServletRequest request) {

		try {
			List<SysUser> userList = findUserByMultiCritria(searchText, PageRequest.of(0, 10));
			response.setContentType("application/vnd.ms-excel");
			String fileName = "用户信息-" + new SimpleDateFormat("yyyy-MM-dd hh-mm").format(new Date());
			fileName = URLEncoder.encode(fileName, "UTF-8");
			response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xls");
			ServletOutputStream out = response.getOutputStream();
			// 创建工具类.
			ExcelUtil<SysUser> util = new ExcelUtil<SysUser>(SysUser.class);
			// 导出
			util.exportExcel(userList, "用户", out, 2, null, true);

		} catch (Exception e) {
			try {
				response.sendError(HttpStatus.BAD_REQUEST.value(), "错误的请求方式~请稍后重试~");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
			log.error("导出用户信息出错~出错原因为: " + e.getMessage());
		}

	}

	/**
	 * @=-=使用多种方式来查询用户信息。 @=-=当前使用了根据手机号模糊查找、根据用户名模糊查找。 将所有模糊查找的内容整合到一个数组中进行返回
	 * @param searchText
	 * @param pageable
	 * @return
	 */
	private List<SysUser> findUserByMultiCritria(String searchText, Pageable pageable) {
		Page<SysUser> usernamePage = userService.findAllByUsername(searchText, pageable);
		Long count = userService.count();
		// 组合多个查询结果
		List<SysUser> usernameList = usernamePage.getContent();
		List<SysUser> userList = new ArrayList<>();
		// 第一次查询到所有的数据，之后无需要整合
		if (usernamePage.getSize() == count) {
			userList = usernameList;
		} else {
			Page<SysUser> phoneNumPage = userService.findAllByPhoneNum(searchText, pageable);

			List<SysUser> phoneList = phoneNumPage.getContent();

			// 如果通过手机号码查询到了数据库内所有数据，无需要再次整合
			if (phoneList.size() == count) {
				userList = phoneList;
			} else {
				Set<SysUser> userSet = new HashSet<SysUser>();
				userSet.addAll(usernameList);
				userSet.addAll(phoneList);
				userList.addAll(userSet);
			}
		}
		// 清空隐私信息
		for (SysUser sysUser : userList) {
			sysUser.setPassword("");
			sysUser.setCredentialsSalt("");
		}
		return userList;
	}
}
