package org.moon.test;

import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.moon.common.db.MDBTool;
import org.moon.common.db.SQLTool;

public class TestDB
{
	MDBTool db = new MDBTool();

	@Test
	public void testProcedure()
	{

		List<String> in = new ArrayList<String>();

		try
		{
			in.add("gggg");
			System.out.println(db.executeProcedure("test_proc", in, null, null));
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testSql()
	{
		System.out.println(SQLTool.getRecuSQl("tab_dept", "pid", "dept_id", 10, false));
	}
}
