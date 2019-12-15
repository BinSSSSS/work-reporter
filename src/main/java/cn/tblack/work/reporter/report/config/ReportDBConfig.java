package cn.tblack.work.reporter.report.config;

import static cn.tblack.work.reporter.constant.DataBaseBeanNames.DS_REPORT;
import static cn.tblack.work.reporter.constant.DataBaseBeanNames.EMF_REPORT;
import static cn.tblack.work.reporter.constant.DataBaseBeanNames.PU_REPORT;
import static cn.tblack.work.reporter.constant.DataBaseBeanNames.TM_REPORT;

import java.util.Map;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateProperties;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateSettings;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
@Configuration
@EnableJpaRepositories(basePackages = {"cn.tblack.work.reporter.report.dao"},
		entityManagerFactoryRef = EMF_REPORT,
		transactionManagerRef =  TM_REPORT)
public class ReportDBConfig {

	
	private static Logger log =  LoggerFactory.getLogger(ReportDBConfig.class);
	
	@Autowired
	private JpaProperties jpaProperties;
	
	@Autowired
	private HibernateProperties properties;
	

	/**
	 * @ report数据库数据源
	 * @return
	 */
	@Bean(name = DS_REPORT)
	@ConfigurationProperties("spring.datasource.druid.report")
	public DataSource reportDatSource() {

		log.info("Create a reportDatSource");
		return DruidDataSourceBuilder.create().build();
	}
	
	@Bean(name =  EMF_REPORT)
	public LocalContainerEntityManagerFactoryBean reportEntityManagerFactory(EntityManagerFactoryBuilder builder,
			@Qualifier(DS_REPORT) DataSource dataSource) {
		
		Map<String, Object> map =
				properties.determineHibernateProperties(jpaProperties.getProperties(), new HibernateSettings());

		map.put("hibernate.transaction.jta.platform",
				"org.springframework.boot.orm.jpa.hibernate.SpringJtaPlatform");
		
		return builder.dataSource(dataSource)
				.properties(map)
				.packages("cn.tblack.work.reporter.report.entity")
				.persistenceUnit(PU_REPORT)
				.build();
	}
	
	
	/**
	 * @ report数据库事务管理器
	 * @param dataSource
	 * @return
	 */
	@Bean(name = TM_REPORT)
	public PlatformTransactionManager reportTransactionManager(EntityManagerFactoryBuilder builder,
			@Qualifier(DS_REPORT) DataSource dataSource) {
		
		log.info("Create reportTransactionManager");
//		return new DataSourceTransactionManager(dataSource);
		return new JpaTransactionManager(reportEntityManagerFactory(builder, dataSource).getObject());
	}

}
