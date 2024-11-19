package com.mindshare.ams;

import com.mindshare.core.MindshareCoreBasePackage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackageClasses = { MindshareCoreBasePackage.class, MindshareAmsBasePackage.class })
@ComponentScan(basePackageClasses = { MindshareCoreBasePackage.class, MindshareAmsBasePackage.class })
@EntityScan(basePackageClasses = { MindshareCoreBasePackage.class, MindshareAmsBasePackage.class })
public class MindshareAmsApplication {

    public static void main(String[] args) {
        SpringApplication.run(MindshareAmsApplication.class, args);
    }

}
