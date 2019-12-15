package cn.tblack.work.reporter.sys.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import cn.tblack.work.reporter.sys.dao.OssConfigDao;
import cn.tblack.work.reporter.sys.entity.OssConfig;
import cn.tblack.work.reporter.sys.service.OssConfigService;

@Service
@Transactional
public class OssConfigServiceImpl implements OssConfigService {

	@Autowired
	private OssConfigDao ossConfigDao;
	
	@Override
	public List<OssConfig> findAll() {
		return ossConfigDao.findAll();
	}

	@Override
	public List<OssConfig> findAll(Sort sort) {
		return ossConfigDao.findAll(sort);
	}

	@Override
	public List<OssConfig> findAllById(Iterable<Integer> ids) {
		return ossConfigDao.findAllById(ids);
	}

	@Override
	public List<OssConfig> saveAll(Iterable<OssConfig> entities) {
		return ossConfigDao.saveAll(entities);
	}

	@Override
	public void flush() {
		ossConfigDao.flush();
	}

	@Override
	public OssConfig saveAndFlush(OssConfig entity) {
		return ossConfigDao.saveAndFlush(entity);
	}

	@Override
	public void deleteInBatch(Iterable<OssConfig> entities) {
		ossConfigDao.deleteInBatch(entities);
	}

	@Override
	public void deleteAllInBatch() {
		ossConfigDao.deleteAllInBatch();
	}

	@Override
	public OssConfig getOne(Integer id) {
		return ossConfigDao.getOne(id);
	}

	@Override
	public OssConfig save(OssConfig entity) {
		return ossConfigDao.save(entity);
	}

	@Override
	public OssConfig findById(Integer id) {
		return ossConfigDao.findById(id).get();
	}


	@Override
	public boolean existsById(Integer id) {
		return ossConfigDao.existsById(id);
	}

	@Override
	public long count() {
		return ossConfigDao.count();
	}

	@Override
	public void deleteById(Integer id) {
		ossConfigDao.deleteById(id);
	}

	@Override
	public void delete(OssConfig entity) {
		ossConfigDao.delete(entity);
	}

	@Override
	public void deleteAll(Iterable<? extends OssConfig> entities) {
		ossConfigDao.deleteAll(entities);
	}

	@Override
	public void deleteAll() {
		ossConfigDao.deleteAll();
	}

	@Override
	public Page<OssConfig> findAll(Pageable pageable) {
		return ossConfigDao.findAll(pageable);
	}

}
