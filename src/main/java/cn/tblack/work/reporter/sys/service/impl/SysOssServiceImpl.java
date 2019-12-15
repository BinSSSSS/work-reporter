package cn.tblack.work.reporter.sys.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.tblack.work.reporter.sys.dao.SysOssDao;
import cn.tblack.work.reporter.sys.entity.SysOss;
import cn.tblack.work.reporter.sys.service.SysOssService;

@Service
@Transactional
public class SysOssServiceImpl implements SysOssService {

	@Autowired
	private SysOssDao sysOssDao;
	
	@Override
	public List<SysOss> findAll() {
		return sysOssDao.findAll();
	}

	@Override
	public List<SysOss> findAll(Sort sort) {
		return sysOssDao.findAll(sort);
	}

	@Override
	public List<SysOss> findAllById(Iterable<Long> ids) {
		return sysOssDao.findAllById(ids);
	}

	@Override
	public List<SysOss> saveAll(Iterable<SysOss> entities) {
		return sysOssDao.saveAll(entities);
	}

	@Override
	public void flush() {
		sysOssDao.flush();
	}

	@Override
	public SysOss saveAndFlush(SysOss entity) {
		return sysOssDao.saveAndFlush(entity);
	}

	@Override
	public void deleteInBatch(Iterable<SysOss> entities) {
		sysOssDao.deleteInBatch(entities);
	}

	@Override
	public void deleteAllInBatch() {
		sysOssDao.deleteAllInBatch();
	}

	@Override
	public SysOss getOne(Long id) {
		return sysOssDao.getOne(id);
	}

	@Override
	public SysOss save(SysOss entity) {
		return sysOssDao.save(entity);
	}

	@Override
	public SysOss findById(Long id) {
		return sysOssDao.findById(id).get();
	}

	@Override
	public boolean existsById(Long id) {
		return sysOssDao.existsById(id);
	}

	@Override
	public long count() {
		return sysOssDao.count();
	}

	@Override
	public void deleteById(Long id) {
		sysOssDao.deleteById(id);
	}

	@Override
	public void delete(SysOss entity) {
		sysOssDao.delete(entity);
	}

	@Override
	public void deleteAll(Iterable<? extends SysOss> entities) {
		sysOssDao.deleteAll(entities);
	}

	@Override
	public void deleteAll() {
		sysOssDao.deleteAll();
	}

	@Override
	public Page<SysOss> findAll(Pageable pageable) {
		return sysOssDao.findAll(pageable);
	}

}
