package com.zz.core.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {

	public static Integer StringToInteger(String str) {
		if (str != null && str.length() > 0) {
			Pattern pattern = Pattern.compile("[0-9]+");
			Matcher matcher = pattern.matcher(str);
			boolean matches = matcher.matches();
			if (matches) {
				return Integer.valueOf(str);
			}
		}
		return null;
	}

	public static int StringToInt(String str) {
		if (str != null && str.length() > 0) {
			Pattern pattern = Pattern.compile("[0-9]+");
			Matcher matcher = pattern.matcher(str);
			boolean matches = matcher.matches();
			if (matches) {
				return Integer.valueOf(str);
			}
		}
		return 0;
	}
	/**
	 * 字符串通过截取字符分组
	 * @param content
	 * @param split
	 * @return
	 */
	public static String[] StringToArray(String content,String split){
		if(content!=null&&content.trim().length()>0){
			if(content.indexOf(split)!=-1){
				String[] arrays = content.split(split);
				String[] contents = new String[arrays.length];
				int index = 0;
				for (int i = 0; i <arrays.length ; i++) {
					String ar = arrays[i];
					if(ar.trim().length()>0){
						contents[index] = ar;
						index ++;
					}
				}
				return contents.length>0?contents:null;
			}else{
				return content.length()>0?new String[]{content}:null;
			}
		}
		return null;
	}
	
	public static List<String> StringToList(String content,String split){
		List<String> result = null;
		if(content!=null&&content.trim().length()>0){
			result = new ArrayList<String>();
			if(content.indexOf(split)!=-1){
				String[] array = content.split(split);
				for (int i = 0; i < array.length; i++) {
					String ar = array[i];
					if(ar.trim().length()>0){
						result.add(ar);
					}
				}
			}else{
				result.add(content);
			}
		}
		return result;
	}
	
	/**
	 * 数组通过标识符组合转换字符串内容
	 * @param contents
	 * @param split
	 * @return
	 */
	public static String ArrayToString(String[] contents,String split){
		if(contents!=null&&contents.length>0&&split!=null){
			StringBuffer buf = new StringBuffer();
			String _split = "";
			for (String content : contents) {
				buf.append(_split);
				buf.append(content);
				_split = split;
			}
			return buf.toString();
		}
		return null;
	}

	/**
	 * 获取随机字符
	 * 
	 * @return
	 */
	public static char getRandChar() {
		char ch = '0';
		LinkedList<String> ls = new LinkedList<String>();
		for (int i = 0; i < 10; i++) {// 0-9
			ls.add(String.valueOf(48 + i));
		}
		int index = (int) (Math.random() * ls.size());
		if (index > (ls.size() - 1)) {
			index = ls.size() - 1;
		}
		ch = (char) Integer.parseInt(String.valueOf(ls.get(index)));
		return ch;
	}

	/**
	 * 获取随机验证码
	 * @param length
	 * @return
	 */
	public static String getCheckCode(int length) {
		// 取随机产生的认证码(length位数字)
		String checkCode = "";
		for (int i = 0; i < length; i++) {
			char rand = StringUtils.getRandChar();
			checkCode += rand;
		}
		return checkCode;
	}
	
	/**
     * 设置非空值到Map映射
     * @param t
     * @param filterName 过滤掉不映射的字段
     * @param <T>
     * @return
     * @throws Exception
     */
    public static <T> Map<String, Object> setObjectToMap(T t,String[] filterName)throws Exception{
        Map<String, Object> conditions = new HashMap<String, Object>();
        Method[] methods = t.getClass().getMethods();
        for (Method method : methods) {
            String name = method.getName();
            if(name.indexOf("get")!=-1&&!name.equals("getClass")){
                name = name.substring(3);
                name = name.substring(0,1).toLowerCase()+name.substring(1);
                boolean isFilter = false;
                if(filterName!=null&&filterName.length>0){
                    for (String  _name: filterName) {
                        if(name.equals(_name)){
                            isFilter = true;
                            break;
                        }
                    }
                }
                if(isFilter){
                    continue;
                }
                Object object = method.invoke(t);
                if(null!=object&&!"".equals(object)){
                    conditions.put(name, method.invoke(t));
                }
            }
        }
        return conditions;
    }
    
    /**
     * 将字符串日期类型转换为Date类型
     * @param dateStr
     * @return
     */
    public static Date stringToDate(String dateStr){
    	Map<String, String> patternMap = new HashMap<String, String>();
    	patternMap.put("[0-9]{4}-[0-9]{2}-[0-9]{2}T[0-9]{2}:[0-9]{2}:[0-9]{2}\\.[0-9]{4}Z", "yyyy-MM-dd'T'HH:mm:ss.SSSZ'Z'");
    	patternMap.put("[0-9]{4}-[0-9]{2}-[0-9]{2}T[0-9]{2}:[0-9]{2}:[0-9]{2}\\.[0-9]{3}Z", "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
    	patternMap.put("[0-9]{4}-[0-9]{2}-[0-9]{2}T[0-9]{2}:[0-9]{2}:[0-9]{2}Z", "yyyy-MM-dd'T'HH:mm:ss'Z'");
    	patternMap.put("[0-9]{4}-[0-9]{2}-[0-9]{2}", "yyyy-MM-dd");
    	patternMap.put("[0-9]{13}", "time");
    	if(dateStr!=null&&dateStr.trim().length()>0){
    		for (Map.Entry<String, String> entry : patternMap.entrySet()) {
	    		String key = entry.getKey();
	    		String value = entry.getValue();
	    		Pattern compile = Pattern.compile(key);
	    		Matcher matcher = compile.matcher(dateStr);
	    		if(matcher.matches()){
	    			if(value.equals("time")){
	    				Date date = new Date();
	    				date.setTime(Long.valueOf(dateStr));
	    				return date;
	    			}else{
	    				SimpleDateFormat format = new SimpleDateFormat(value); 
	        			try {  
	        				Date date = format.parse(dateStr);
	        				Calendar cal = Calendar.getInstance();
	        		        cal.setTime(date);//date 换成已经已知的Date对象
	        		        if(!value.equals("yyyy-MM-dd")){
	        		        	cal.add(Calendar.HOUR_OF_DAY, +8);// after 8 hour
	        		        }
	        				return cal.getTime();  
	        			} catch (ParseException e) {  
	        				e.printStackTrace();  
	        			}  
	    			}
	    		}
			}
    	}
    	return null;
    }
    
    /**
     * 根据经纬度 半径  计算最大/最小经纬度
     * @param lon 经度  12.34567
     * @param lat 纬度 12.34567
     * @param raidus 半径（米）
     * @return
     */
    public static Map<String,Double> getAround(double lon,double lat,int raidus){  
    	Double latitude = lat;  
        Double longitude = lon;  
          
        Double degree = (24901*1609)/360.0;  
        double raidusMile = raidus;  
          
        Double dpmLat = 1/degree;  
        Double radiusLat = dpmLat*raidusMile;  
        Double minLat = latitude - radiusLat;  
        Double maxLat = latitude + radiusLat;  
          
        Double mpdLng = degree*Math.cos(latitude * (Math.PI/180));  
        Double dpmLng = 1 / mpdLng;  
        Double radiusLng = dpmLng*raidusMile;  
        Double minLon = longitude - radiusLng;  
        Double maxLon = longitude + radiusLng;
        
        Map<String,Double> map = new HashMap<String,Double>();
        map.put("maxLon", maxLon);
        map.put("minLon", minLon);
        map.put("maxLat", maxLat);
        map.put("minLat", minLat);
        return map;  
    }  
    
    public static String getContent(InputStream content, String charset) {
		StringBuffer sbuf = new StringBuffer();
		int ch;
		try {
			BufferedReader bufferedReader = new BufferedReader(
					new InputStreamReader(content, charset));
			while ((ch = bufferedReader.read()) != -1) {
				sbuf.append((char) ch + bufferedReader.readLine());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sbuf.toString();
	}

	public static String ListToString(List<String> acTypeList, String split) {
		// TODO Auto-generated method stub
		String _split = "";
		StringBuffer sbuf = new StringBuffer();
		if(acTypeList!=null&&acTypeList.size()>0){
			for (String acType : acTypeList) {
				sbuf.append(_split);
				sbuf.append(acType);
				_split = split;
			}
		}
		return sbuf.toString();
	}

    public static String uppercaseFirstLetter(String str) {
        if(str != null && !str.isEmpty()) {
            str = str.substring(0,1).toUpperCase() + str.substring(1,str.length());
        }
        return str;
    }


}
