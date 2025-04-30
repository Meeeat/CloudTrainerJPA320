package com.cloudtrainerjpa320.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class DataSourceConfig {

    private final HikariDataSource dataSource;
    private final JdbcTemplate jdbcTemplate;

    public DataSourceConfig(HikariDataSource dataSource, JdbcTemplate jdbcTemplate) {
        this.dataSource = dataSource;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Bean
    public HealthIndicator dbHealthIndicator() {
        return () -> {
            try {
                jdbcTemplate.queryForObject("SELECT 1", Integer.class);

                return Health.up()
                        .withDetail("database", dataSource.getDriverClassName())
                        .withDetail("url", dataSource.getJdbcUrl())
                        .withDetail("username", dataSource.getUsername())
                        .withDetail("maxPoolSize", dataSource.getMaximumPoolSize())
                        .withDetail("minIdle", dataSource.getMinimumIdle())
                        .withDetail("activeConnections", dataSource.getHikariPoolMXBean().getActiveConnections())
                        .withDetail("idleConnections", dataSource.getHikariPoolMXBean().getIdleConnections())
                        .withDetail("totalConnections", dataSource.getHikariPoolMXBean().getTotalConnections())
                        .build();
            } catch (Exception e) {
                return Health.down()
                        .withDetail("error", e.getMessage())
                        .build();
            }
        };
    }
}