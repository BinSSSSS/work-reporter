package cn.tblack.work.reporter.sys.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.tblack.work.reporter.page.LaYuiPage;
import cn.tblack.work.reporter.sys.dao.SysGenDbDao;
import cn.tblack.work.reporter.sys.entity.SysGenDb;
import cn.tblack.work.reporter.sys.service.SysGenDbService;

@Service
@Transactional
public class SysGenDbServiceImpl implements SysGenDbService {

	@Autowired
	private SysGenDbDao sysGenDbDao;
	
	@Override
	public List<SysGenDb> findAll() {
		return sysGenDbDao.findAll();
	}

	@Override
	public List<SysGenDb> findAll(Sort sort) {
		return sysGenDbDao.findAll(sort);
	}

	@Override
	public List<SysGenDb> findAllById(Iterable<String> ids) {
		return sysGenDbDao.findAllById(ids);
	}

	@Override
	public List<SysGenDb> saveAll(Iterable<SysGenDb> entities) {
		return sysGenDbDao.saveAll(entities);
	}

	@Override
	public void flush() {
		sysGenDbDao.flush();
	}

	@Override
	public SysGenDb saveAndFlush(SysGenDb entity) {
		return sysGenDbDao.saveAndFlush(entity);
	}

	@Override
	public void deleteInBatch(Iterable<SysGenDb> entities) {
		sysGenDbDao.deleteInBatch(entities);
	}

	@Override
	public void deleteAllInBatch() {
		sysGenDbDao.deleteAllInBatch();
	}

	@Override
	public SysGenDb getOne(String id) {
		return sysGenDbDao.getOne(id);
	}

	@Override
	public SysGenDb save(SysGenDb entity) {
		return sysGenDbDao.save(entity);
	}

	@Override
	public SysGenDb findById(String id) {
		return sysGenDbDao.findById(id).get();
	}


	@Override
	public boolean existsById(String id) {
		return sysGenDbDao.existsById(id);
	}

	@Override
	public long count() {
		return sysGenDbDao.count();
	}

	@Override
	public void deleteById(String id) {
		sysGenDbDao.deleteById(id);
	}

	@Override
	public void delete(SysGenDb entity) {
		sysGenDbDao.delete(entity);
	}

	@Override
	public void deleteAll(Iterable<? extends SysGenDb> entities) {
		sysGenDbDao.deleteAll(entities);
	}

	@Override
	public void deleteAll() {
		sysGenDbDao.deleteAll();
	}

	@Override
	public List<SysGenDb> selectByDbName(String dbName, boolean isBlur) {
		
		if(isBlur) {
			return sysGenDbDao.blurredFindAllByDbName( dbName);
		}
		
		return sysGenDbDao.findAllByDbName(dbName);
	}

	@Override
	public LaYuiPage<SysGenDb> findAll(Pageable pageable) {
		return new LaYuiPage<SysGenDb>(sysGenDbDao.findAll(pageable));
	}

}
