package cn.tblack.work.reporter.sys.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import cn.tblack.work.reporter.sys.entity.SysResRole;

public interface SysResRoleDao extends JpaRepository<SysResRole, Integer>{
	
	
	/**
	 * @__~~撤销某一个资源文件所需要的角色信息。 在数据库中进行删除操作
	 * @param resId
	 * @param roleId
	 */
	@Modifying(clearAutomatically = true)
	@Query("DELETE #{#entityName} srr WHERE srr.resId = :resId AND srr.roleId = :roleId")
	void revokeResRole(@Param("resId") String resId,@Param("roleId") String roleId);
}
