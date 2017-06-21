package org.smartmvc.configuration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.smartmvc.core.exception.ExceptionHandler;

public class MyExceptionHandler implements ExceptionHandler {

	@Override
	public void resolveException(HttpServletRequest request, HttpServletResponse response, Exception e) {
		System.out.println("ȫ���쳣����--------------------------------------");
		e.printStackTrace();
	}

}
