package com.mindshare.db.config;

import jakarta.annotation.PostConstruct;
import org.h2.tools.Server;
import org.springframework.context.annotation.Configuration;

import java.sql.SQLException;

@Configuration
public class H2DatabaseConfig {

    @PostConstruct
    public void startH2Server() throws SQLException {
        Server.createTcpServer("-tcp", "-tcpAllowOthers", "-tcpPort", "9092").start();
    }

}
