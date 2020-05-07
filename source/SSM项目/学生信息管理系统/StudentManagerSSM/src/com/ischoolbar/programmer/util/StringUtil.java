package com.ischoolbar.programmer.util;

import java.util.Date;
import java.util.List;

/**
 * ʵ�ù�����
 * @author Crspin
 *
 */
public class StringUtil {
	/**
	 * ��������list����ָ���ķָ���ƴ�ճ��ַ�������
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
	 * ����ѧ�ŵķ���
	 * @param prefix
	 * @param suffix
	 * @return
	 */
	public static String generateSn(String prefix, String suffix) {
		
		return prefix + new Date().getTime() + suffix;
		
	}
}
