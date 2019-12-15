package cn.tblack.work.reporter.schedule.util;

import cn.tblack.work.reporter.schedule.ReminderScheduler;

public class SchedulerUtils {

	private static ReminderScheduler reminderScheduler;

	public static ReminderScheduler getReminderScheduler() {

		return reminderScheduler;
	}

	public static void setReminderScheduler(ReminderScheduler scheduler) {
		reminderScheduler = scheduler;
	}
}
