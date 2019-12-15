package cn.tblack.work.reporter.sys.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import cn.tblack.work.reporter.sys.entity.SysUserRole;

public interface SysUserRoleDao extends JpaRepository<SysUserRole, Integer>{

	/**
	 * @__^_^撤销指定用户的指定角色对象
	 * @param userId
	 * @param roleId
	 */
	@Modifying(clearAutomatically = true)
	@Query("DELETE #{#entityName} ur WHERE ur.roleId = :roleId AND ur.userId = :userId")
	void revokeUserRole(@Param("userId") String userId,@Param("roleId") String roleId);

	
	
}
