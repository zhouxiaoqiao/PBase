//package org.moon.common.util;
//
//import javax.servlet.ServletContext;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import com.ming.webreport.MREngine;
//import com.mysql.jdbc.ResultSet;
//
///**
// * <b>版权信息 :</b> 2012，云技术有限公司<br/>
// * <b>功能描述 :</b> <br/>
// * <b>版本历史 :</b> <br/>
// * @author 周小桥| 2014-6-27 上午9:11:12 | 创建
// */
//public class ReportUtil
//{
//	/**
//	 * @param reportName
//	 * @param dsName
//	 * @param rs
//	 * @param httpServletRequest
//	 * @param httpServletResponse
//	 * @throws Exception
//	 * @author 周小桥 |2014-6-26 下午5:40:57
//	 * @version 0.1
//	 */
//	public static void excuteWebMingPrint(String reportName, String dsName,
//			ResultSet rs, HttpServletRequest httpServletRequest,
//			HttpServletResponse httpServletResponse,
//			ServletContext servletContext) throws Exception
//	{
//		if (reportName == null || reportName.trim().equals(""))
//			throw new UnsupportedOperationException(
//					"\u62A5\u8868\u540D\u79F0\u4E0D\u80FD\u4E3A\u7A7A!");
//		if (dsName == null || dsName.trim().equals("") || rs == null)
//			throw new UnsupportedOperationException(
//					"\u6570\u636E\u96C6\u4E0D\u80FD\u4E3A\u7A7A!");
//		MREngine engine = null;
//		try
//		{
//
//			engine = new MREngine(httpServletRequest, httpServletResponse,
//					servletContext);
//
//		} catch (Exception e)
//		{
//			System.err
//					.println("\u6839\u636E\u6570\u636E\u96C6\u751F\u6210\u5982\u610F\u62A5\u8868\u51FA\u9519\uFF1A"
//							+ e.getMessage());
//
//		}
//		engine.addDataSet(dsName, rs);
//		byte mingData[] = engine.bind(reportName);
//		httpServletRequest.setAttribute("mingData", mingData);
//	}
//}
