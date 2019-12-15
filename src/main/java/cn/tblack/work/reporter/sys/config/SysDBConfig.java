package cn.tblack.work.reporter.sys.config;


import static cn.tblack.work.reporter.constant.DataBaseBeanNames.DS_SYSTEM;
import static cn.tblack.work.reporter.constant.DataBaseBeanNames.EMF_SYSTEM;
import static cn.tblack.work.reporter.constant.DataBaseBeanNames.PU_SYSTEM;
import static cn.tblack.work.reporter.constant.DataBaseBeanNames.TM_SYSTEM;

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
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;

import cn.tblack.work.reporter.sys.dao.SysUserDao;
import cn.tblack.work.reporter.sys.entity.SysUser;

@Configuration
@EnableJpaRepositories(basePackageClasses = SysUserDao.class,
		//basePackages = {"cn.tblack.work.reporter.sys.dao"},
		entityManagerFactoryRef = EMF_SYSTEM,
		transactionManagerRef = TM_SYSTEM)	
public class SysDBConfig {
	
	private static Logger log =  LoggerFactory.getLogger(SysDBConfig.class);
	
	@Autowired
	private JpaProperties jpaProperties;


	@Autowired
	private HibernateProperties properties;

	/**
	 * @ sys数据库数据源
	 * @return
	 */
	@Primary
	@Bean(name = DS_SYSTEM)
	@ConfigurationProperties("spring.datasource.druid.sys")
	public DataSource sysDataSource() {
		
		log.info("Create sysDataSource");
		return DruidDataSourceBuilder.create().build();
	}
	
	
	/**
	 * @ 实体类管理器
	 * @param builder
	 * @param dataSource
	 * @return
	 */
	@Primary
	@Bean(name = EMF_SYSTEM )
	public LocalContainerEntityManagerFactoryBean sysEntityManagerFactory(EntityManagerFactoryBuilder builder,
			@Qualifier(DS_SYSTEM) DataSource dataSource) {
		
		Map<String, Object> map =
				properties.determineHibernateProperties(jpaProperties.getProperties(), new HibernateSettings());

 		map.put("hibernate.transaction.jta.platform",
				"org.springframework.boot.orm.jpa.hibernate.SpringJtaPlatform");
		
 		log.info("Create SysEntityManagerFactory");
 		
		return builder.dataSource(dataSource)
				.properties(map)
				.packages(SysUser.class)
//				.packages("cn.tblack.work.reporter.sys.entity")
				.persistenceUnit(PU_SYSTEM)
				.build();
	}
	
	
	/**
	 * @ sys数据库事务管理器
	 * @param dataSource
	 * @return
	 */
	@Primary
	@Bean(name = TM_SYSTEM)
	public PlatformTransactionManager sysTransactionManager(@Qualifier(DS_SYSTEM) DataSource dataSource,
			EntityManagerFactoryBuilder builder) {
		
		log.info("Create sysTransactionManager");
//		return new DataSourceTransactionManager(dataSource);
		
		return new JpaTransactionManager(sysEntityManagerFactory(builder, dataSource).getObject());
	}
}
