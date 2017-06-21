package org.smartmvc.core.exception;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 统一的异常处理接口
 * @author ZHANGYUKUN
 *
 */
public interface ExceptionHandler {

	 void resolveException(HttpServletRequest request, HttpServletResponse response,Exception e);
}