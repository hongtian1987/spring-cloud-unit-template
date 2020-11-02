/**
 * 文件名：ConnMqCmd.java 2017年9月20日
 * 版权：Copyright(C) 2003-2017 Suzhou Keda Technology Co., Ltd. All rights reserved.
 * 修改人：刘春光
 * 修改时间： 2017年9月20日
 * 修改内容：创建文件
 */
package com.kedacom.ismp.build;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.kedacom.ismp.common.utils.IsmpGlobalUtil;
import com.kedacom.ismp.ismplog.EnumLogModule;
import com.kedacom.ismp.ismplog.IsmpLog;
import com.kedacom.ismp.mqinterface.EnumMqLogLevel;
import com.kedacom.ismp.mqinterface.IMqInterface;
import com.kedacom.ismp.mqinterface.IMqOnLog;
import com.kedacom.ismp.mqinterface.IMqOnServerDown;
import com.kedacom.ismp.mqinterface.MqError;
import com.kedacom.ismp.mqinterface.MqServerInfo;
import com.kedacom.ismp.mqinterface.mqkafka.MqKafka;
import com.kedacom.ismp.mqinterface.utils.MqServerUtil;

/**
 * 开始连接消息队列(kafka)服务
 * @description 
 * @since 2017年9月20日
 * @author 刘春光
 * @version 1.0
 * @date 2017年9月20日
 */
@Component
@Order(2)
public class ConnMqCmd implements CommandLineRunner {
	private static final String MODULE_NAME="RESOURCE";
	
	@Value("${mq.type}")
	private String type;
	@Value("${mq.host}")
	private String host;
	@Value("${mq.port}")
	private Integer port;
	@Value("${mq.address}")
	private String address;
	@Value("${mq.username:''}")
	private String username;
	@Value("${mq.password:''}")
	private String password;
	@Value("${ismp.debug:''}")
	private String debugFlag;
//	@Autowired
//	private ApplicationContext applicationContext;
	/**
	 * @description 
	 * @param args
	 * @throws Exception 
	 * @throws  
	 * @since 2017年9月19日
	 * @author 刘春光
	 * @see org.springframework.boot.CommandLineRunner#run(java.lang.String[])
	 */
	@Override
	public void run(String... args) throws Exception {
		System.out.println("初始化系统配置");
		if("1".equals(debugFlag) || "true".equals(debugFlag)) IsmpGlobalUtil.DEBUG_FLAG = true;
		System.out.println("准备连接mq服务器 server:" + address);
		//创建mqserver连接
		OnCallBack onCb = new OnCallBack();
		IMqInterface mqServer = new MqKafka("default", onCb, onCb);
		MqServerInfo[] serverInfo = new MqServerInfo[1];
		serverInfo[0] = new MqServerInfo();
		serverInfo[0].setServerHost(host);
		serverInfo[0].setServerPort(port);
		MqError eRet = mqServer.loginServer(serverInfo, username, 
					password, IMqInterface.MQ_DEFAULT_TIMEOUT);
		if(MqError.ERR_NO != eRet){
			throw new Exception(String.format("login to server failed!errcode :%d\n", eRet.type()));
		}
		if(IMqInterface.MQ_SERVER_ONLINE != mqServer.getServerState()){
			throw new Exception(String.format("ERROR:Server not online!\n"));
		}
		MqServerUtil.bindServer(mqServer);
		IsmpLog.logModuleInit(EnumLogModule.RESOURCE, MODULE_NAME, mqServer);
		IsmpLog logIst = IsmpLog.getInstance();
		if(null != logIst){
			//System.out.println("ERROR:MQ connect start!");
		}else{
			IsmpLog.logModuleUnInit();
			throw new Exception(String.format("ERROR:log module init failed!"));
		}
	}
}

class OnCallBack implements IMqOnLog, IMqOnServerDown{
	Thread recThread = null;
	   OnCallBack(){
		}
		/* (non-Javadoc)
		 * @see com.kedacom.cimsv2.mqinterface.IMqOnLog#OnLog(com.kedacom.cimsv2.mqinterface.EMqLogLevel, java.lang.String, java.lang.Object[])
		 */
		public void onLog(EnumMqLogLevel eLevel, String format, Object... args) {
			String strLog = String.format(format, args);
			System.out.printf("log:%s  %s\n", eLevel.name(), strLog);
		}
		/**
		 * @description  
		 * @since 2017年12月13日
		 * @author 刘春光
		 * @see com.kedacom.ismp.mqinterface.IMqOnServerDown#onServerDown()
		 */
		@Override
		public void onServerDown(IMqInterface server) {
			//只有一个server，如果已经有线程存在，直接退出
			if(null != recThread && recThread.isAlive()){
				return;
			}
			ServerReconnecter reconnecter = new ServerReconnecter(server);
			recThread = new Thread(reconnecter);
			recThread.start();
		}
	}
class ServerReconnecter implements Runnable {
    IMqInterface server;

    public ServerReconnecter(IMqInterface server){
    	this.server = server;
    }
    public void run() {
        try {
            while (server.getServerState() != IMqInterface.MQ_SERVER_ONLINE) {
            	System.out.println("mqserver 服务重连");
            	server.reConnectServer(IMqInterface.MQ_DEFAULT_TIMEOUT);
            	Thread.sleep(10000);
            }
        } catch (Exception e) {
        	e.printStackTrace();
        } 
    }
}