package cn.tblack.work.reporter.role.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.tblack.work.reporter.model.BatchAuthorize;
import cn.tblack.work.reporter.result.WebResult;
import cn.tblack.work.reporter.sys.entity.SysResRole;
import cn.tblack.work.reporter.sys.entity.SysResources;
import cn.tblack.work.reporter.sys.entity.SysRole;
import cn.tblack.work.reporter.sys.service.SysResRoleService;
import cn.tblack.work.reporter.sys.service.SysResourcesService;
import cn.tblack.work.reporter.sys.service.SysRoleService;
import cn.tblack.work.reporter.util.DatabaseTableIdGenerator;

@RestController
//@HasAdminRole
@RequestMapping("/role")
public class RestRoleController {

	private static Logger log = LoggerFactory.getLogger(RestRoleController.class);

	@Autowired
	private SysResourcesService resService;
	
	@Autowired
	private SysRoleService roleService;

	@Autowired
	private SysResRoleService resRoleService;
	
	@RequestMapping(value = "/all-role")
	public List<SysRole> getRoleList() {

		List<SysRole> list = null;
		try {

			list = roleService.findAll();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("查询角色列表出错，出错信息为: " + e.getMessage());
		}

		return list;
	}

	/**
	 * @~_~通过传递的多个拼接的角色id来删除角色对象
	 * @param ids
	 * @return
	 */
	@PostMapping(value = "/delete")
	public WebResult deleteRole(String ids) {

		WebResult result = new WebResult();

		String[] delIds = ids.split(",");

		if (delIds == null || delIds.length == 0) {

			result.setMsg("无角色可供删除~");
			result.setSuccess(false);
			return result;
		}

		// 在数据库内进行数据的删除操作
		StringBuffer buffer = new StringBuffer();
		result.setSuccess(false);
		try {
			for (String id : delIds) {
				try {
					roleService.deleteById(id);
				} catch (Exception e) {
					log.info("删除角色失败~角色id为: " + id + "，错误信息为: " + e.getMessage());
					e.printStackTrace();
					buffer.append("删除角色失败~角色id为: " + id + "。\n");
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
	 * @~__~拿到指定id角色的具体信息
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/get")
	public SysRole getRole(String id) {
		
		SysRole role = null ;
		
		if(id == null || id.isEmpty())
			return role;
		
		try {
			role = roleService.findById(id);
			role.setResIdList(resService.findResIdListByRoleId(id));
			
			if(role.getParentId() != null && !Strings.isEmpty(role.getParentId())) {
				SysRole parentRole =  roleService.findById(role.getParentId());
				if(parentRole != null)
					role.setParentName(parentRole.getName());
			}
			
		}catch(Exception e) {
			e.printStackTrace();
			log.error("查询id为:[ " + id + " ]的角色失败！ 失败信息为: " +  e.getMessage());
		}
		
		return role;
	}
	
	@PostMapping(value = "/update")
	public WebResult updateRole(@RequestBody SysRole sysRole) {
		
		WebResult result = new WebResult();
		
		log.info("当前传递的角色信息为: " + sysRole);
		
		try {
			
			SysRole orgRole =  roleService.findById(sysRole.getId());
			
			if(orgRole == null) {
				result.setMsg("需要更新的角色不存在");
				result.setSuccess(false);
				return result;
			}

			//更新操作
			roleService.save(sysRole);
			
			//更新该角色拥有的资源信息
			List<String> orgResIdList = resService.findResIdListByRoleId(orgRole.getId());
			List<String> newResIdList = sysRole.getResIdList();
			
			for (String newResId : newResIdList) {
				
				boolean hasRes = false;		//表示该角色拥有之前的资源
				for (String orgResId : orgResIdList) {
					///如果该角色之前拥有该资源，在列表中删除该资源
					if(orgResId.equals(newResId)) {
						orgResIdList.remove(orgResId);
						hasRes = true;
						break;
					}
				}
				//如果该角色之前未拥有该资源~添加该资源
				if(!hasRes) {
					SysResRole resRole =  new SysResRole();
					resRole.setResId(newResId);
					resRole.setRoleId(sysRole.getId());
					resRoleService.save(resRole);
				}
			}
			
			//删除原先分配的资源~
			for (String resId : orgResIdList) {
				resRoleService.revokeResRole(resId, sysRole.getId());
			}
			result.setMsg("更新成功~");
			result.setSuccess(true);
		}catch(Exception e) {
			e.printStackTrace();
			log.error("更新角色信息出错~出错信息为: " + e.getMessage());
			result.setMsg("更新出错~服务器正忙~");
			result.setSuccess(false);
		}
		
		return result;
	}
	/**
	 * @!!_!批量授权
	 * @param batchAuth
	 * @return
	 */
	@PostMapping(value = "/batch-authorize")
	public WebResult batchAuthorize(@RequestBody BatchAuthorize batchAuth) {
		WebResult result = new WebResult();
		
		log.info("传递的批量授权对象信息为: " +  batchAuth);
		
		if(batchAuth == null || batchAuth.isEmpty()) {
			result.setMsg("批量授权对象内的资源和角色信息不能为空~");
			result.setSuccess(false);
			return result;
		}
		
		try {
			
			/**
			 * 1.如果批量授权的资源已经包含了指定角色信息~那么则无需要授权~。
			 * 2.首先查询出资源所拥有的角色信息.并进行验证
			 * */
			List<SysResources> resList  = new ArrayList<>();
			for (String	 resId : batchAuth.getResIdList()) {
				resList.add(resService.findById(resId));
			}			
			//验证资源所拥有的角色信息。如果没有~则添加新的角色信息
			for (SysResources res : resList) {
				
				//拿到当前需要批量授权的角色信息
				for (String roleId : batchAuth.getRoleIdList()) {
					boolean hasRole = false;
					//将资源的角色信息和需要授权的角色信息比较- 如果当前批量授权的角色信息已经存在了，那么则无需要再次授权。否则需要再次授权
					for (SysRole role : res.getRoles()) {
						if(role.getId().equals(roleId)) {
							hasRole =  true;
							break;
						}
					}
					//如果当前的资源不存在指定的角色信息。那么则需要给该资源分配角色信息。
					if(!hasRole) {
						SysResRole resRole = new SysResRole();
						resRole.setResId(res.getId());
						resRole.setRoleId(roleId);
						resRoleService.save(resRole);
					}
				}
			}
			result.setMsg("授权成功");
			result.setSuccess(true);
			
		}catch(Exception e) {
			e.printStackTrace();
			log.error("批量授权时出错，出错信息为: " +  e.getMessage());
			result.setMsg("授权失败~服务器正忙");
			result.setSuccess(false);
		}
		
		
		return result;
	}
	
	/**
	 * @_~_~创建一个新的角色对象
	 * @param role
	 * @return
	 */
	@RequestMapping(value = "/insert")
	public WebResult insertRole(@RequestBody SysRole role) {
		
		WebResult result =  new WebResult();
		
		log.info("插入的角色信息为: " +  role);
		
		try {
			List<String> resIdList = role.getResIdList();
			role.setId(DatabaseTableIdGenerator.createRoleId(roleService));
			log.info("创建的用户id为: " + role.getId());
			//进行数据库的插入操作
			
			role = roleService.save(role);
			
			//遍历当前角色的资源树并进行资源角色的分配
			for (String resId : resIdList) {
				SysResRole resRole = new SysResRole();
				resRole.setResId(resId);
				resRole.setRoleId(role.getId());
				resRoleService.save(resRole);
			}
			result.setMsg("插入新角色成功~");
			result.setSuccess(true);
		}catch(Exception e) {
			result.setMsg("插入角色失败~服务器正忙~");
			result.setSuccess(false);
			e.printStackTrace();
			log.error("插入新角色出错。出错信息为: " + e.getMessage());
		}
		
		return result;
	}
	
	@RequestMapping(value = "/role-list")
	public List<SysRole> roleList(@RequestParam(name = "roleName",defaultValue = "")String roleName) {
		
		log.info("传递的角色名字为: " +  roleName);
		List<SysRole> list = roleService.fuzzyFindByRoleName(roleName);
		
	
		
		return list;
	}
}
