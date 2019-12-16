package cn.tblack.work.reporter.quartz.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.tblack.work.reporter.page.LaYuiPage;
import cn.tblack.work.reporter.quartz.dao.ScheduleJobDao;
import cn.tblack.work.reporter.quartz.entity.ScheduleJob;
import cn.tblack.work.reporter.quartz.service.ScheduleJobService;

@Service
@Transactional
public class ScheduleJobServiceImpl implements ScheduleJobService {

	@Autowired
	private ScheduleJobDao shceduleJobDao;
	
	@Override
	public List<ScheduleJob> findAll() {
		return shceduleJobDao.findAll();
	}

	@Override
	public List<ScheduleJob> findAll(Sort sort) {
		return shceduleJobDao.findAll(sort);
	}

	@Override
	public List<ScheduleJob> findAllById(Iterable<Integer> ids) {
		return shceduleJobDao.findAllById(ids);
	}

	@Override
	public List<ScheduleJob> saveAll(Iterable<ScheduleJob> entities) {
		return shceduleJobDao.saveAll(entities);
	}

	@Override
	public void flush() {
		shceduleJobDao.flush();
	}

	@Override
	public ScheduleJob saveAndFlush(ScheduleJob entity) {
		return shceduleJobDao.saveAndFlush(entity);
	}

	@Override
	public void deleteInBatch(Iterable<ScheduleJob> entities) {
		shceduleJobDao.deleteInBatch(entities);
	}

	@Override
	public void deleteAllInBatch() {
		shceduleJobDao.deleteAllInBatch();
	}

	@Override
	public ScheduleJob getOne(Integer id) {
		return shceduleJobDao.getOne(id);
	}

	@Override
	public ScheduleJob save(ScheduleJob entity) {
		return shceduleJobDao.save(entity);
	}

	@Override
	public ScheduleJob findById(Integer id) {
		return shceduleJobDao.findById(id).get();
	}

	@Override
	public boolean existsById(Integer id) {
		return shceduleJobDao.existsById(id);
	}

	@Override
	public long count() {
		return shceduleJobDao.count();
	}

	@Override
	public void deleteById(Integer id) {
		shceduleJobDao.deleteById(id);
	}

	@Override
	public void delete(ScheduleJob entity) {
		shceduleJobDao.delete(entity);
	}

	@Override
	public void deleteAll(Iterable<? extends ScheduleJob> entities) {
		shceduleJobDao.deleteAll(entities);
	}

	@Override
	public void deleteAll() {
		shceduleJobDao.deleteAll();
	}

	@Override
	public LaYuiPage<ScheduleJob> findAll(Pageable pageable) {
		return new LaYuiPage<ScheduleJob>(shceduleJobDao.findAll(pageable));
	}

	
}
