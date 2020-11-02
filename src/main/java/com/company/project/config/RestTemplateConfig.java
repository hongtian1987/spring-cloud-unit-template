/**
 * 文件名：RestTemplateConfig.java 2017年11月3日
 * 版权：Copyright(C) 2003-2017 Suzhou Keda Technology Co., Ltd. All rights reserved.
 * 修改人：刘春光
 * 修改时间： 2017年11月3日
 * 修改内容：创建文件
 */
package com.kedacom.ismp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * restTemplate需要的bean配置等信息
 * @description 
 * @since 2017年11月3日
 * @author 刘春光
 * @version 1.0
 * @date 2017年11月3日
 */
@Configuration
public class RestTemplateConfig {
	@Bean
    public RestTemplate restTemplate(ClientHttpRequestFactory factory) {
        return new RestTemplate(factory);
    }

    @Bean
    public ClientHttpRequestFactory simpleClientHttpRequestFactory() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setReadTimeout(15000);// ms
        factory.setConnectTimeout(15000);// ms
        return factory;
    }
}
