package com.zzr.framework;

import com.alibaba.nacos.api.config.annotation.NacosConfigListener;
import com.alibaba.nacos.api.config.annotation.NacosConfigurationProperties;
import com.alibaba.nacos.spring.util.parse.DefaultPropertiesConfigParse;
import com.sun.deploy.config.DefaultConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.logging.LogLevel;
import org.springframework.boot.logging.LoggingSystem;
import org.springframework.stereotype.Service;

import java.util.Properties;
import java.util.concurrent.*;

@Service
public class PrintLogger {

    private static Logger logger = LoggerFactory.getLogger(PrintLogger.class);

    private static final String LOGGER_TAG = "logging.level.";

    @Autowired
    private LoggingSystem loggingSystem;

    @NacosConfigListener(dataId = "nacos.log", timeout = 5000)
    public void onchange(String newLog) throws Exception{
        Properties properties = new DefaultPropertiesConfigParse().parse(newLog);
        for(Object t : properties.keySet()){
            String key = String.valueOf(t);
            if(key.startsWith(LOGGER_TAG)){
                String stringLevel = properties.getProperty(key,"info");
                LogLevel level = LogLevel.valueOf(stringLevel);
                loggingSystem.setLogLevel(key.replace(LOGGER_TAG, ""), level);
                logger.info("{}:{}", key, stringLevel);
            }
        }
    }

    public void printLogger() throws Exception{
        new ThreadPoolExecutor(1, 1, 5000, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>()).submit(() ->{
            while (true){
                logger.info("我是info级别日志");
                logger.error("我是error级别日志");
                logger.warn("我是warn级别日志");
                logger.debug("我是debug级别日志");
            }
        });
    }

}
