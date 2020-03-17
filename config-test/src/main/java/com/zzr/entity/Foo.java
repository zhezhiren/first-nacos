package com.zzr.entity;

import com.alibaba.nacos.api.config.annotation.NacosConfigurationProperties;
import com.zzr.Application;

@NacosConfigurationProperties(dataId = Application.DATA_ID)
public class Foo {

    private String dept;

    private String group;

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    @Override
    public String toString() {
        return "Foo{" + "dept='" + dept + '\'' + ", group='" + group + '\'' + '}';
    }
}
