package cn.tblack.work.reporter.sys.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.tblack.work.reporter.sys.dao.SysGenTempDao;
import cn.tblack.work.reporter.sys.entity.SysGenTemp;
import cn.tblack.work.reporter.sys.service.SysGenTempService;

@Service
@Transactional
public class SysGenTempServiceImpl implements SysGenTempService {

	@Autowired
	private SysGenTempDao sysGenTempDao;
	
	@Override
	public List<SysGenTemp> findAll() {
		return sysGenTempDao.findAll();
	}

	@Override
	public List<SysGenTemp> findAll(Sort sort) {
		return sysGenTempDao.findAll(sort);
	}

	@Override
	public List<SysGenTemp> findAllById(Iterable<String> ids) {
		return sysGenTempDao.findAllById(ids);
	}

	@Override
	public List<SysGenTemp> saveAll(Iterable<SysGenTemp> entities) {
		return sysGenTempDao.saveAll(entities);
	}

	@Override
	public void flush() {
		sysGenTempDao.flush();
	}

	@Override
	public SysGenTemp saveAndFlush(SysGenTemp entity) {
		return sysGenTempDao.saveAndFlush(entity);
	}

	@Override
	public void deleteInBatch(Iterable<SysGenTemp> entities) {
		sysGenTempDao.deleteInBatch(entities);
	}

	@Override
	public void deleteAllInBatch() {
		sysGenTempDao.deleteAllInBatch();
	}

	@Override
	public SysGenTemp getOne(String id) {
		return sysGenTempDao.getOne(id);
	}

	@Override
	public SysGenTemp save(SysGenTemp entity) {
		return sysGenTempDao.save(entity);
	}

	@Override
	public SysGenTemp findById(String id) {
		return sysGenTempDao.findById(id).get();
	}



	@Override
	public boolean existsById(String id) {
		return sysGenTempDao.existsById(id);
	}

	@Override
	public long count() {
		return sysGenTempDao.count();
	}

	@Override
	public void deleteById(String id) {
		sysGenTempDao.deleteById(id);
	}

	@Override
	public void delete(SysGenTemp entity) {
		sysGenTempDao.delete(entity);
	}

	@Override
	public void deleteAll(Iterable<? extends SysGenTemp> entities) {
		sysGenTempDao.deleteAll(entities);
	}

	@Override
	public void deleteAll() {
		sysGenTempDao.deleteAll();
	}

	@Override
	public List<SysGenTemp> selectAllByGenFilename(String filename, boolean isBlur) {
		
		if(!isBlur)
			return sysGenTempDao.findAllByGenFilename(filename);
		
		return sysGenTempDao.blurredFindAllByFilename(filename);
	}

	@Override
	public Page<SysGenTemp> findAll(Pageable pageable) {
		return sysGenTempDao.findAll(pageable);
	}

	@Override
	public Page<SysGenTemp> findAllByStyleId(String styleId, Pageable pageable) {
		return sysGenTempDao.findAllByStyleId(styleId,pageable);
	}

	@Override
	public List<SysGenTemp> findAllByStyleId(String styleId) {
		return sysGenTempDao.findAllByStyleId(styleId);
	}

}
