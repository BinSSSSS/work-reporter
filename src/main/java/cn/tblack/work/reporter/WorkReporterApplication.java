package cn.tblack.work.reporter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableTransactionManagement
public class WorkReporterApplication {

	
	@Bean
	public RestTemplate restTemplate() {
		
		return new RestTemplate();
	}
	
	public static void main(String[] args) {
		SpringApplication.run(WorkReporterApplication.class, args);
	}

}
