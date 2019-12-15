package cn.tblack.work.reporter.sys.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.tblack.work.reporter.sys.dao.SysConfigDao;
import cn.tblack.work.reporter.sys.entity.SysConfig;
import cn.tblack.work.reporter.sys.service.SysConfigService;

@Service
@Transactional
public class SysConfigServiceImpl implements SysConfigService {

	@Autowired
	private SysConfigDao sysConfigDao;
	
	@Override
	public List<SysConfig> findAll() {
		return sysConfigDao.findAll();
	}

	@Override
	public List<SysConfig> findAll(Sort sort) {
		return sysConfigDao.findAll(sort);
	}

	@Override
	public List<SysConfig> findAllById(Iterable<Long> ids) {
		return sysConfigDao.findAllById(ids);
	}

	@Override
	public List<SysConfig> saveAll(Iterable<SysConfig> entities) {
		return sysConfigDao.saveAll(entities);
	}

	@Override
	public void flush() {
		sysConfigDao.flush();
	}

	@Override
	public SysConfig saveAndFlush(SysConfig entity) {
		return sysConfigDao.saveAndFlush(entity);
	}

	@Override
	public void deleteInBatch(Iterable<SysConfig> entities) {
		sysConfigDao.deleteInBatch(entities);
	}

	@Override
	public void deleteAllInBatch() {
		sysConfigDao.deleteAllInBatch();
	}

	@Override
	public SysConfig getOne(Long id) {
		return sysConfigDao.getOne(id);
	}

	@Override
	public SysConfig save(SysConfig entity) {
		return sysConfigDao.save(entity);
	}

	@Override
	public SysConfig findById(Long id) {
		return sysConfigDao.findById(id).get();
	}



	@Override
	public boolean existsById(Long id) {
		return sysConfigDao.existsById(id);
	}

	@Override
	public long count() {
		return sysConfigDao.count();
	}

	@Override
	public void deleteById(Long id) {
		
		sysConfigDao.deleteById(id);
	}

	@Override
	public void delete(SysConfig entity) {
		sysConfigDao.delete(entity);
	}

	@Override
	public void deleteAll(Iterable<? extends SysConfig> entities) {
		sysConfigDao.deleteAll(entities);
	}

	@Override
	public void deleteAll() {
		sysConfigDao.deleteAll();
	}

}
