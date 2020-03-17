package com.zzr.entity;

import com.alibaba.nacos.api.config.ConfigType;
import com.alibaba.nacos.api.config.annotation.NacosConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;


@NacosConfigurationProperties(prefix = "apple", dataId = "apple", type = ConfigType.YAML, autoRefreshed = true)
@Configuration
public class Apple {

    private List<String> list;

    private Map<String, List<String>> listMap;

    public List<String> getList() {
        return list;
    }

    public void setList(List<String> list) {
        this.list = list;
    }

    public Map<String, List<String>> getListMap() {
        return listMap;
    }

    public void setListMap(Map<String, List<String>> listMap) {
        this.listMap = listMap;
    }

    @Override
    public String toString() {
        return "Apple{" + "list=" + list + ", listMap=" + listMap + '}';
    }

}
