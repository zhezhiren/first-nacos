package com.zzr;

import static org.springframework.core.env.StandardEnvironment.SYSTEM_ENVIRONMENT_PROPERTY_SOURCE_NAME;
import static org.springframework.core.env.StandardEnvironment.SYSTEM_PROPERTIES_PROPERTY_SOURCE_NAME;

import com.alibaba.nacos.api.annotation.NacosInjected;
import com.alibaba.nacos.api.common.Constants;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.annotation.NacosValue;
import com.alibaba.nacos.spring.context.annotation.config.NacosPropertySource;
import com.zzr.entity.Foo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@NacosPropertySource(dataId = Application.DATA_ID, first = true, before = SYSTEM_PROPERTIES_PROPERTY_SOURCE_NAME, after = SYSTEM_ENVIRONMENT_PROPERTY_SOURCE_NAME)
@EnableScheduling
public class Application {

    public static final String content = "dept: Aliware\ngroup: Alibaba";

    public static final String DATA_ID = "test";


    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }

    @Bean
    @Order(Ordered.LOWEST_PRECEDENCE)
    public CommandLineRunner firstCommandLineRunner(){
        return  new FirstCommandLineRunner();
    }

    @Bean
    @Order(Ordered.LOWEST_PRECEDENCE - 1)
    public CommandLineRunner secondCommondLineRunner(){
        return new SecondCommandLineRunner();
    }

    @Bean
    public Foo foo(){
        return new Foo();
    }

    @Configuration
    @ConditionalOnProperty(prefix = "people", name= "enable", havingValue="true")
    protected static class People{

        private static Logger logger = LoggerFactory.getLogger(People.class);

        public Object object(){
            logger.info("[liaochuntao] : " + this.getClass().getCanonicalName());
            return new Object();
        }

    }

    public static class FirstCommandLineRunner implements CommandLineRunner{
        private static Logger logger = LoggerFactory.getLogger(FirstCommandLineRunner.class);

        @NacosInjected
        private ConfigService configService;

        @Override
        public void run(String... args) throws Exception {
            if(configService.publishConfig(DATA_ID, Constants.DEFAULT_GROUP, content)){
                Thread.sleep(200);
                logger.info("First runner success: " + configService
                        .getConfig(DATA_ID, Constants.DEFAULT_GROUP, 5000));
            }
        }
    }

    public static class SecondCommandLineRunner implements CommandLineRunner{

        private static Logger logger = LoggerFactory.getLogger(SecondCommandLineRunner.class);

        @NacosValue("${dept:unknown}")
        private String dept;

        @NacosValue("${group:unknown}")
        private String group;

        @Autowired
        private Foo foo;

        @Override
        public void run(String... args) throws Exception {
            logger.info("Second runner. dept: " + dept + ", group: " + group);
            logger.info("Second runner. foo: " + foo);
        }
    }

}
