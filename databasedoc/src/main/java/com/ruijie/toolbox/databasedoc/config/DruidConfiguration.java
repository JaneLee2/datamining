package com.ruijie.toolbox.databasedoc.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.sql.SQLException;

@Configuration
public class DruidConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(DruidConfiguration.class);
/*
    @Bean
    public ServletRegistrationBean druidServlet() {
        logger.info("init Druid Servlet Configuration ");
        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(new StatViewServlet(), "/druid*//*");
        // IP白名单
        servletRegistrationBean.addInitParameter("allow", "127.0.0.1");
        // IP黑名单(共同存在时，deny优先于allow)
        //servletRegistrationBean.addInitParameter("deny", "192.168.1.100");
        //控制台管理用户
        servletRegistrationBean.addInitParameter("loginUsername", "admin");
        servletRegistrationBean.addInitParameter("loginPassword", "admin");
        //是否能够重置数据 禁用HTML页面上的“Reset All”功能
        servletRegistrationBean.addInitParameter("resetEnable", "false");
        return servletRegistrationBean;
    }

    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(new WebStatFilter());
        filterRegistrationBean.addUrlPatterns("*//*");
        filterRegistrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid*//*");
        return filterRegistrationBean;
    }*/

    @Bean(name = "dataSource")    //声明其为Bean实例
    @Primary  //在同样的DataSource中，首先使用被标注的DataSource
    public DataSource dataSource(@Value("${spring.datasource.driverClassName}") String driver,
                                        @Value("${spring.datasource.url}") String url,
                                        @Value("${spring.datasource.username}") String username,
                                        @Value("${spring.datasource.password}") String password,
                                        @Value("${spring.datasource.minIdle}") int minIdle,
                                        @Value("${spring.datasource.initialSize}") int initialSize,
                                        @Value("${spring.datasource.maxActive}") int maxActive,
                                        @Value("${spring.datasource.maxWait}") int maxWait,
                                        @Value("${spring.datasource.timeBetweenEvictionRunsMillis}") long timeBetweenEvictionRunsMillis,
                                        @Value("${spring.datasource.minEvictableIdleTimeMillis}") long minEvictableIdleTimeMillis,
                                        @Value("${spring.datasource.validationQuery}") String validationQuery,
                                        @Value("${spring.datasource.testWhileIdle}") Boolean testWhileIdle,
                                        @Value("${spring.datasource.testOnBorrow}") Boolean testOnBorrow,
                                        @Value("${spring.datasource.testOnReturn}") Boolean testOnReturn,
                                        @Value("${spring.datasource.poolPreparedStatements}") Boolean poolPreparedStatements,
                                        //@Value("${spring.datasource.maxPoolPreparedStatementPerConnectionSize}") int maxPoolPreparedStatementPerConnectionSize,
                                        @Value("${spring.datasource.filters}") String filters,
                                        @Value("${spring.datasource.removeAbandoned}") Boolean removeAbandoned,
                                        @Value("${spring.datasource.removeAbandonedTimeout}") int removeAbandonedTimeout,
                                        @Value("${spring.datasource.logAbandoned}") Boolean logAbandoned
                                        //@Value("${spring.datasource.connectionProperties}") String connectionProperties
                                        ) {
            DruidDataSource datasource = new DruidDataSource();
            datasource.setUrl(url);
            datasource.setUsername(username);
            datasource.setPassword(password);
            datasource.setDriverClassName(driver);

            //configuration
            datasource.setInitialSize(initialSize);
            datasource.setMinIdle(minIdle);
            datasource.setMaxActive(maxActive);
            datasource.setMaxWait(maxWait);
            datasource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
            datasource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
            datasource.setValidationQuery(validationQuery);
            datasource.setTestWhileIdle(testWhileIdle);
            datasource.setTestOnBorrow(testOnBorrow);
            datasource.setTestOnReturn(testOnReturn);
            datasource.setPoolPreparedStatements(poolPreparedStatements);
            datasource.setRemoveAbandoned(removeAbandoned);
            datasource.setRemoveAbandonedTimeout(removeAbandonedTimeout);
            datasource.setLogAbandoned(logAbandoned);
            //datasource.setMaxPoolPreparedStatementPerConnectionSize(maxPoolPreparedStatementPerConnectionSize);
            try {
                datasource.setFilters(filters);
            } catch (SQLException e) {
                System.err.println("druid configuration initialization filter: " + e);
            }
            //datasource.setConnectionProperties(connectionProperties);
            return datasource;
        }

    @Bean(name = "jdbcTemplate")
    public JdbcTemplate primaryJdbcTemplate(
            @Qualifier("dataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}