package com.ccd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SpringBootApplication
//@ComponentScan(basePackageClasses = PatientController.class)
public class Application {


    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                // We'll always run our apps from localhost:3000 (which is an alias for 127.0.0.1:3000)
                registry.addMapping("/**")
                        .allowedMethods("DELETE", "GET", "POST", "PUT")
                        .allowedOrigins("chrome-extension://fhbjgbiflinjbdggehcddcbncdddomop", "http://127.0.0.1:8080", "http://localhost:8080");
            }
        };
    }
    
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}