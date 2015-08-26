package org.moon.filter;
import javax.servlet.*;
import java.io.*;

/*
 * ���ı��봦�?ʹ��servlet�Ĺ��˹���
 */
public class EncodingFilter implements Filter {

	protected String encoding = null;
	protected FilterConfig filterConfig = null;

	public void destroy() {
		this.encoding = null;
		this.filterConfig = null;
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		String encoding = selectEncoding(request);

		if (encoding != null) {
			request.setCharacterEncoding(encoding);
		}

		chain.doFilter(request, response);
	}

	public void init(FilterConfig filterConfig) throws ServletException {
		this.filterConfig = filterConfig;
		this.encoding = filterConfig.getInitParameter("encoding");
	}

	protected String selectEncoding(ServletRequest request) {
		return (this.encoding);
	}
}
