package com.model2.mvc.framework;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;


public class RequestMapping {
	//Field
	private static RequestMapping requestMapping;
	private Map<String, Action> map; //map<key,value>
	private Properties properties;
	
	//Constructor
	private RequestMapping(String resources) {//
		map = new HashMap<String, Action>();
		InputStream in = null;
		try{
			in = getClass().getClassLoader().getResourceAsStream(resources);
			//getResourceAsStream : resource를 InputStream으로
			properties = new Properties();
			properties.load(in); //properties에 InputStream화된 resource를 파싱하기.
		}catch(Exception ex){
			System.out.println(ex);
			throw new RuntimeException("actionmapping.properties 파일 로딩 실패 :"  + ex);
		}finally{
			if(in != null){
				try{ in.close(); } catch(Exception ex){}
			}
		}
	}
	
	public synchronized static RequestMapping getInstance(String resources){
		//인스턴스 단 한개만 생성하도록 설계
		
		if(requestMapping == null){
			requestMapping = new RequestMapping(resources);
		}
		return requestMapping;
	}
	
	
	//method
	public Action getAction(String path){ //주어진 path를 통해, properties에 있는 내용 중 class이름을 얻어서
		//해당 action 인스턴스 생성 후 인터페이스 기반 코딩을 위한 (Action)형변환 하는 메소드
		
		Action action = map.get(path); //path를 key로 가지고 있는 value 를 action에 할당. 처음엔 
		if(action == null){
			String className = properties.getProperty(path);//properties중에 path를 key로 가지고 있는 value(class이름) 할당.
			// getProperty는 properties의 내장 메소드!
			System.out.println("prop : " + properties);
			System.out.println("path : " + path);			
			System.out.println("className : " + className);
			className = className.trim();
			try{
				Class c = Class.forName(className);//className을 가진 Class 가져오기 
				Object obj = c.newInstance(); //클래스('___'Action) 생성
				if(obj instanceof Action){ //('___'Action이니 Action의 instance겠죠?)
					map.put(path, (Action)obj);
					action = (Action)obj;// 결론. '___'Action을 Action타입(상위)으로 형변환(인터페이스 기반 코딩 하려고?)
				}else{
					throw new ClassCastException("Class형변환시 오류 발생  ");
				}
			}catch(Exception ex){
				System.out.println(ex);
				throw new RuntimeException("Action정보를 구하는 도중 오류 발생 : " + ex);
			}
		}
		return action;
	}
}