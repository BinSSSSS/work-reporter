package cn.tblack.work.reporter.sys.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.tblack.work.reporter.sys.dao.SysRoleDao;
import cn.tblack.work.reporter.sys.entity.SysRole;
import cn.tblack.work.reporter.sys.service.SysRoleService;

@Service
@Transactional
public class SysRoleServiceImpl implements SysRoleService {

	@Autowired
	private SysRoleDao sysRoleDao;
	
	@Override
	public List<SysRole> findAll() {
		return sysRoleDao.findAll();
	}

	@Override
	public List<SysRole> findAll(Sort sort) {
		return sysRoleDao.findAll(sort);
	}

	@Override
	public List<SysRole> findAllById(Iterable<String> ids) {
		return sysRoleDao.findAllById(ids);
	}

	@Override
	public List<SysRole> saveAll(Iterable<SysRole> entities) {
		return sysRoleDao.saveAll(entities);
	}

	@Override
	public void flush() {
		sysRoleDao.flush();
	}

	@Override
	public SysRole saveAndFlush(SysRole entity) {
		return sysRoleDao.saveAndFlush(entity);
	}

	@Override
	public void deleteInBatch(Iterable<SysRole> entities) {
		sysRoleDao.deleteInBatch(entities);
	}

	@Override
	public void deleteAllInBatch() {
		sysRoleDao.deleteAllInBatch();
	}

	@Override
	public SysRole getOne(String id) {
		return sysRoleDao.getOne(id);
	}

	@Override
	public SysRole save(SysRole entity) {
		return sysRoleDao.save(entity);
	}

	@Override
	public SysRole findById(String id) {
		return sysRoleDao.findById(id).get();
	}

	@Override
	public boolean existsById(String id) {
		return sysRoleDao.existsById(id);
	}

	@Override
	public long count() {
		return sysRoleDao.count();
	}

	@Override
	public void deleteById(String id) {
		sysRoleDao.deleteById(id);
	}

	@Override
	public void delete(SysRole entity) {
		sysRoleDao.delete(entity);
	}

	@Override
	public void deleteAll(Iterable<? extends SysRole> entities) {
		sysRoleDao.deleteAll(entities);
	}

	@Override
	public void deleteAll() {
		sysRoleDao.deleteAll();
	}

	@Override
	public SysRole findByRoleName(String roleName) {
		return sysRoleDao.findByRoleName(roleName);
	}

	@Override
	public List<SysRole> fuzzyFindByRoleName(String roleName) {
		return sysRoleDao.fuzzyFindByRoleName(roleName);
	}

}
