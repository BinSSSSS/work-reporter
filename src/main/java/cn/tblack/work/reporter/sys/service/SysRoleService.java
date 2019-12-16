package cn.tblack.work.reporter.sys.service;

import java.util.List;

import org.springframework.data.domain.Sort;

import cn.tblack.work.reporter.service.AbstractDBService;
import cn.tblack.work.reporter.sys.entity.SysRole;

public interface SysRoleService extends AbstractDBService<String>{
	
	List<SysRole> findAll();

	List<SysRole> findAll(Sort sort);

	List<SysRole> findAllById(Iterable<String> ids);

	List<SysRole> saveAll(Iterable<SysRole> entities);

	void flush();

	SysRole saveAndFlush(SysRole entity);

	void deleteInBatch(Iterable<SysRole> entities);

	void deleteAllInBatch();

	SysRole getOne(String id);

	SysRole save(SysRole entity);

	SysRole findById(String id);
	
	boolean existsById(String id);

	long count();

	void deleteById(String id);

	void delete(SysRole entity);

	void deleteAll(Iterable<? extends SysRole> entities);

	void deleteAll();
	
	/**
	 * @!_!按照角色名查找角色
	 * @param roleUser
	 * @return
	 */
	SysRole findByRoleName(String roleName);
	
	/**
	 * @!_!通过角色名进行模糊查找
	 * @param roleName
	 * @return
	 */
	List<SysRole> fuzzyFindByRoleName(String roleName);
	
}
