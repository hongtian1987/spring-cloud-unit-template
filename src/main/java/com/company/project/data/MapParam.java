package com.kedacom.ismp.data;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

@SuppressWarnings("unchecked")
public class MapParam<T> implements ParamFace<T> {

	private Class<T> targetClazz; 
	
	public MapParam() {
		/*Type type = this.getClass().getGenericSuperclass(); // generic 泛型
		System.out.println("Type:"+type.getClass());
		if(type instanceof ParameterizedType){
		          // 强制转化“参数化类型”
		           ParameterizedType parameterizedType = (ParameterizedType) type;
		           // 参数化类型中可能有多个泛型参数
		           Type[] types = parameterizedType.getActualTypeArguments();
		           // 获取数据的第一个元素(User.class)
		           targetClazz = (Class<T>) types[0]; // com.oa.shore.entity.User.class 
		           System.out.println("class:"+targetClazz);
		 }*/
		//Type t = getClass().getGenericSuperclass();
		//ParameterizedType pt = (ParameterizedType) t;
		targetClazz = getEntity();
		Field[] fields = targetClazz.getDeclaredFields();
		for (Field field : fields) {  
            // 同时存入大小写，如果表中列名区分大小写且有列ID和列iD，则会出现异常。  
            // 阿里开发公约，建议表名、字段名必须使用小写字母或数字；禁止出现数字开头，禁止两个下划线中间只出现数字。  
            //fieldMap.put(field.getName(), field);  
            System.out.println(field.getName()+":"+field.getType());
            if(field.getType().equals(String.class)) {
            	System.out.println(field.getName()+"====");
            }
        }
		
	}
	
	Class<T> getEntity() {
		return (Class<T>)((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}
	//@Override
	public String printRow() {
		System.out.println("class:"+targetClazz);
		return "11111";
	}
}
