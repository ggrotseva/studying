package com.example.advquerying.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
public class Config {

    @Autowired
    private Environment environment;

    @Bean
    public DataSource createDataSource() {
        DriverManagerDataSource dmdc = new DriverManagerDataSource();

        dmdc.setDriverClassName(environment.getProperty("spring.datasource.driverClassName"));
        dmdc.setUrl(environment.getProperty("spring.datasource.Url"));
        dmdc.setUsername(environment.getProperty("spring.datasource.username"));
        dmdc.setPassword(environment.getProperty("spring.datasource.password"));

        return dmdc;
    }
}
