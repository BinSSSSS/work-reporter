package cn.tblack.work.reporter.sys.service;

import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Sort;

import cn.tblack.work.reporter.service.AbstractDBService;
import cn.tblack.work.reporter.sys.entity.SysRole;
import cn.tblack.work.reporter.sys.entity.SysUser;
import cn.tblack.work.reporter.sys.entity.SysUserRole;


public interface SysUserRoleService extends AbstractDBService<Integer>{
	
	List<SysUserRole> findAll();

	List<SysUserRole> findAll(Sort sort);

	List<SysUserRole> findAllById(Iterable<Integer> ids);

	List<SysUserRole> saveAll(Iterable<SysUserRole> entities);

	void flush();

	SysUserRole saveAndFlush(SysUserRole entity);

	void deleteInBatch(Iterable<SysUserRole> entities);

	void deleteAllInBatch();

	SysUserRole getOne(Integer id);

	SysUserRole save(SysUserRole entity);

	SysUserRole findById(Integer id);
	
	boolean existsById(Integer id);

	long count();

	void deleteById(Integer id);

	void delete(SysUserRole entity);

	void deleteAll(Iterable<? extends SysUserRole> entities);

	void deleteAll();
	
	/**
	 * @_-_^_- 按照角色id和用户id来进行数据的删除操作
	 * @param userId
	 * @param roleId
	 */
	void revokeUserRole(String userId, String roleId);
	
	/**
	 * @!_!分配角色
	 * @param user
	 */
	boolean grantUserRole(SysUser user);
	
	/**
	 * @分配一个管理员角色给指定的用户
	 * @param user
	 * @return
	 */
	boolean grantAdminRole(SysUser user);
	
	/**
	 * @~_~通过用户id来查找该用户所对应的角色信息
	 * @param id
	 * @return
	 */
	Set<SysRole> findUserRoleByUserId(String id);
}
