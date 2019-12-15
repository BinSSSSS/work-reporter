package cn.tblack.work.reporter.quartz.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import cn.tblack.work.reporter.quartz.entity.ScheduleJobLog;

public interface ScheduleJobLogDao extends JpaRepository<ScheduleJobLog, Long>{

}
