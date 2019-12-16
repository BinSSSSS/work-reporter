package cn.tblack.work.reporter.sys.service.impl;

import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import cn.tblack.work.reporter.sys.dao.SysResRoleDao;
import cn.tblack.work.reporter.sys.entity.SysResRole;
import cn.tblack.work.reporter.sys.entity.SysRole;
import cn.tblack.work.reporter.sys.service.SysResRoleService;

@Service
@Transactional
public class SysResRoleServiceImpl implements SysResRoleService {

	@Autowired
	private SysResRoleDao resRoleDao;
	
	@Override
	public List<SysResRole> findAll() {
		return resRoleDao.findAll();
	}

	@Override
	public List<SysResRole> findAll(Sort sort) {
		return resRoleDao.findAll(sort);
	}

	@Override
	public List<SysResRole> findAllById(Iterable<Integer> ids) {
		return resRoleDao.findAllById(ids);
	}

	@Override
	public List<SysResRole> saveAll(Iterable<SysResRole> entities) {
		return resRoleDao.saveAll(entities);
	}

	@Override
	public void flush() {
		resRoleDao.flush();
	}

	@Override
	public SysResRole saveAndFlush(SysResRole entity) {
		return resRoleDao.saveAndFlush(entity);
	}

	@Override
	public void deleteInBatch(Iterable<SysResRole> entities) {
		resRoleDao.deleteInBatch(entities);
	}

	@Override
	public void deleteAllInBatch() {
		resRoleDao.deleteAllInBatch();
	}

	@Override
	public SysResRole getOne(Integer id) {
		return resRoleDao.getOne(id);
	}

	@Override
	public SysResRole save(SysResRole entity) {
		return resRoleDao.save(entity);
	}

	@Override
	public SysResRole findById(Integer id) {
		return resRoleDao.findById(id).get();
	}

	@Override
	public boolean existsById(Integer id) {
		return resRoleDao.existsById(id);
	}

	@Override
	public long count() {
		return resRoleDao.count();
	}

	@Override
	public void deleteById(Integer id) {
		resRoleDao.deleteById(id);
	}

	@Override
	public void delete(SysResRole entity) {
		resRoleDao.delete(entity);
	}

	@Override
	public void deleteAll(Iterable<? extends SysResRole> entities) {
		resRoleDao.deleteAll(entities);
	}

	@Override
	public void deleteAll() {
		resRoleDao.deleteAll();
	}

	@Override
	public void revokeResRole(String resId, String roleId) {
		resRoleDao.revokeResRole(resId, roleId);
	}
	
	
	@Override
	public Set<SysRole> findResRoleByResId(String resId) {
		return resRoleDao.findResRoleByResId(resId);
	}
}
