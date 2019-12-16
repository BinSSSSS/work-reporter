package cn.tblack.work.reporter.sys.service;

import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Sort;

import cn.tblack.work.reporter.service.AbstractDBService;
import cn.tblack.work.reporter.sys.entity.SysResRole;
import cn.tblack.work.reporter.sys.entity.SysRole;

public interface SysResRoleService extends AbstractDBService<Integer>{

	List<SysResRole> findAll();

	List<SysResRole> findAll(Sort sort);

	List<SysResRole> findAllById(Iterable<Integer> ids);

	List<SysResRole> saveAll(Iterable<SysResRole> entities);

	void flush();

	SysResRole saveAndFlush(SysResRole entity);

	void deleteInBatch(Iterable<SysResRole> entities);

	void deleteAllInBatch();

	SysResRole getOne(Integer id);

	SysResRole save(SysResRole entity);

	SysResRole findById(Integer id);
	
	boolean existsById(Integer id);

	long count();

	void deleteById(Integer id);

	void delete(SysResRole entity);

	void deleteAll(Iterable<? extends SysResRole> entities);

	void deleteAll();
	
	/**
	 * @___~撤销某个资源上的所需要的角色信息
	 * @param resId
	 * @param roleId
	 */
	void revokeResRole(String resId, String roleId);
		
	/**
	 * @~_~通过资源的id来查找该资源对应的角色信息
	 * @param resId
	 * @return
	 */
	Set<SysRole> findResRoleByResId(String resId);
}
