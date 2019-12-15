package cn.tblack.work.reporter.quartz.config;

import static cn.tblack.work.reporter.constant.DataBaseBeanNames.DS_QUARTZ;
import static cn.tblack.work.reporter.constant.DataBaseBeanNames.EMF_QUARTZ;
import static cn.tblack.work.reporter.constant.DataBaseBeanNames.PU_QUARTZ;
import static cn.tblack.work.reporter.constant.DataBaseBeanNames.TM_QUARTZ;

import java.util.Map;
import java.util.Properties;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateProperties;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateSettings;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.autoconfigure.quartz.QuartzProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;

@Configuration
@EnableJpaRepositories(basePackages = "cn.tblack.work.reporter.quartz.dao",
	transactionManagerRef = TM_QUARTZ,
	entityManagerFactoryRef = EMF_QUARTZ)
public class QuartzDBConfig {
	
	private static Logger log =  LoggerFactory.getLogger(QuartzDBConfig.class);
	
	@Autowired
	private JpaProperties jpaProperties;
	
	@Autowired
	private QuartzProperties quartzProperties;
	
	@Autowired
	private HibernateProperties properties;
	
	/**
	 * @quartz数据库数据源
	 * @return
	 */
	@Bean(name = DS_QUARTZ)
	@ConfigurationProperties("spring.datasource.druid.quartz")
	public DataSource quartzDataSource() {

		log.info("Create a quartzDataSource");
		return DruidDataSourceBuilder.create().build();
	}
	
	@Bean(name = EMF_QUARTZ)
	public LocalContainerEntityManagerFactoryBean quartzEntityManagerFactory(EntityManagerFactoryBuilder builder,
			@Qualifier(DS_QUARTZ) DataSource dataSource) {
		
		Map<String, Object> map =
				properties.determineHibernateProperties(jpaProperties.getProperties(), new HibernateSettings());
		
		map.put("hibernate.transaction.jta.platform",
				"org.springframework.boot.orm.jpa.hibernate.SpringJtaPlatform");
		
		return builder.dataSource(dataSource)
				.properties(map)
				.packages("cn.tblack.work.reporter.quartz.entity")
				.persistenceUnit(PU_QUARTZ)
				.build();
	}
	@Primary
	@Bean
	/**
	 * @ 用于在数据库中存放 quartz job信息的工厂Bean
	 * @param dataSource
	 * @return
	 */
	public SchedulerFactoryBean quartzSchedulerFactoryBean(@Qualifier(DS_QUARTZ) DataSource dataSource) {
		
		SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();

		Properties properties = new Properties();
		properties.putAll(quartzProperties.getProperties());

		schedulerFactoryBean.setDataSource(dataSource);

		schedulerFactoryBean.setQuartzProperties(properties);

		// 延时启动
		schedulerFactoryBean.setStartupDelay(1);
		// 启动时更新己存在的Job，这样就不用每次修改targetObject后删除qrtz_job_details表对应记录了
		schedulerFactoryBean.setOverwriteExistingJobs(true);

		return schedulerFactoryBean;
	}
	

	/**
	 * @ quartz数据库事务管理器
	 * @param dataSource
	 * @return
	 */
	@Bean(name = TM_QUARTZ)
	public PlatformTransactionManager 
	quartzTransactionManager(EntityManagerFactoryBuilder builder,
			@Qualifier(DS_QUARTZ) DataSource dataSource) {
		
		log.info("Create quartzTransactionManager");
	
		return new JpaTransactionManager(quartzEntityManagerFactory(builder,dataSource).getObject());
	}
}
