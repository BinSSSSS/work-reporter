package cn.tblack.work.reporter.sys.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static cn.tblack.work.reporter.gen.util.EntityInjectionUtil.*;
import cn.tblack.work.reporter.sys.dao.SysTableDao;
import cn.tblack.work.reporter.sys.entity.SysTable;
import cn.tblack.work.reporter.sys.service.SysTableService;

@Service
@Transactional
public class SysTableServiceImpl implements SysTableService {

	@Autowired
	private SysTableDao tableDao;

	@Override
	public List<SysTable> findAll() {
		return tableDao.findAll();
	}

	@Override
	public List<SysTable> findAll(Sort sort) {
		return tableDao.findAll(sort);
	}

	@Override
	public List<SysTable> findAllById(Iterable<Integer> ids) {
		return tableDao.findAllById(ids);
	}

	@Override
	public List<SysTable> saveAll(Iterable<SysTable> entities) {
		return tableDao.saveAll(entities);
	}

	@Override
	public void flush() {
		tableDao.flush();
	}

	@Override
	public SysTable saveAndFlush(SysTable entity) {
		return tableDao.saveAndFlush(entity);
	}

	@Override
	public void deleteInBatch(Iterable<SysTable> entities) {
		tableDao.deleteInBatch(entities);
	}

	@Override
	public void deleteAllInBatch() {
		tableDao.deleteAllInBatch();
	}

	@Override
	public SysTable getOne(Integer id) {
		return tableDao.getOne(id);
	}

	@Override
	public SysTable save(SysTable entity) {
		return tableDao.save(entity);
	}

	@Override
	public SysTable findById(Integer id) {
		return tableDao.findById(id).get();
	}

	@Override
	public boolean existsById(Integer id) {
		return tableDao.existsById(id);
	}

	@Override
	public long count() {
		return tableDao.count();
	}

	@Override
	public void deleteById(Integer id) {
		tableDao.deleteById(id);
	}

	@Override
	public void delete(SysTable entity) {
		tableDao.delete(entity);
	}

	@Override
	public void deleteAll(Iterable<? extends SysTable> entities) {
		tableDao.deleteAll(entities);
	}

	@Override
	public void deleteAll() {
		tableDao.deleteAll();
	}

	@Override
	public Page<SysTable> findAll(Pageable pageable) {
		return tableDao.findAll(pageable);
	}

	@Override
	public List<SysTable> findAllBySchema(String schema) {

		return beanListInjection(new SysTable(), tableDao.findAllBySchema(schema));
	}

	@Override
	public List<SysTable> findAllBySchemaAndTableName(String schemaName, String tableName) {
		return beanListInjection(new SysTable(), 
				tableDao.fuzzyFindAllBySchemaAndTableName(schemaName, tableName));
	}

	@Override
	public List<SysTable> findAllBySchemaAndTableName(String schemaName, String tableName, Integer pageNo,
			Integer pageSize) {
		return beanListInjection(new SysTable(),
				tableDao.fuzzyFindAllBySchemaAndTableName(schemaName, tableName, pageNo * pageSize, pageSize));
	}

	@Override
	public SysTable findBySchemaAndTableName(String schemaName, String tableName) {
		SysTable table = new SysTable();
		if (beanInjection(new SysTable(), 
				tableDao.findBySchemaAndTableName(schemaName, tableName)))
			return table;
		return null;
	}

	@Override
	public SysTable findBySchemaAndTableName(String schemaName, String tableName, Integer pageNo,
			Integer pageSize) {
		
		SysTable table = new SysTable();
		if (beanInjection(new SysTable(),
				tableDao.findBySchemaAndTableName(schemaName, tableName, pageNo, pageSize)))
			return table;
		
		return null;
	}

}
