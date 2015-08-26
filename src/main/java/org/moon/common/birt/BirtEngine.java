package org.moon.common.birt;

import java.util.logging.Level;

import javax.servlet.ServletContext;

import org.eclipse.birt.core.exception.BirtException;
import org.eclipse.birt.core.framework.Platform;
import org.eclipse.birt.report.engine.api.EngineConfig;
import org.eclipse.birt.report.engine.api.IReportEngine;
import org.eclipse.birt.report.engine.api.IReportEngineFactory;



///参考网址：http://www.blogjava.net/leweslove/articles/leweslove.html
/**
 * <b>版权信息 :</b> 2012，云技术有限公司<br/>
 * <b>功能描述 :</b> <br/>
 * <b>版本历史 :</b> <br/>
 * @author 周小桥| 2014-7-3 下午2:05:46 | 创建
 */
public class BirtEngine
{

	private static IReportEngine birtEngine = null;

	public static synchronized void initBirtConfig()
	{
		// loadEngineProps();
	}

	/**
	 * @param sc
	 * @return
	 * @author 周小桥 |2014-7-3 下午2:05:51
	 * @version 0.1
	 */
	public static synchronized IReportEngine getBirtEngine(ServletContext sc)
	{
		if (birtEngine == null)
		{
			EngineConfig config = new EngineConfig();
			try
			{
				String en_path = BirtEngine.class.getClassLoader()
						.getResource("").getPath();
				en_path = en_path.replace("/classes/", "/ReportEngine");

				config.setEngineHome(en_path);
				config.setLogConfig(System.getProperty("user.dir")
						+ "/logs/birt_log", Level.FINE);
				Platform.startup(config);
				// If using RE API in Eclipse/RCP application this is not
				// needed.
				IReportEngineFactory factory = (IReportEngineFactory) Platform
						.createFactoryObject(IReportEngineFactory.EXTENSION_REPORT_ENGINE_FACTORY);
				birtEngine = factory.createReportEngine(config);
				birtEngine.changeLogLevel(Level.WARNING);
			} catch (BirtException e)
			{
				e.printStackTrace();
			}

		}
		return birtEngine;
	}

	/**
	 * @author 周小桥 |2014-7-3 下午2:06:14
	 * @version 0.1
	 */
	public static synchronized void destroyBirtEngine()
	{
		if (birtEngine == null)
		{
			return;
		}
		birtEngine.destroy();
		Platform.shutdown();
		birtEngine = null;
	}

	public Object clone() throws CloneNotSupportedException
	{
		throw new CloneNotSupportedException();
	}

}
