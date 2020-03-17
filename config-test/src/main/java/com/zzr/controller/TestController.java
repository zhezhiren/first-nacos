package com.zzr.controller;

import com.alibaba.nacos.api.config.annotation.NacosValue;
import com.zzr.entity.Apple;
import com.zzr.entity.TestConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    private static Logger logger = LoggerFactory.getLogger(TestController.class);

    @NacosValue(value = "${people.enable.bbbbb}", autoRefreshed = true)
    private String enable;

    @Value("${people.enable}")
    private String springEnable;

    @Autowired
    private Apple apple;

    @Autowired
    private TestConfiguration configuration;

    @Scheduled(cron = "0/10 * * * * *")
    public void print(){
        logger.info(configuration.getCount());
    }

    @RequestMapping("/get")
    @ResponseBody
    public String testGet(){
        return enable + "-" + springEnable;
    }

    @GetMapping("/apple")
    @ResponseBody
    public  String getApple(){
        return apple.toString();
    }

}
