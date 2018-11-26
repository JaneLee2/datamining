## 说明介绍 ##      
databasedoc是一个简易的将数据库的整个库表的元信息（表信息、字段信息）自动导出到word文档中，用户可以简单的配置就可生成数据库字典。       
## 使用技术 ##  
- 使用刚接触到的Springboot架构了控制层、服务层、dao层的层级结构；          
- 配置使用阿里巴巴的Druid数据连接池；    
- 与word的交互则使用apache开源的poi工具；
- 日志使用logback 
![](https://i.imgur.com/pdTLFB9.png)                                                                                                                                                                    
## 使用方法 ##
1. 运行方式
java -jar D:/datamining-0.0.1-SNAPSHOT.jar spring.config.location="D:/application.yml"               
其中：    
	参数D:/datamining-0.0.1-SNAPSHOT.jar为jar包存放位置；  
	参数spring.config.location为外部application配置文件存放的地方；   
2. application配置文件说明
可将代码中的application.yml拷贝至外部，并修改其中的部分参数，说明如下：   
1） **spring.datasource.url**:  导出数据源的url地址   
2） **spring.datasource.driverClassName**:  导出数据源的驱动名称     
3） **spring.datasource.username**:  导出数据源数据库用户名  
4） **spring.datasource.password**:  导出数据源数据库密码  
5） **targetPath**:  目标word存放路径和名称    
不同数据源的数据连接配置不同，大家可以根据阿里巴巴的Druid数据连接不同数据源来配置，示例如下：   
1） mysql数据库   
**spring.datasource.url**:  jdbc:mariadb://ip:port/base?autoReconnect=true&autoReconnectForPools=true&useUnicode=true&characterEncoding=UTF-8&allowMultiQueries=true   
**spring.datasource.driverClassName**:  org.mariadb.jdbc.Driver     
**spring.datasource.username**:  username      
**spring.datasource.password**:  password   
2） oracle数据库   
**spring.datasource.url**:  jdbc:oracle:thin:@ip:port:sid   
**spring.datasource.driverClassName**:  oracle.jdbc.driver.OracleDriver     
**spring.datasource.username**:  username       
**spring.datasource.password**:  password   
3） sqlserver数据库   
**spring.datasource.url**:  jdbc:sqlserver://ip:port;DatabaseName=base   
**spring.datasource.driverClassName**:  com.microsoft.sqlserver.jdbc.SQLServerDriver     
**spring.datasource.username**:  username      
**spring.datasource.password**:  password    
4） postsql数据库   
**spring.datasource.url**:  jdbc:postgresql://ip:port/base   
**spring.datasource.driverClassName**:  org.postgresql.Driver     
**spring.datasource.username**:  username       
**spring.datasource.password**:  password    
## word效果 ##
![](https://i.imgur.com/rujaxnW.png)
