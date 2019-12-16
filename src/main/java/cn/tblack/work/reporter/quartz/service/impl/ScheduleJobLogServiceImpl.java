package cn.tblack.work.reporter.quartz.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.tblack.work.reporter.page.LaYuiPage;
import cn.tblack.work.reporter.quartz.dao.ScheduleJobLogDao;
import cn.tblack.work.reporter.quartz.entity.ScheduleJobLog;
import cn.tblack.work.reporter.quartz.service.ScheduleJobLogService;
@Service
@Transactional
public class ScheduleJobLogServiceImpl implements ScheduleJobLogService {

	@Autowired
	private ScheduleJobLogDao scheduleJobLogDao;
	
	@Override
	public List<ScheduleJobLog> findAll() {
		return scheduleJobLogDao.findAll();
	}

	@Override
	public List<ScheduleJobLog> findAll(Sort sort) {
		return scheduleJobLogDao.findAll(sort);
	}

	@Override
	public List<ScheduleJobLog> findAllById(Iterable<Long> ids) {
		return scheduleJobLogDao.findAllById(ids);
	}

	@Override
	public List<ScheduleJobLog> saveAll(Iterable<ScheduleJobLog> entities) {
		return scheduleJobLogDao.saveAll(entities);
	}

	@Override
	public void flush() {
		scheduleJobLogDao.flush();
	}

	@Override
	public ScheduleJobLog saveAndFlush(ScheduleJobLog entity) {
		return scheduleJobLogDao.saveAndFlush(entity);
	}

	@Override
	public void deleteInBatch(Iterable<ScheduleJobLog> entities) {
		scheduleJobLogDao.deleteInBatch(entities);
	}

	@Override
	public void deleteAllInBatch() {
		scheduleJobLogDao.deleteAllInBatch();
	}

	@Override
	public ScheduleJobLog getOne(Long id) {
		return scheduleJobLogDao.getOne(id);
	}

	@Override
	public ScheduleJobLog save(ScheduleJobLog entity) {
		return scheduleJobLogDao.save(entity);
	}

	@Override
	public ScheduleJobLog findById(Long id) {
		return scheduleJobLogDao.findById(id).get();
	}

	@Override
	public boolean existsById(Long id) {
		return scheduleJobLogDao.existsById(id);
	}

	@Override
	public long count() {
		return scheduleJobLogDao.count();
	}

	@Override
	public void deleteById(Long id) {
		scheduleJobLogDao.deleteById(id);
	}

	@Override
	public void delete(ScheduleJobLog entity) {
		scheduleJobLogDao.delete(entity);
	}

	@Override
	public void deleteAll(Iterable<? extends ScheduleJobLog> entities) {
		scheduleJobLogDao.deleteAll(entities);
	}

	@Override
	public void deleteAll() {
		scheduleJobLogDao.deleteAll();
	}

	@Override
	public LaYuiPage<ScheduleJobLog> findAll(Pageable pageable) {
		return new LaYuiPage<ScheduleJobLog>(scheduleJobLogDao.findAll(pageable));
	}

}
