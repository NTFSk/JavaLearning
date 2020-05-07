package com.ischoolbar.programmer.util;

import java.util.Date;
import java.util.List;

/**
 * 实用工具类
 * @author Crspin
 *
 */
public class StringUtil {
	/**
	 * 将给定的list按照指定的分隔符拼凑成字符串返回
	 * @param list
	 * @param spilit
	 * @return
	 */
	public static String joinString(List<Long> list, String spilit) {
		String ret = "";
		for(Long o:list) {
			ret += o + spilit;
		}
		if(!"".equals(ret)) {
			ret = ret.substring(0, ret.length() - spilit.length());
		}
		
		return ret;
	}
	
	/**
	 * 生成学号的方法
	 * @param prefix
	 * @param suffix
	 * @return
	 */
	public static String generateSn(String prefix, String suffix) {
		
		return prefix + new Date().getTime() + suffix;
		
	}
}
