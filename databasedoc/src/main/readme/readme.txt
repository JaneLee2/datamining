1.功能说明：
将数据库的表信息（通常意义上表的数据字典）存放到word中。

2.配置参数：application.yml 以下数据根据实际情况填写
spring.datasource.url: jdbc:mariadb://172.31.138.93:3306/test?autoReconnect=true&autoReconnectForPools=true&useUnicode=true&characterEncoding=UTF-8&allowMultiQueries=true
spring.datasource.driverClassName: org.mariadb.jdbc.Driver
spring.datasource.username: root
spring.datasource.password: Ruijie123
targetPath:D:/数据库数据字典

3.运行方式：
java -jar D:/datamining-0.0.1-SNAPSHOT.jar spring.config.location="D:/application.yml"
