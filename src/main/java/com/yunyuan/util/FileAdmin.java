package com.yunyuan.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

public class FileAdmin
{
/**
 * 
 * @param request
 * @param upload_file
 * @param fileName
 * @return
 * @throws ServletException
 * @throws IOException
 * @author 周小桥 |2015-9-24 上午11:16:26
 * @version 0.1
 */
	@SuppressWarnings("deprecation")
	public static boolean uploadFile(HttpServletRequest request, File upload_file, String fileName)
			throws ServletException, IOException
	{

		// 获取文件需要上传到的路径
		String path = request.getRealPath("/upload");
		path = path + "\\" + fileName;

		try
		{

			FileOutputStream fos = new FileOutputStream(path);

			FileInputStream fis = new FileInputStream(upload_file);
			byte[] buffer = new byte[1024];
			int len = 0;
			while ((len = fis.read(buffer)) > 0)
			{
				fos.write(buffer, 0, len);
			}
			fis.close();
			fos.close();

		}

		catch (Exception e)
		{
			// TODO Auto-generated catch block

			e.printStackTrace();
		}

		return true;

	}
	
	
}
