package cn.tblack.work.reporter.sys.dao;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import cn.tblack.work.reporter.social.enums.SocialPlatformType;
import cn.tblack.work.reporter.sys.entity.TempUser;
import io.lettuce.core.dynamic.annotation.Param;

public interface TempUserDao extends JpaRepository<TempUser, Integer>{

	
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
	@Query("SELECT u FROM #{#entityName} u WHERE u.nickname = :nickname AND u.socialPlatform = :socialPlatform")
	List<TempUser> findByNicknameAndSocialPlatform(@Param("nickname") String nickname,
			@Param("socialPlatform") SocialPlatformType socialPlatform);
	
	/**
	 * @~_~根据OpenId和第三方社交平台进行查找临时用户
	 * @param openId
	 * @param socialPlatform
	 * @return
	 */
	@Query("SELECT u FROM #{#entityName} u WHERE u.openId = :openId AND u.socialPlatform = :socialPlatform")
	TempUser findByOpenIdAndSocialPlatform(@Param("openId") String openId,
			@Param("socialPlatform") SocialPlatformType socialPlatform);

}
