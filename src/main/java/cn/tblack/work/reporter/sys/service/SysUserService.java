package cn.tblack.work.reporter.sys.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import cn.tblack.work.reporter.service.AbstractDBService;
import cn.tblack.work.reporter.sys.entity.SysUser;

public interface SysUserService extends AbstractDBService<String>{
	
	Page<SysUser> findAll(Pageable pageable);
	
	List<SysUser> findAll();

	List<SysUser> findAll(Sort sort);

	List<SysUser> findAllById(Iterable<String> ids);

	List<SysUser> saveAll(Iterable<SysUser> entities);

	void flush();

	SysUser saveAndFlush(SysUser entity);

	void deleteInBatch(Iterable<SysUser> entities);

	void deleteAllInBatch();

	SysUser getOne(String id);

	SysUser save(SysUser entity);

	SysUser findById(String id);
	
	boolean existsById(String id);

	long count();

	void deleteById(String id);

	void delete(SysUser entity);

	void deleteAll(Iterable<? extends SysUser> entities);

	void deleteAll();

	/**
	 * @--通过用户名和密码进行登录
	 * @param username
	 * @param password
	 * @return
	 */
	boolean login(String username, String password);

	/**
	 * @=-=通过用户名查找用户对象
	 * @param username
	 * @return
	 */
	SysUser findByUsername(String username);
	
	/**
	 * @=-=更改用户的密码。
	 * @param user
	 * @param newPassword
	 */
	void updatePassword(SysUser user, String newPassword);

	/**
	 * ~-=-~通过邮箱地址来查询用户，一个用户只能绑定一个邮箱地址。
	 * @param emailAddress
	 * @return
	 */
	SysUser finByEmailAddress(String emailAddress);
	
	/**
	 * @=-=通过用户名来模糊分页查找。
	 * @param searchText
	 * @param pageable
	 * @return
	 */
	Page<SysUser> findAllByUsername(String username, Pageable pageable);

	/**
	 * @~_~通过手机号码来模糊查找
	 * @param phone
	 * @param pageable
	 * @return
	 */
	Page<SysUser> findAllByPhoneNum(String phone, Pageable pageable);

	/**
	 * @!_!!检查某个用户名是否已经存在
	 * @param user
	 * @return
	 */
	boolean canBeRegister(SysUser user);
	
}
