package org.shenling.week5.database.jdbc;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

@Configuration
public class HikariDatasourceConfig {

    @Bean(name = "primaryDataSource")
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource")
    public HikariDataSource dataSource() {
        return new HikariDataSource();
    }

    private static String driverClassName;
    private static String url;
    private static String username;
    private static String password;

    /**
     * 通过静态代码块，初始化数据库连接配置数据，并且注册数据库驱动
     */
    static {
        try {
            Properties pr = new Properties();
            //通过读取Properties文件给属性赋值，即每次使用该工具类都会读取最新配置进行连接
            pr.load(new FileInputStream(new File("E:\\java01\\Week_05\\src\\main\\resources\\application.yml")));
            driverClassName = pr.getProperty("driver-class-name");
            url = pr.getProperty("url");
            username = pr.getProperty("username");
            password = pr.getProperty("password");
            Class.forName(driverClassName);
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
            throw new RuntimeException("获取数据库连接异常，请检查配置数据");
        }
    }
    /**
     * 获取数据库连接对象
     * @return
     */
    public static Connection getConnection() {
        HikariConfig config = new HikariConfig("E:\\java01\\Week_05\\src\\main\\resources\\Hikari.properties");
        HikariDataSource hikariDataSource = new HikariDataSource(config);

        Connection con = null;
        try {
            con = hikariDataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("获取数据库连接异常，请检查配置数据");
        }
        return con;
    }
    /**
     * 关闭JDBC相关资源
     * @param con
     * @param sta
     * @param rs
     */
    public static void closeResource(Connection con, Statement sta, ResultSet rs) {
        try {
            if(con!=null) {
                con.close();
            }
            if(sta!=null) {
                sta.close();
            }
            if(rs!=null) {
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
