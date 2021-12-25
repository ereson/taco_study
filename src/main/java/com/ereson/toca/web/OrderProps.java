package com.ereson.toca.web;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


/**
 * 用来专门配置与Order相关的数据
 */
@Component
@ConfigurationProperties(prefix = "taco.orders")
@Data
public class OrderProps {

    private int pageSize = 10;
}
