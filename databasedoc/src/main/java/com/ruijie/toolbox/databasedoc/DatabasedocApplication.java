package com.ruijie.toolbox.databasedoc;

import com.ruijie.toolbox.databasedoc.service.AttributeAnalysisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class DatabasedocApplication {

	private static final Logger logger = LoggerFactory.getLogger(DatabasedocApplication.class);
	public static void main(String[] args) throws Exception {
		ApplicationContext context = SpringApplication.run(DatabasedocApplication.class, args);
		logger.info("App启动了!");
		//检测输入参数：如果有需要的话就再次判断参数
		if (args.length == 0 ){
			logger.info("没有参数输入需求");
		}else if(args.length == 1){
			logger.info("参数输入需求为一个");
		}else{
			logger.info("参数输入需求为至少两个");
		}
		AttributeAnalysisService attributeAnalysisService = (AttributeAnalysisService)context.getBean("attributeAnalysisService");
		attributeAnalysisService.start();
		logger.info("############# over #################");
	}

}
