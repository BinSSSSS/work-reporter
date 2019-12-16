package cn.tblack.work.reporter.sys.dao;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import cn.tblack.work.reporter.sys.entity.SysRole;
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

	/**
	 * @~——~通过用户id来查找该用户对应的角色信息
	 * @param userId
	 * @return
	 */
	@Query("SELECT r FROM SysRole r , #{#entityName} ur WHERE ur.userId = :userId AND ur.roleId = r.id")
	Set<SysRole> findUserRoleByUserId(String userId);	
}
