package cn.tblack.work.reporter.sys.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.tblack.work.reporter.social.auth.SocialLoginAuthentication;
import cn.tblack.work.reporter.social.enums.SocialPlatformType;
import cn.tblack.work.reporter.sys.dao.TempUserDao;
import cn.tblack.work.reporter.sys.entity.SysUser;
import cn.tblack.work.reporter.sys.entity.TempUser;
import cn.tblack.work.reporter.sys.service.TempUserService;
import cn.tblack.work.reporter.util.AuthenticationVaildatorUtils;

@Service
@Transactional
public class TempUserServiceImpl implements TempUserService {

	private static Logger log =  LoggerFactory.getLogger(TempUserServiceImpl.class);
	
	@Autowired
	private TempUserDao tempUserDao;

	@Override
	public List<TempUser> findAll() {
		return tempUserDao.findAll();
	}

	@Override
	public List<TempUser> findAll(Sort sort) {
		return tempUserDao.findAll(sort);
	}

	@Override
	public List<TempUser> findAllById(Iterable<Integer> ids) {
		return tempUserDao.findAllById(ids);
	}

	@Override
	public List<TempUser> saveAll(Iterable<TempUser> entities) {
		return tempUserDao.saveAll(entities);
	}

	@Override
	public void flush() {
		tempUserDao.flush();
	}

	@Override
	public TempUser saveAndFlush(TempUser entity) {
		return tempUserDao.saveAndFlush(entity);
	}

	@Override
	public void deleteInBatch(Iterable<TempUser> entities) {
		tempUserDao.deleteInBatch(entities);
	}

	@Override
	public void deleteAllInBatch() {
		tempUserDao.deleteAllInBatch();
	}

	@Override
	public TempUser getOne(Integer id) {
		return tempUserDao.getOne(id);
	}

	@Override
	public TempUser save(TempUser entity) {
		return tempUserDao.save(entity);
	}

	@Override
	public TempUser findById(Integer id) {
		return tempUserDao.findById(id).get();
	}

	@Override
	public boolean existsById(Integer id) {
		return tempUserDao.existsById(id);
	}

	@Override
	public long count() {
		return tempUserDao.count();
	}

	@Override
	public void deleteById(Integer id) {
		tempUserDao.deleteById(id);
	}

	@Override
	public void delete(TempUser entity) {
		tempUserDao.delete(entity);
	}

	@Override
	public void deleteAll(Iterable<? extends TempUser> entities) {
		tempUserDao.deleteAll(entities);
	}

	@Override
	public void deleteAll() {
		tempUserDao.deleteAll();
	}

	@Override
	public Page<TempUser> findAll(Pageable pageable) {
		return tempUserDao.findAll(pageable);
	}

	@Override
	public TempUser findByOpenId(String openId) {
		return tempUserDao.findByOpenId(openId);
	}

	@Override
	public List<TempUser> findByNickname(String nickname) {
		return tempUserDao.findByNickname(nickname);
	}

	@Override
	public List<TempUser> findByNicknameAndSocialPlatform(String nickname, SocialPlatformType socialPlatform) {
		return tempUserDao.findByNicknameAndSocialPlatform(nickname, socialPlatform);
	}

	@Override
	public TempUser findByOpenIdAndSocialPlatform(String openId, SocialPlatformType socialPlatform) {
		return tempUserDao.findByOpenIdAndSocialPlatform(openId, socialPlatform);
	}

	@Override
	public TempUser findTempUserByAuthentication(Authentication auth) {

		// 如果是游客。那么则在临时表信息中进行查找
		if (AuthenticationVaildatorUtils.isVisitor(auth)) {
			SocialLoginAuthentication socialAuth = null;

			// 如果是第三方登录的话，注入的是一个SocialLoginAuthentication对象
			if (auth instanceof SocialLoginAuthentication) {
				socialAuth = (SocialLoginAuthentication) auth;

				// 在临时表中查找
				TempUser tuser = findByOpenIdAndSocialPlatform(socialAuth.getOpenId(),
						socialAuth.getSocialPlatform());
				
				log.info("查询的OpenId为: " +  socialAuth.getOpenId() + ",查询的平台为: " + socialAuth.getSocialPlatform());
				
				return tuser;
			}
			else {
				
				log.info("传递的Authentication不是一个自定义的SocialAuthentication");
			}
			
		}
		return null;
	}

	@Override
	public SysUser converterToSysUser(TempUser tuser) {
		
		SysUser	user = new SysUser();
		
		user.setId(tuser.getOpenId());
	
		user.setUsername(tuser.getNickname() + "-" 
				+ tuser.getSocialPlatform()  + "-" +  tuser.getOpenId());
		
		return user;
	}

}
