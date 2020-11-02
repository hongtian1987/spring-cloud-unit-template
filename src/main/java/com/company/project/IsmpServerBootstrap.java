package com.kedacom.ismp;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

import com.alibaba.fastjson.JSONObject;
import com.kedacom.ismp.api.model.dict.IsmpDictionaryInfo;
import com.kedacom.ismp.api.model.user.UmsUserRoleRefDto;
import com.kedacom.ismp.common.msg.ObjectRestResponse;
import com.kedacom.ismp.common.msg.TableResultResponse;
import com.kedacom.ismp.controller.DiskController;
import com.kedacom.ismp.data.MapParam;
import com.kedacom.ismp.data.TestParam;
import com.kedacom.ismp.service.impl.TestSeviceImpl;
import com.kedacom.ismp.utils.TestUtils;



@EnableDiscoveryClient
//@SpringBootApplication
//排除默认的mybatis数据配置，使用自定义数据源
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@ComponentScan(basePackages = {"com.kedacom.ismp"})
public class IsmpServerBootstrap {
	public static String getRandomPassword(int length){
        char charr[] = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890~!@#$%^&*.?".toCharArray();
        StringBuilder sb = new StringBuilder();
        Random r = new Random();
        for (int x = 0; x < length; ++x) {
            sb.append(charr[r.nextInt(charr.length)]);
        }
        return sb.toString();
    }
	
	public static String generatePassword(int length) {
		String result = null;
		result = getRandomPassword(length);
		if (result.matches(".*[a-z]{1,}.*") && result.matches(".*[A-Z]{1,}.*") && result.matches(".*[0-9]{1,}.*") && result.matches(".*[~!@#$%^&*\\.?]{1,}.*")) {
			return result;
		}
		return generatePassword(length);
	}
	
	public static void main(String[] args) {
		//System.out.println(String.class.getClassLoader());
		//System.out.println(TestSeviceImpl.class.getClassLoader());
		
		
		/*String[] urlFilters = new String[]{"/jwt/token", "/jwt/lock", "/umsapi/getuser", "/umsapi/loginret", "/jwt/user",
		        "/umsapp/userconfig", "/umsapi/user/resources", "/ums/userphoto", "/acess-cfg/types", "/ums/user/", "/auth/jwt",
		        "/auth/client", "/umsapi", "/license/acceptor", "/license/info", "/license/update", "/machine/code", 
		        "/ums/auth-menu","/ums/auth-data", "/auth/jwt/resetpassword", "/ums/auth/user-pwd"};
		String url = "/group";
		
		System.out.println("55555555555555");
		
		for (int i = 0; i < urlFilters.length; i++) {
            if (url.contains(urlFilters[i])) {
                System.out.println("找到了对应过滤的值"); 
                break;
            }
        }*/
		
		TestParam rrr = new TestParam();

		System.out.println("aaaaaa:"+ rrr.printRow());
		
		
		
		//String systemType = System.getProperty("os.name");
		//System.out.println("systemType:"+systemType);
		
		//String password = generatePassword(12);
		//System.out.println("password:"+password);
		
		
		
		
		return ; 
		
		/*String key = "device_";
		String arr[] = key.split("_");
		System.out.println("arr len:"+arr.length);
		for(int i=0; i<10; ++i){
			TestUtils.contains(arr[1]);
			System.out.println("eeeeeeeeeee\n");
		}
		TestUtils.contains(arr[1]);
		//System.out.println("arr[1]:"+arr[1]);
		String[] aa = {"444444444444"};
		System.out.println("aa len:"+aa.length);*/
//		List<UmsUserRoleRefDto> userRoles = new ArrayList<UmsUserRoleRefDto>();
//		UmsUserRoleRefDto userRoleRef = new UmsUserRoleRefDto();
//		userRoleRef.setRoleId("5555");
//		userRoleRef.setUserId("07092b23077649558844d921aee81bc4");
//		userRoles.add(userRoleRef);
//		
//		System.out.println(JSONObject.toJSONString(userRoles));
		
/////		DiskController controller = new DiskController();
//		controller.getDiskInfo2();
/////		controller.getLocalMAC();
		
		/*TableResultResponse<IsmpDictionaryInfo> tab = new TableResultResponse<IsmpDictionaryInfo>();
		List<IsmpDictionaryInfo> infos = new ArrayList<IsmpDictionaryInfo>();
		IsmpDictionaryInfo info = new IsmpDictionaryInfo();
		info.setTypeCode("position_code");
		info.setDictCode("auxiliary_police");
		info.setDictName("辅警");
		info.setAuthFlag(1);
		info.setDictValue("auxiliary_police");
		infos.add(info);
		tab.setRows(infos);
		tab.setTotal(1);
		System.out.println(JSONObject.toJSONString(tab));*/
		// TODO Auto-generated method stub
//		SpringApplication.run(IsmpServerBootstrap.class, args);
		
		

	}
}
