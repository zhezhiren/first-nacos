package com.zzr.entity;

import com.alibaba.nacos.api.config.annotation.NacosValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TestConfiguration {

    Logger logger = LoggerFactory.getLogger(TestConfiguration.class);

    @NacosValue(value = "${people.count:0}", autoRefreshed = true)
    private String count;

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public void onChange(String newContent){
        logger.info("TestConfiguration onChange : " + newContent);
    }

}
