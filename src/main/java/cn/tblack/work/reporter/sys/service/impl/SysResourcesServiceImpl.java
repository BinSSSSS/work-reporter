package cn.tblack.work.reporter.sys.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.tblack.work.reporter.sys.dao.SysResourcesDao;
import cn.tblack.work.reporter.sys.entity.SysResources;
import cn.tblack.work.reporter.sys.service.SysResourcesService;

@Service
@Transactional
public class SysResourcesServiceImpl implements SysResourcesService {

	@Autowired
	private SysResourcesDao sysResourcesDao;
	
	@Override
	public List<SysResources> findAll() {
		return sysResourcesDao.findAll();
	}

	@Override
	public List<SysResources> findAll(Sort sort) {
		return sysResourcesDao.findAll(sort);
	}

	@Override
	public List<SysResources> findAllById(Iterable<String> ids) {
		return sysResourcesDao.findAllById(ids);
	}

	@Override
	public List<SysResources> saveAll(Iterable<SysResources> entities) {
		return sysResourcesDao.saveAll(entities);
	}

	@Override
	public void flush() {
		sysResourcesDao.flush();
	}

	@Override
	public SysResources saveAndFlush(SysResources entity) {
		return sysResourcesDao.saveAndFlush(entity);
	}

	@Override
	public void deleteInBatch(Iterable<SysResources> entities) {
		sysResourcesDao.deleteInBatch(entities);
	}

	@Override
	public void deleteAllInBatch() {
		sysResourcesDao.deleteAllInBatch();
	}

	@Override
	public SysResources getOne(String id) {
		return sysResourcesDao.getOne(id);
	}

	@Override
	public SysResources save(SysResources entity) {
		return sysResourcesDao.save(entity);
	}

	@Override
	public SysResources findById(String id) {
		return sysResourcesDao.findById(id).get();
	}



	@Override
	public boolean existsById(String id) {
		return sysResourcesDao.existsById(id);
	}

	@Override
	public long count() {
		return sysResourcesDao.count();
	}

	@Override
	public void deleteById(String id) {
		sysResourcesDao.deleteById(id);
	}

	@Override
	public void delete(SysResources entity) {
		sysResourcesDao.delete(entity);
	}

	@Override
	public void deleteAll(Iterable<? extends SysResources> entities) {
		sysResourcesDao.deleteAll(entities);
	}

	@Override
	public void deleteAll() {
		sysResourcesDao.deleteAll();
	}

	@Override
	public List<SysResources> findByRoleId(String roleId) {
		return sysResourcesDao.findByRoleId(roleId);
	}

	@Override
	public List<SysResources> findAllResourcesHasRole() {
		return sysResourcesDao.findAllResourcesHasRole();
	}

	@Override
	public List<String> findResIdListByRoleId(String roleId) {
		return sysResourcesDao.findResIdListByRoleId(roleId);
	}

	@Override
	public List<SysResources> fuzzyFindByResUrl(String resUrl) {
		return sysResourcesDao.fuzzyFindByResUrl(resUrl);
	}
}
