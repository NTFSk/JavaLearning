package com.ischoolbar.programmer.interceptor;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.ischoolbar.programmer.entity.User;

import net.sf.json.JSONObject;

/**
 * ��¼����������
 * @author Crspin
 *
 */
public class LoginInterceptor implements HandlerInterceptor {


	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		// TODO Auto-generated method stub

	}

	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {
		// TODO Auto-generated method stub

	}

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object arg2) throws Exception {
		// TODO Auto-generated method stub
		String url = request.getRequestURI();
		// System.out.println("������������url = " + url);
		Object user = request.getSession().getAttribute("user");
		if(user == null) {
			// δ��¼���½״̬ʧЧ
			System.out.println("δ��¼���½ʧЧ url = " + url);
			if("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) {
				/**
				 *  ����д�û���Ϣ�󣬵�����ύ����ť��ʱ��������ajax����
				 * �����޷�����������ض���
				 * ���Զ�ajax���󵥶������ж�
				 */
				// ajax����
				Map<String, String> ret = new HashMap<String, String>();
				ret.put("type","error");
				ret.put("msg", "��½״̬ʧЧ�������µ�¼��");
				response.getWriter().write(JSONObject.fromObject(ret).toString());
				return false;
			}
			// �ض��򵽵�½���棬request.getContextPath()��ȡ��Ŀ�ĸ�Ŀ¼
			response.sendRedirect(request.getContextPath() + "/system/login");
			return false;
		}
		return true;
	}

}
