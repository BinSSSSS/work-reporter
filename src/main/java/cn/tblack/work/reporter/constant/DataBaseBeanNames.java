package cn.tblack.work.reporter.constant;

/**
 * @该类用于定义数据库源名字的常量
 * @author TD唐登
 * @Date:2019年11月17日
 * @Version: 1.0(测试版)
 */
public class DataBaseBeanNames {

	// 数据库**********************************************
	
	/*系统相关的数据库*/
	public static final String DB_SYSTEM = "wreporter_sys";
	
	/*定时任务的数据库*/
	public static final String DB_QUARTZ = "wreporter_quartz";
	
	/*报告相关的数据库*/
	public static final String DB_REPORT = "wreporter_report";
	
	/*任务存放的数据库*/
	public static final String DB_JOB_STORE = "job_store";
	
	/**存放着数据库表信息和数据库信息。 系统数据库*/
	public static final String DB_INFORMATION = "information_schema";
	
	// 数据源**********************************************
	/* wreporter_sys 数据源 */
	public static final String DS_SYSTEM = "sysDataSource";

	/* wreporter_quartz 数据源 */
	public static final String DS_QUARTZ = "quartzDataSource";

	/* wreporter_report 数据源 */
	public static final String DS_REPORT = "reportDataSource";

	/* job_store 数据源 */
	public static final String DS_JOB_STORE = "jobStoretDataSource";
	
	/* information 数据源 */
	public static final String DS_INFORMATION = "informationDataSource";
	// JDBC模板*********************************************
	/* 使用wreporter_sys生成的JDBC模板 */
	public static final String JT_SYSTEM = "sysJdbcTemplate";

	/* 使用wreporter_quartz生成的JDBC模板 */
	public static final String JT_QUARTZ = "quartzJdbcTemplate";

	/* 使用wreporter_report生成的JDBC模板 */
	public static final String JT_REPORT = "reportJdbcTemplate";

	// 数据库事务***********************************************
	/* 用于wreporter_sys的事务管理器 */
	public static final String TM_SYSTEM = "sysTransactionManager";

	/* 用于wreporter_quartz的事务管理器 */
	public static final String TM_QUARTZ = "quartzTransactionManager";

	/* 用于wreporter_report的事务管理器 */
	public static final String TM_REPORT = "reportTransactionManager";
	
	/* 用于job_store的事务管理器 */
	public static final String TM_JOB_STORE = "jobStoreTransactionManager";
	
	/* 用于information的事务管理器 */
	public static final String TM_INFORMATION = "informationTransactionManager";
	// 实体类管理工厂***********************************************
	/*用于wreporter_sys数据库内的实体表*/
	public static final String EMF_SYSTEM = "sysEntityManagerFactory";
	
	/*用于wreporter_quartz数据库内的实体表*/
	public static final String EMF_QUARTZ = "quartzEntityManagerFactory";
	
	/*用于wreporter_report数据库内的实体表*/
	public static final String EMF_REPORT = "reportEntityManagerFactory";
	
	/*用于job_store数据库内的实体表*/
	public static final String EMF_JOB_STORE = "jobStoreEntityManagerFactory";
	
	/*用于information数据库内的实体表*/
	public static final String EMF_INFORMATION = "informationEntityManagerFactory";
	// PersistenceUnit***********************************************
	
	public static final String PU_SYSTEM = "sysPersistenceUnit";
	
	public static final String PU_QUARTZ = "quartzPersistenceUnit";
	
	public static final String PU_REPORT = "reportPersistenceUnit";
	
	public static final String PU_JOB_STORE = "jobStorePersistenceUnit";

	public static final String PU_INFORMATION = "informationPersistenceUnit";
	// 数据源切换***********************************************
	/*使用ThreadLocal存放的数据，只有在指定线程才可以获取到数据。 Get 和  Set 得到和设置当前线程对应的值。*/
	public static ThreadLocal<String> JD_THREAD = new ThreadLocal<>();

	/**
	 * @设置当前线程切换的数据库
	 * @param jdbcTemplate
	 */
	public static void setJdbcTemplate(String jdbcTemplate) {
		JD_THREAD.set(jdbcTemplate);
	}

	/**
	 * @移除当前线程切换的数据库
	 */
	public static void removeJdbcTemplate() {
		JD_THREAD.remove();
	}

	/**
	 * @获取当前切换到的数据库
	 * @return
	 */
	public static String getJdbcTemplate() {
		return JD_THREAD.get();
	}
}
