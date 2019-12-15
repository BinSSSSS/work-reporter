package cn.tblack.work.reporter.sys.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import cn.tblack.work.reporter.sys.entity.SysResources;

public interface SysResourcesDao extends JpaRepository<SysResources, String>{
	
	/**
	 * @-通过角色来查询SysResources表信息
	 * @param roleId
	 * @return
	 */
	@Query("SELECT r FROM #{#entityName} r , SysResRole srr"
			+ " WHERE srr.roleId = :roleId AND srr.resId = r.id")
	List<SysResources> findByRoleId(@Param("roleId") String roleId);

	/**
	 * @-使用连接查询来查询到sys_resource表内存在角色的对象-并且该对象未被删除且角色对象状态为0
	 * @-注: SysResRole是存储 SysResources 和 SysRole 关联信息的表
	 * @return
	 */
	@Query("SELECT r FROM #{#entityName} r "
			+ " LEFT JOIN SysResRole srr ON r.id = srr.resId AND r.isDelete = 0 "
			+ " LEFT JOIN SysRole sr ON sr.id = srr.roleId AND sr.state = 0"
			+ " WHERE 1 = 1")
	List<SysResources> findAllResourcesHasRole();

	/**
	 * @~——~通过角色id来查找所属资源列表的id
	 * @param roleId
	 * @return
	 */
	@Query("SELECT r.id FROM #{#entityName} r, SysResRole srr "
			+ " WHERE r.id = srr.resId AND srr.roleId = :roleId")
	List<String> findResIdListByRoleId(@Param("roleId") String roleId);

	@Query("SELECT r FROM #{#entityName} r WHERE r.resUrl LIKE %:resUrl%")
	List<SysResources> fuzzyFindByResUrl(String resUrl);
}
