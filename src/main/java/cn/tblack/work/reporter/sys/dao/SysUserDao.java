package cn.tblack.work.reporter.sys.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import cn.tblack.work.reporter.sys.entity.SysUser;

public interface SysUserDao extends JpaRepository<SysUser, String>{

	@Query("SELECT u FROM #{#entityName} u WHERE u.username = :username")
	SysUser findByUsername(@Param("username") String username);

	/**
	 * @ -- 根据用户名和密码进行数据的查询
	 * @param username
	 * @param password
	 * @return
	 */
	@Query("SELECT u FROM #{#entityName} u WHERE u.username = :username AND u.password = :password")
	SysUser findByUsernameAndPassword(@Param("username") String username,@Param("password") String password);
	
	/**
	 * @=-=密码修改操作。
	 * @param user
	 * @param newPassword
	 */
	@Modifying(clearAutomatically = true)
	@Query("UPDATE #{#entityName} u SET u.password = :password WHERE u.id = :id")
	void updatePassword(@Param("id") String id,@Param("password") String newPassword);
	
	/**
	 * @=~=邮箱查找用户操作
	 * @param emailAddress
	 * @return
	 */
	@Query("SELECT u FROM #{#entityName} u WHERE u.email = :email")
	SysUser findByEmailAddress(@Param("email") String emailAddress);
	
	/**
	 * @~-~通过用户名来模糊分页查找
	 * @param username
	 * @param pageable
	 * @return
	 */
	@Query("SELECT u FROM #{#entityName} u WHERE u.username LIKE  %:username%")
	Page<SysUser> findAllByUsername(@Param("username")String username, Pageable pageable);
	
	/**
	 * @~——~通过手机号来模糊分页查找
	 * @param phone
	 * @param pageable
	 * @return
	 */
	@Query("SELECT u FROM #{#entityName} u WHERE u.phone LIKE %:phone%")
	Page<SysUser> findAllByPhoneNum(@Param("phone")String phone, Pageable pageable);
	
}
