package br.ufrn.ePET;

/*import com.google.common.base.Predicate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static springfox.documentation.builders.PathSelectors.regex;
import static com.google.common.base.Predicates.or;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
	
	@Bean
	public Docket postsApi() {
		return new Docket(DocumentationType.SWAGGER_2).groupName("public-api")
				.apiInfo(apiInfo()).select().paths(postPaths()).build();
	}
	
	private Predicate<String> postPaths(){
		return or(regex("/api/posts.*"), regex("/api/epet.*"));
	}
	
	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("ePET")
				.description("ePET")
				.termsOfServiceUrl("http://petcc.dimap.ufrn.br/")
				.contact("petcc@dimap.ufrn.br").license("JavaInUse License")
				.licenseUrl("petcc@dimap.ufrn.br").version("1.0").build();
	}
}
*/