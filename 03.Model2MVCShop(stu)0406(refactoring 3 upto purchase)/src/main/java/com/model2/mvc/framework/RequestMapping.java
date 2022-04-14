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
			//getResourceAsStream : resource�� InputStream����
			properties = new Properties();
			properties.load(in); //properties�� InputStreamȭ�� resource�� �Ľ��ϱ�.
		}catch(Exception ex){
			System.out.println(ex);
			throw new RuntimeException("actionmapping.properties ���� �ε� ���� :"  + ex);
		}finally{
			if(in != null){
				try{ in.close(); } catch(Exception ex){}
			}
		}
	}
	
	public synchronized static RequestMapping getInstance(String resources){
		//�ν��Ͻ� �� �Ѱ��� �����ϵ��� ����
		
		if(requestMapping == null){
			requestMapping = new RequestMapping(resources);
		}
		return requestMapping;
	}
	
	
	//method
	public Action getAction(String path){ //�־��� path�� ����, properties�� �ִ� ���� �� class�̸��� ��
		//�ش� action �ν��Ͻ� ���� �� �������̽� ��� �ڵ��� ���� (Action)����ȯ �ϴ� �޼ҵ�
		
		Action action = map.get(path); //path�� key�� ������ �ִ� value �� action�� �Ҵ�. ó���� 
		if(action == null){
			String className = properties.getProperty(path);//properties�߿� path�� key�� ������ �ִ� value(class�̸�) �Ҵ�.
			// getProperty�� properties�� ���� �޼ҵ�!
			System.out.println("prop : " + properties);
			System.out.println("path : " + path);			
			System.out.println("className : " + className);
			className = className.trim();
			try{
				Class c = Class.forName(className);//className�� ���� Class �������� 
				Object obj = c.newInstance(); //Ŭ����('___'Action) ����
				if(obj instanceof Action){ //('___'Action�̴� Action�� instance����?)
					map.put(path, (Action)obj);
					action = (Action)obj;// ���. '___'Action�� ActionŸ��(����)���� ����ȯ(�������̽� ��� �ڵ� �Ϸ���?)
				}else{
					throw new ClassCastException("Class����ȯ�� ���� �߻�  ");
				}
			}catch(Exception ex){
				System.out.println(ex);
				throw new RuntimeException("Action������ ���ϴ� ���� ���� �߻� : " + ex);
			}
		}
		return action;
	}
}