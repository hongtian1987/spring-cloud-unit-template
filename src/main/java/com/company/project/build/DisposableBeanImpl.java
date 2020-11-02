/**
 * 文件名：DisposableBeanImpl.java 2017年10月10日
 * 版权：Copyright(C) 2003-2017 Suzhou Keda Technology Co., Ltd. All rights reserved.
 * 修改人：刘春光
 * 修改时间： 2017年10月10日
 * 修改内容：创建文件
 */
package com.kedacom.ismp.build;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.stereotype.Component;

import com.kedacom.ismp.ismplog.IsmpLog;
import com.kedacom.ismp.mqinterface.IMqInterface;
import com.kedacom.ismp.mqinterface.utils.MqServerUtil;

/**
 * 在程序退出时进行自定义动作处理
 * @description 
 * @since 2017年10月10日
 * @author 刘春光
 * @version 1.0
 * @date 2017年10月10日
 */
@Component
public class DisposableBeanImpl implements DisposableBean {

	/**
	 * @description 
	 * @throws Exception 
	 * @throws  
	 * @since 2017年10月10日
	 * @author 刘春光
	 * @see org.springframework.beans.factory.DisposableBean#destroy()
	 */
	@Override
	public void destroy() throws Exception {
		IsmpLog.logModuleUnInit();
		if(MqServerUtil.getServer().size() > 0){
			IMqInterface server = MqServerUtil.getServer().get(0);
			MqServerUtil.unbindServer(server);
//			System.out.println("server 个数："+ MqServerUtil.getServer().size());
			server.logoutServer();
		}
		System.out.println("程序退出!");
	}

}
