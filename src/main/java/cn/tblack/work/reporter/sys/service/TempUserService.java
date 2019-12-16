package cn.tblack.work.reporter.sys.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.Authentication;

import cn.tblack.work.reporter.service.AbstractDBService;
import cn.tblack.work.reporter.social.enums.SocialPlatformType;
import cn.tblack.work.reporter.sys.entity.SysUser;
import cn.tblack.work.reporter.sys.entity.TempUser;

public interface TempUserService extends AbstractDBService<Integer>{

	List<TempUser> findAll();

	List<TempUser> findAll(Sort sort);

	List<TempUser> findAllById(Iterable<Integer> ids);

	List<TempUser> saveAll(Iterable<TempUser> entities);

	void flush();

	TempUser saveAndFlush(TempUser entity);

	void deleteInBatch(Iterable<TempUser> entities);

	void deleteAllInBatch();

	TempUser getOne(Integer id);

	TempUser save(TempUser entity);

	TempUser findById(Integer id);
	
	Page<TempUser> findAll(Pageable pageable);
	
	boolean existsById(Integer id);

	long count();

	void deleteById(Integer id);

	void delete(TempUser entity);

	void deleteAll(Iterable<? extends TempUser> entities);

	void deleteAll();
	@Query("SELECT u FROM #{#entityName} u WHERE u.openId = :openId")
	TempUser findByOpenId(String openId);
	
	
	@Query("SELECT u FROM #{#entityName} u WHERE u.nickname = :nickname")
	List<TempUser> findByNickname(String nickname);
	
	/**
	 * @~_~根据用户昵称和第三方社交平台进行查找临时用户
	 * @param nickname
	 * @param socialPlatform
	 * @return
	 */
	List<TempUser> findByNicknameAndSocialPlatform(String nickname,
			 SocialPlatformType socialPlatform);
	
	/**
	 * @~_~根据OpenId和第三方社交平台进行查找临时用户
	 * @param openId
	 * @param socialPlatform
	 * @return
	 */
	TempUser findByOpenIdAndSocialPlatform(String openId,
			SocialPlatformType socialPlatform);
	/**
	 * @~——~通过一个权限信息来查找临时用户。如果该权限信息不是一个SocialLoginAuthenticaion的话，返回null
	 * @~_~如果该权限信息不具备游客权限，返回null
	 * @param auth
	 * @return
	 */
	TempUser findTempUserByAuthentication(Authentication auth);
	
	/**
	 * @~_~将一个临时用户的信息转换为一个SysUser用户信息。  主要将TempUser的openId作为SysUser的id。
	 * @~+~将TempUser的nickname和OpenId和SocialPlatform作为SysUser的用户名，
	 * @param user
	 * @return
	 */
	SysUser converterToSysUser(TempUser tuser);
}
