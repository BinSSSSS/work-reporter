package cn.tblack.work.reporter.sys.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.tblack.work.reporter.sys.dao.SysGenDao;
import cn.tblack.work.reporter.sys.entity.SysGen;
import cn.tblack.work.reporter.sys.service.SysGenService;
@Service
@Transactional
public class SysGenServiceImpl implements SysGenService {

	@Autowired
	private SysGenDao sysGenDao;
	
	@Override
	public List<SysGen> findAll() {
		return sysGenDao.findAll();
	}

	@Override
	public List<SysGen> findAll(Sort sort) {
		return sysGenDao.findAll(sort);
	}

	@Override
	public List<SysGen> findAllById(Iterable<String> ids) {
		return sysGenDao.findAllById(ids);
	}

	@Override
	public List<SysGen> saveAll(Iterable<SysGen> entities) {
		return sysGenDao.saveAll(entities);
	}

	@Override
	public void flush() {
		sysGenDao.flush();
	}

	@Override
	public SysGen saveAndFlush(SysGen entity) {
		return sysGenDao.saveAndFlush(entity);
	}

	@Override
	public void deleteInBatch(Iterable<SysGen> entities) {
		sysGenDao.deleteInBatch(entities);
	}

	@Override
	public void deleteAllInBatch() {
		sysGenDao.deleteAllInBatch();
	}

	@Override
	public SysGen getOne(String id) {
		return sysGenDao.getOne(id);
	}

	@Override
	public SysGen save(SysGen entity) {
		return sysGenDao.save(entity);
	}

	@Override
	public SysGen findById(String id) {
		return sysGenDao.findById(id).get();
	}


	@Override
	public boolean existsById(String id) {
		return sysGenDao.existsById(id);
	}

	@Override
	public long count() {
		return sysGenDao.count();
	}

	@Override
	public void deleteById(String id) {
		sysGenDao.deleteById(id);
	}

	@Override
	public void delete(SysGen entity) {
		sysGenDao.delete(entity);
	}

	@Override
	public void deleteAll(Iterable<? extends SysGen> entities) {
		sysGenDao.deleteAll(entities);
	}

	@Override
	public void deleteAll() {
		sysGenDao.deleteAll();
	}

	@Override
	public Page<SysGen> findAll(Pageable pageable) {
		return sysGenDao.findAll(pageable);
	}

	@Override
	public void updateKeyValue(String key, String value) {
		sysGenDao.updateKeyValue(key,value);
	}

}
