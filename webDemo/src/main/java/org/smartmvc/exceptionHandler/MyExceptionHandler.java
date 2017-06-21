package org.smartmvc.exceptionHandler;

import java.io.IOException;
import java.io.Writer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.smartmvc.core.exception.ExceptionHandler;

public class MyExceptionHandler implements ExceptionHandler {

	@Override
	public void resolveException(HttpServletRequest request, HttpServletResponse response, Exception e) {
		System.out.println("统一异常处理--------------------------------------");
		e.printStackTrace();
		
		response.setCharacterEncoding("gbk");
		
		response.setStatus(400);
		
		
		try {
			Writer out = response.getWriter();
			if( e.getCause() != null ){
				out.write( e.getCause().getMessage() );
			}else{
				out.write( e.toString() );
			}
			out.flush();
			out.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
	}

}
