学习笔记

# 1 按自己设计的表结构，插入 100 万订单模拟数据，测试不同方式的插入效率





# 1 配置一遍异步复制，半同步复制、组复制

![image-20210314142320520](C:\Users\dmm\AppData\Roaming\Typora\typora-user-images\image-20210314142320520.png)

使用docker 完成了两个容器之间的异步复制。

**Master(主)：**

```
docker run -p 3316:3306 --name mymysql -e MYSQL_ROOT_PASSWORD=123 -d mysql:5.7
```

**Slave(从)：**

```
docker run -p 3326:3306 --name slave -e MYSQL_ROOT_PASSWORD=123 -d mysql:5.7
```

Master对外映射的端口是3339，Slave对外映射的端口是3340。因为docker容器是相互独立的，每个容器有其独立的ip，所以不同容器使用相同的端口并不会冲突。这里我们应该尽量使用mysql默认的3306端口，否则可能会出现无法通过ip连接docker容器内mysql的问题。

![image-20210313214440202](C:\Users\dmm\AppData\Roaming\Typora\typora-user-images\image-20210313214440202.png)





change master to master_host='172.17.0.2', master_user='slave', master_password='123', master_port=3306,master_log_file='mysql-bin.000001', master_log_pos= 617, master_connect_retry=30;

https://my.oschina.net/u/3773384/blog/1810111

https://www.cnblogs.com/songwenjie/p/9371422.html



# 2 读写分离 - 动态切换数据源版本 1.0

基于springboot实现读写分离



建表：

```sql
CREATE TABLE IF NOT EXISTS `test`(
   `id` INT UNSIGNED AUTO_INCREMENT,
   `num` INT NOT NULL,
   PRIMARY KEY ( `id` )
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
```



https://blog.csdn.net/qq_37502106/article/details/91044952

目前仅完成了一个主一个从，等以后完成一主多从

# 3 shardingsphere版本

完成了一主两从，并且运用轮询机制。