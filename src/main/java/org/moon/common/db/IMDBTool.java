package org.moon.common.db;

import java.io.FileInputStream;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONObject;

/**
 * <b>版权信息 :</b> 2012，云技术有限公司<br/>
 * <b>功能描述 :</b> <br/>
 * <b>版本历史 :</b> <br/>
 * @author 周小桥| 2014-6-18 下午5:45:15 | 创建
 */
public interface IMDBTool
{
	public ResultSet executeQuery(String sql, List<?> parm) throws Exception;

	public List<JSONObject> executeJSONQuery(String sql, List<String> parm)
			throws Exception;

	public int executeUpdate(String sql, List<?> parm) throws Exception;

	public ArrayList<?> executeProcedure(String procedureName,
			List<String> inparams, List<String> outparams,
			List<String> returnparams) throws Exception;

	public int executeStreamUpdate(String sql, List<?> parm,
			FileInputStream stream) throws Exception;

	public void closeConnection() throws Exception;
}
