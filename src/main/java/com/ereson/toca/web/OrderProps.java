package com.ereson.toca.web;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;


/**
 * 用来专门配置与Order相关的数据
 * @Component 注释，用来将普通的Pojo实例化到spring容器中（用于非@Controller @Service @Repository)
 */
@Component
@ConfigurationProperties(prefix = "taco.orders")
@Data
@Validated
public class OrderProps {

    @Min(value=5, message = "must be between 5 and 25")
    @Max(value=25, message = "must be between 5 and 25")
    private int pageSize = 20;
}
