package com.elvis.springapp.dukachallenge.config;


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


@EnableSwagger2
@Configuration
public class SwaggerConfig {

    @Bean
    public Docket todoApi(){
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.elvis.springapp.dukachallenge"))
                .paths(PathSelectors.regex("/.*"))
                .build()
                .apiInfo(metaData());
    }
    private ApiInfo metaData() {
        return new ApiInfoBuilder()
                .title("To-Do App API Specification")
                .description("\"To-Do App API Specification\"")
                .version("1.0.0")
                .contact(new Contact("Kwizera.A.Elvis", "", "kwizeraelvis6@gmail.com"))
                .build();
    }
}
