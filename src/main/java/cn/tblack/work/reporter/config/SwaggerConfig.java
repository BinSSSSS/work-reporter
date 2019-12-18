package cn.tblack.work.reporter.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static cn.tblack.work.reporter.properties.WebSystemProperties.*;
/**
 * @~——~Swagger接口文档配置
 * @author TD唐登
 * @Date:2019年12月17日
 * @Version: 1.0(测试版)
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {
		
	@Bean
	public Docket createRestApi() {
		
		return new Docket(DocumentationType.SWAGGER_2).
				apiInfo(apiInfo()).select().
				apis(RequestHandlerSelectors.basePackage("cn.tblack.work.reporter")).
				paths(PathSelectors.any()).build();
	}
	
	private ApiInfo apiInfo() {
		Contact contact = new Contact(WEB_TITLE, DAMAIN, DOMAIN_EMAIL);
		return new ApiInfoBuilder().title("云工作报告").
					termsOfServiceUrl(DAMAIN).contact(contact).version("1.0").build();
	}
}
