package cn.tblack.work.reporter.sys.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import cn.tblack.work.reporter.sys.dao.SysUserDao;
import cn.tblack.work.reporter.sys.entity.SysUser;
import cn.tblack.work.reporter.sys.service.SysUserService;
import cn.tblack.work.reporter.util.MD5Utils;

import static cn.tblack.work.reporter.constant.RedisCacheBeanDefinition.REDIS_CACHE_KEY_GENERATOR;
import static cn.tblack.work.reporter.constant.RedisCacheBeanDefinition.REDIS_CACHE_MANAGER;;
@Service
@Transactional
@CacheConfig(keyGenerator = REDIS_CACHE_KEY_GENERATOR,cacheManager = REDIS_CACHE_MANAGER, 
	cacheNames = "SysUser")
public class SysUserServiceImpl implements SysUserService {

	@Autowired
	private SysUserDao sysUserDao;
	
	@Override
	@Cacheable(value = "findAll")
	public List<SysUser> findAll() {
	
		return sysUserDao.findAll();
	}

	@Override
	public List<SysUser> findAll(Sort sort) {
		return sysUserDao.findAll(sort);
	}

	@Override
	public List<SysUser> findAllById(Iterable<String> ids) {
		return sysUserDao.findAllById(ids);
	}

	@Override
	public List<SysUser> saveAll(Iterable<SysUser> entities) {
		return sysUserDao.saveAll(entities);
	}

	@Override
	public void flush() {
		sysUserDao.flush();
	}

	@Override
	public SysUser saveAndFlush(SysUser entity) {
		return sysUserDao.saveAndFlush(entity);
	}

	@Override
	public void deleteInBatch(Iterable<SysUser> entities) {
		sysUserDao.deleteInBatch(entities);
	}

	@Override
	public void deleteAllInBatch() {
		sysUserDao.deleteAllInBatch();
	}

	@Override
	public SysUser getOne(String id) {
		return sysUserDao.getOne(id);
	}

	@Override
	public SysUser save(SysUser entity) {
		return sysUserDao.save(entity);
	}

	@Override
	public SysUser findById(String id) {
		return sysUserDao.findById(id).get();
	}

	@Override
	public boolean existsById(String id) {
		return sysUserDao.existsById(id);
	}

	@Override
	public long count() {
		return sysUserDao.count();
	}

	@Override
	public void deleteById(String id) {
		sysUserDao.deleteById(id);
	}

	@Override
	public void delete(SysUser entity) {
		sysUserDao.delete(entity);
	}

	@Override
	public void deleteAll(Iterable<? extends SysUser> entities) {
		sysUserDao.deleteAll(entities);
	}

	@Override
	public void deleteAll() {
		sysUserDao.deleteAll();
	}

	@Override
	public boolean login(String username, String password) {
		
		if(username == null || password == null) {
			return false;
		}
		return sysUserDao.findByUsernameAndPassword(username,MD5Utils.encrypt(password)) != null;
	}

	@Override
	public SysUser findByUsername(String username) {
		return sysUserDao.findByUsername(username);
	}

	@Override
	@Transactional
	public void updatePassword(SysUser user, String newPassword) {
		
		sysUserDao.updatePassword(user.getId(),MD5Utils.encrypt(newPassword));
	}

	@Override
	public SysUser findByEmailAddress(String emailAddress) {
		return sysUserDao.findByEmailAddress(emailAddress);
	}
	
	@Override
	public Page<SysUser> findAll(Pageable pageable){
		return sysUserDao.findAll(pageable);
	}

	@Override
	public Page<SysUser> findAllByUsername(String username, Pageable pageable) {
		
		return sysUserDao.findAllByUsername(username,pageable);
	}
	
	@Override
	public Page<SysUser> findAllByPhoneNum(String phone,Pageable pageable){
		return sysUserDao.findAllByPhoneNum(phone,pageable);
	}

	@Override
	public boolean canBeRegister(SysUser user) {
		return sysUserDao.findByUsername(user.getUsername()) == null;
	}

}
