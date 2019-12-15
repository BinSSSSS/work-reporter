package cn.tblack.work.reporter.sys.service;

import java.util.List;

import org.springframework.data.domain.Sort;

import cn.tblack.work.reporter.service.AbstractDBService;
import cn.tblack.work.reporter.sys.entity.SysResources;

public interface SysResourcesService extends AbstractDBService<String>{

	/**
	 * @ 通过角色Id来查找 Resources对象-
	 * @return
	 */
	List<SysResources> findByRoleId(String roleId);
	
	List<SysResources> findAll();

	List<SysResources> findAll(Sort sort);

	List<SysResources> findAllById(Iterable<String> ids);

	List<SysResources> saveAll(Iterable<SysResources> entities);

	void flush();

	SysResources saveAndFlush(SysResources entity);

	void deleteInBatch(Iterable<SysResources> entities);

	void deleteAllInBatch();

	SysResources getOne(String id);

	SysResources save(SysResources entity);

	SysResources findById(String id);

	boolean existsById(String id);

	long count();

	void deleteById(String id);

	void delete(SysResources entity);

	void deleteAll(Iterable<? extends SysResources> entities);

	void deleteAll();
	
	/**
	 * @---找到一个资源的对应角色信息。 如果该资源不存在角色信息， 那么则不会返回该资源。 主要用于配置访问该资源的角色信息。
	 * @return
	 */
	List<SysResources> findAllResourcesHasRole();
	
	/**
	 * @~_~通过角色id来查找该角色能够访问的资源id列表
	 * @param roleId
	 * @return
	 */
	List<String> findResIdListByRoleId(String roleId);
	
	/**
	 * @!_!通过ResUrl模糊查找
	 * @param resUrl
	 * @return
	 */
	List<SysResources> fuzzyFindByResUrl(String resUrl);
}
