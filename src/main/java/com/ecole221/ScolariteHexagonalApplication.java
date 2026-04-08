package com.ecole221;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication//(exclude = {DataSourceAutoConfiguration.class})
@EnableJpaRepositories(
        basePackages = {
                "com.ecole221.infrastructure.persistence",
                "com.ecole221.infrastructure.projection"
        }
)
@EntityScan(
        basePackages = {
                "com.ecole221.infrastructure.persistence",
                "com.ecole221.infrastructure.projection"
        }
)
@EnableScheduling
public class ScolariteHexagonalApplication {

    public static void main(String[] args) {
        SpringApplication.run(ScolariteHexagonalApplication.class, args);
    }
}
