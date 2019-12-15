package cn.tblack.work.reporter.sys.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.tblack.work.reporter.sys.dao.SysGenStyleDao;
import cn.tblack.work.reporter.sys.entity.SysGenStyle;
import cn.tblack.work.reporter.sys.service.SysGenStyleService;

@Service
@Transactional
public class SysGenStyleServiceImpl implements SysGenStyleService {

	@Autowired
	private SysGenStyleDao sysGenStyleDao;

	@Override
	public List<SysGenStyle> findAll() {
		return sysGenStyleDao.findAll();
	}

	@Override
	public List<SysGenStyle> findAll(Sort sort) {
		return sysGenStyleDao.findAll(sort);
	}

	@Override
	public List<SysGenStyle> findAllById(Iterable<String> ids) {
		return sysGenStyleDao.findAllById(ids);
	}

	@Override
	public List<SysGenStyle> saveAll(Iterable<SysGenStyle> entities) {
		return sysGenStyleDao.saveAll(entities);
	}

	@Override
	public void flush() {
		sysGenStyleDao.flush();
	}

	@Override
	public SysGenStyle saveAndFlush(SysGenStyle entity) {
		return sysGenStyleDao.saveAndFlush(entity);
	}

	@Override
	public void deleteInBatch(Iterable<SysGenStyle> entities) {
		sysGenStyleDao.deleteInBatch(entities);
	}

	@Override
	public void deleteAllInBatch() {
		sysGenStyleDao.deleteAllInBatch();
	}

	@Override
	public SysGenStyle getOne(String id) {
		return sysGenStyleDao.getOne(id);
	}

	@Override
	public SysGenStyle save(SysGenStyle entity) {
		return sysGenStyleDao.save(entity);
	}

	@Override
	public SysGenStyle findById(String id) {
		return sysGenStyleDao.findById(id).get();
	}

	@Override
	public boolean existsById(String id) {
		return sysGenStyleDao.existsById(id);
	}

	@Override
	public long count() {
		return sysGenStyleDao.count();
	}

	@Override
	public void deleteById(String id) {
		sysGenStyleDao.deleteById(id);
	}

	@Override
	public void delete(SysGenStyle entity) {
		sysGenStyleDao.delete(entity);
	}

	@Override
	public void deleteAll(Iterable<? extends SysGenStyle> entities) {
		sysGenStyleDao.deleteAll(entities);
	}

	@Override
	public void deleteAll() {
		sysGenStyleDao.deleteAll();
	}

	@Override
	public List<SysGenStyle> selectAllByName(String name, boolean isBlur) {

		if (!isBlur) {
			return sysGenStyleDao.findAllByName(name);
		}

		return sysGenStyleDao.blurredFindAllByName(name);
	}

	@Override
	public Page<SysGenStyle> findAll(Pageable pageable) {
		return sysGenStyleDao.findAll(pageable);
	}

}
