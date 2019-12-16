package cn.tblack.work.reporter.sys.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import cn.tblack.work.reporter.sys.entity.SysRole;

public interface SysRoleDao extends JpaRepository<SysRole, String>{

	
	@Query("SELECT r FROM #{#entityName} r WHERE r.roleKey = :roleName")
	SysRole findByRoleName(String roleName);
	
	@Query("SELECT r FROM #{#entityName} r WHERE r.name LIKE %:roleName%")
	List<SysRole> fuzzyFindByRoleName(String roleName);
	

}
