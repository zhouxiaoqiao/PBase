package com.yunyuan.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class FileAdminUtil
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
	public boolean uploadFile(HttpServletRequest request, File upload_file, String fileName) throws ServletException,
			IOException
	{

		// 获取文件需要上传到的路径
		String path = request.getRealPath("/upload");
		path = path + "\\" + fileName;
		boolean ret = false;
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
			ret = true;
		}

		catch (Exception e)
		{
			// TODO Auto-generated catch block

			e.printStackTrace();
		}

		return ret;

	}

	/**
	 * 
	 * @param request
	 * @param down_local_path
	 * @param fileName
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 * @author 周小桥 |2015-9-30 下午2:56:19
	 * @version 0.1
	 */
	@SuppressWarnings("deprecation")
	public boolean downloadFile(HttpServletRequest request, HttpServletResponse response, String fileName)

	{

		// 获取文件需要上传到的路径
		String down_path = request.getRealPath("/");

		down_path = down_path + "\\down_file/" + fileName;
		boolean ret = false;
		FileInputStream fis = null;
		OutputStream out = null;
		try
		{
			response.setHeader("Content-disposition", "attachment;filename=" + fileName);
			fis = new FileInputStream(down_path);
			out = response.getOutputStream(); //new FileOutputStream(down_local_path + "/" + fileName);
			byte[] buffer = new byte[1024];
			int len = 0;
			while ((len = fis.read(buffer)) > 0)
			{
				out.write(buffer, 0, len);
			}

			out.flush();

			ret = true;
		}

		catch (Exception e)
		{
			// TODO Auto-generated catch block

			e.printStackTrace();
		}
		finally
		{
			try
			{
				if (fis != null)
					fis.close();
				if (out != null)
					out.close();
			}
			catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		return ret;

	}

}
