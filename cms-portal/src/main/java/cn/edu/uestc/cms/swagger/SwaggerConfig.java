package cn.edu.uestc.cms.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.common.base.Predicate;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
	
	private static String apiTitle = "党建+大数据——RESTFful APIS";
	
	private static String version = "1.1";
	
	@Bean
	public Docket testApi() {
		 return new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(apiInfo())
                .useDefaultResponseMessages(false)
                .select()
                .paths(new Predicate<String>() {
					
					@Override
					public boolean apply(String input) {
						return input.contains("/api");
					}
				})
                .build();
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
                .title(apiTitle)
                .version(version)
                .license("详情请查看swagger官网api")
                .licenseUrl("https://github.com/OAI/OpenAPI-Specification/blob/master/versions/1.2.md#524-parameter-object")
                .build();
	}

}
