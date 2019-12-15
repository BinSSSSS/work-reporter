package cn.tblack.work.reporter.info.config;

import static cn.tblack.work.reporter.constant.DataBaseBeanNames.DS_INFORMATION;
import static cn.tblack.work.reporter.constant.DataBaseBeanNames.EMF_INFORMATION;
import static cn.tblack.work.reporter.constant.DataBaseBeanNames.PU_INFORMATION;
import static cn.tblack.work.reporter.constant.DataBaseBeanNames.TM_INFORMATION;

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
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;

import cn.tblack.work.reporter.info.entity.Tables;

/**
 * @!_!用于系统数据库 information_schema的数据库配置。不推荐使用。因为系统数据库不存在主键，所以无法直接构建JPARepository
 * @author TD唐登
 * @Date:2019年11月29日
 * @Version: 1.0(测试版)
 */
//@Configuration
//@EnableJpaRepositories(basePackageClasses = TablesDao.class,
//		entityManagerFactoryRef = EMF_INFORMATION,
//		transactionManagerRef = TM_INFORMATION)
@Deprecated
public class InformationDbConfig {

	private static Logger log =  LoggerFactory.getLogger(InformationDbConfig.class);
	
	@Autowired
	private JpaProperties jpaProperties;
	
	@Autowired
	private HibernateProperties properties;
	
	
	/**
	 * @ information数据库数据源
	 * @return
	 */
	@Bean(name = DS_INFORMATION)
	@ConfigurationProperties("spring.datasource.druid.information")
	public DataSource informationDataSource() {
		
		log.info("Create informationDataSource");
		return DruidDataSourceBuilder.create().build();
	}
	
	
	/**
	 * @ 实体类管理器
	 * @param builder
	 * @param dataSource
	 * @return
	 */
	@Bean(name = EMF_INFORMATION )
	public LocalContainerEntityManagerFactoryBean informationEntityManagerFactory(EntityManagerFactoryBuilder builder,
			@Qualifier(DS_INFORMATION) DataSource dataSource) {
		
		Map<String, Object> map =
				properties.determineHibernateProperties(jpaProperties.getProperties(), new HibernateSettings());

 		map.put("hibernate.transaction.jta.platform",
				"org.springframework.boot.orm.jpa.hibernate.SpringJtaPlatform");
		
 		log.info("Create SysEntityManagerFactory");
 		
		return builder.dataSource(dataSource)
				.properties(map)
				.packages(Tables.class)
//				.packages("cn.tblack.work.reporter.information.entity")
				.persistenceUnit(PU_INFORMATION)
				.build();
	}
	
	
	/**
	 * @ information数据库事务管理器
	 * @param dataSource
	 * @return
	 */
	@Bean(name = TM_INFORMATION)
	public PlatformTransactionManager informationTransactionManager(@Qualifier(DS_INFORMATION) DataSource dataSource,
			EntityManagerFactoryBuilder builder) {
		
		log.info("Create informationTransactionManager");
		
		return new JpaTransactionManager(informationEntityManagerFactory(builder, dataSource).getObject());
	}
	
}
