package com.wjl.config;

import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.web.servlet.ErrorPage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

/**
 * @author wjl
 * @date 2018/2/9
 */
@Configuration
public class ErrorClass {
    @Bean
    public EmbeddedServletContainerCustomizer containerCustomizer(){
        return (container->{
            ErrorPage error401Page  = new ErrorPage(HttpStatus.UNAUTHORIZED,"/error.html");
            ErrorPage error404Page  = new ErrorPage(HttpStatus.NOT_FOUND,"/error.html");
            ErrorPage error405Page  = new ErrorPage(HttpStatus.METHOD_NOT_ALLOWED,"/error.html");
            ErrorPage error500Page  = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR,"/error.html");
            container.addErrorPages(error401Page,error404Page,error405Page,error500Page);
        });
    }
}
