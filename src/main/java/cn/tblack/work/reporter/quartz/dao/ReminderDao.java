package cn.tblack.work.reporter.quartz.dao;


import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import cn.tblack.work.reporter.quartz.entity.Reminder;

@Transactional
public interface ReminderDao extends JpaRepository<Reminder, Integer> {

	/**
	 * @-=-=该方法通过传递一个reminderId来进行更新提醒的最后完成时间和已经完成的次数
	 * @param reminderId
	 * @param finishedTime
	 * @return
	 */
	@Modifying(clearAutomatically = true)
	@Query("UPDATE #{#entityName} r " + "SET r.finishedTime = :finishedTime,"
			+ " r.finishedCount = r.finishedCount+1 WHERE r.id = :reminderId")
	void updateFinishedStateById(@Param("reminderId") Integer reminderId, @Param("finishedTime") Date finishedTime);
	
	@Modifying(clearAutomatically = true)
	@Query("UPDATE #{#entityName} r " + "SET r.deprecated = :deprecated WHERE r.id = :id")
	void updateDeprecated(@Param("id")Integer id,@Param("deprecated") Short deprecated);

	@Query("SELECT r FROM #{#entityName} r WHERE r.userId = :userId")
	Page<Reminder> findRemindersByUserId(@Param("userId") String userId, Pageable pageable);
	
}
