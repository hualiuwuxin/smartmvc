package org.smartmvc.core;


import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.smartmvc.core.envi.RequesteEnvironmentHolder;
import org.smartmvc.core.envi.SystemConfiguration;
import org.smartmvc.core.exception.ExceptionHandler;
import org.smartmvc.core.resolver.ParameterMapResolve;

/**
 * 分发请求
 * @author ZHANGYUKUN
 *
 */
public class RequestDispatcher extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
    public RequestDispatcher() {
    	super();
    }
    
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String targetUrl =  request.getServletPath();
		if (targetUrl.startsWith("/")) {
			targetUrl = targetUrl.substring(1);
		}
		
		RequesteEnvironmentHolder.setTargetUrl( targetUrl );
		RequesteEnvironmentHolder.setRequest( request );
		RequesteEnvironmentHolder.setResponse(response);
		RequesteEnvironmentHolder.setParameterArrayMaxSubscript( ParameterMapResolve.resolveParameterMap( request.getParameterMap() ) );
		
		try {
			ActionAware.dispatche( targetUrl );
		} catch (Exception e) {
			if( SystemConfiguration.getExceptionHandler() != null ){
				ExceptionHandler exceptionHandler = SystemConfiguration.getExceptionHandler();
				exceptionHandler.resolveException(request, response, e);
			}else{
				e.printStackTrace();
				response.setStatus(500);
				PrintWriter out =  response.getWriter();
				e.printStackTrace(  out );
				out.flush();
				out.close();
			}
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	
	

}
