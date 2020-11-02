/**
 * 文件名：MybatisScanConfiguration.java 2018年5月17日
 * 版权：Copyright(C) 2003-2017 Suzhou Keda Technology Co., Ltd. All rights reserved.
 * 修改人：刘春光
 * 修改时间： 2018年5月17日
 * 修改内容：创建文件
 */
package com.kedacom.ismp.config;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Configuration;

import com.kedacom.ismp.common.annotation.MapperScanner;
import com.kedacom.ismp.common.bean.DataSourceProperty;

@Configuration
@AutoConfigureAfter(DataSourceProperty.class)
@MapperScanner(basePackages = { "com.kedacom.ismp" })
public class MybatisScanConfiguration {
	protected static Log log = LogFactory.getLog(MybatisScanConfiguration.class);
	public MybatisScanConfiguration() {
		log.info("*************************MybatisScanConfiguration***********************");
	}
}
