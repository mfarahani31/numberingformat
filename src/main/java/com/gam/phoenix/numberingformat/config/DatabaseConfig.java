package com.gam.phoenix.numberingformat.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * @author Mohammad Farahani (farahani@gamelectronics.com)
 **/


@Configuration
@EntityScan(basePackages = {"com.gam.phoenix.numberingformat.model"})
@EnableJpaAuditing(
        auditorAwareRef = "auditorAwareProvider"
)
@EnableJpaRepositories(basePackages = {"com.gam.phoenix"})
public class DatabaseConfig {

    @Bean
    public AuditorAware<String> auditorAwareProvider() {
        return new AuditorAwareImpl();
    }
}
