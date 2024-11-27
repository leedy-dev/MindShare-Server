package com.mindshare.security;

import com.mindshare.cmm.MindshareCmmBasePackage;
import com.mindshare.core.MindshareCoreBasePackage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackageClasses = { MindshareCoreBasePackage.class, MindshareCmmBasePackage.class, MindshareSecurityBasePackage.class })
@ComponentScan(basePackageClasses = { MindshareCoreBasePackage.class, MindshareCmmBasePackage.class, MindshareSecurityBasePackage.class })
@EntityScan(basePackageClasses = { MindshareCoreBasePackage.class, MindshareCmmBasePackage.class, MindshareSecurityBasePackage.class })
public class MindshareSecurityApplication {

    public static void main(String[] args) {
        SpringApplication.run(MindshareSecurityApplication.class, args);
    }

}
