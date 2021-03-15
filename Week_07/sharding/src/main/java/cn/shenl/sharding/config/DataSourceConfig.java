package cn.shenl.sharding.config;

import com.alibaba.druid.pool.DruidDataSource;
import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.driver.api.ShardingSphereDataSourceFactory;
import org.apache.shardingsphere.infra.config.RuleConfiguration;
import org.apache.shardingsphere.infra.config.algorithm.ShardingSphereAlgorithmConfiguration;
import org.apache.shardingsphere.replicaquery.api.config.ReplicaQueryRuleConfiguration;
import org.apache.shardingsphere.replicaquery.api.config.rule.ReplicaQueryDataSourceRuleConfiguration;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.SQLException;
import java.util.*;

/**
 * @ClassName DataSource
 * @Description TODO
 * @Author dmm
 * @Date 2021/3/15 17:29
 * @Version 1.0
 */
@Slf4j
@Configuration
public class DataSourceConfig {
    @Bean
    public javax.sql.DataSource dataSource() {
        // 配置真实数据源
        Map<String, javax.sql.DataSource> dataSourceMap = new HashMap<>();

        // 配置第 1 个数据源
        DruidDataSource master = new DruidDataSource();
        master.setDriverClassName("com.mysql.cj.jdbc.Driver");
        master.setUrl("jdbc:mysql://192.168.99.100:3316/geektime?useUnicode=true&characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull&allowMultiQueries=true&serverTimezone=UTC");
        master.setUsername("root");
        master.setPassword("123");
        dataSourceMap.put("master", master);

        // 配置第 2 个数据源
        DruidDataSource slave = new DruidDataSource();
        slave.setDriverClassName("com.mysql.cj.jdbc.Driver");
        slave.setUrl("jdbc:mysql://192.168.99.100:3326/geektime?useUnicode=true&characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull&allowMultiQueries=true&serverTimezone=UTC");
        slave.setUsername("root");
        slave.setPassword("123");
        dataSourceMap.put("slave", slave);

        // 配置第 3 个数据源
        DruidDataSource slave2 = new DruidDataSource();
        slave2.setDriverClassName("com.mysql.cj.jdbc.Driver");
        slave2.setUrl("jdbc:mysql://192.168.99.100:3336/geektime?useUnicode=true&characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull&allowMultiQueries=true&serverTimezone=UTC");
        slave2.setUsername("root");
        slave2.setPassword("123");
        dataSourceMap.put("slave2", slave2);

//        // 配置第 2 个数据源
//        DruidDataSource slave = new DruidDataSource();
//        slave.setDriverClassName("com.mysql.cj.jdbc.Driver");
//        slave.setUrl("jdbc:mysql://192.168.99.100:3326/geektime?useUnicode=true&characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull&allowMultiQueries=true&serverTimezone=UTC");
//        slave.setUsername("root");
//        slave.setPassword("123");
//        dataSourceMap.put("slave", slave);
//​
//        // 配置第 3 个数据源
//        DruidDataSource slave2 = new DruidDataSource();
//        slave2.setDriverClassName("com.mysql.cj.jdbc.Driver");
//        slave2.setUrl("jdbc:mysql://192.168.99.100:3336/geektime?useUnicode=true&characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull&allowMultiQueries=true&serverTimezone=UTC");
//        slave2.setUsername("root");
//        slave2.setPassword("123");
//        dataSourceMap.put("slave2", slave2);

        List<String> slaveList = new ArrayList<String>();
        slaveList.add("slave");
        slaveList.add("slave2");

        //配置读写分离规则
        List<ReplicaQueryDataSourceRuleConfiguration> configurations = new ArrayList<>();
        configurations.add(new ReplicaQueryDataSourceRuleConfiguration("ds", "master", slaveList, "load_balancer"));
        Map<String, ShardingSphereAlgorithmConfiguration> loadBalancers = new HashMap<>();
        //ROUND_ROBIN:轮询算法 RANDOM:随机访问算法
        loadBalancers.put("load_balancer", new ShardingSphereAlgorithmConfiguration("ROUND_ROBIN", new Properties()));
        ReplicaQueryRuleConfiguration ruleConfiguration = new ReplicaQueryRuleConfiguration(configurations, loadBalancers);
        List<RuleConfiguration> ruleConfigurationList = new ArrayList<RuleConfiguration>();
        ruleConfigurationList.add(ruleConfiguration);

        javax.sql.DataSource dataSource = null;
        try {
            dataSource = ShardingSphereDataSourceFactory.createDataSource(dataSourceMap, ruleConfigurationList, new Properties());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        log.info("datasource : {}", dataSource);
        return dataSource;
    }
}
