package org.moon.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
	public static SimpleDateFormat dateFormat_StringToDate = new SimpleDateFormat("yyyy-MM-dd");
	public static SimpleDateFormat dateFormat_DateToString = new SimpleDateFormat("yyyy��M��");
	/**
	 * ���ַ�ת��������
	 * @param {date String} �����ַ�
	 * @return ������������yyyy-MM-dd
	 */
	public static Date StringToDate(String date) {
		if (date == null || date.equals("")||date.trim().length()<5)
			return null;
		java.util.Date newStartTime = null;
		try {

			if(date.length()<8){//����ֻ������
				newStartTime = dateFormat_StringToDate.parse(date+"-01");
			}
			else newStartTime = dateFormat_StringToDate.parse(date);
		} catch (ParseException e) {

			e.printStackTrace();
		}

		return newStartTime;
	}
	/**
	 * ������ת�����ַ�
	 * @param  {date Date} ����
	 * @return ���ظ�ʽΪ"yyyy��M��"
	 */
	public static String dateToString(Date d) {

		if (d == null)
			return "";
		return dateFormat_DateToString.format(d);
	}

	/**
	 * ������ת�����ַ�
	 * @param {date Date} ����
	 * @return ���ظ�ʽΪ"yyyy-MM-dd"
	 */
	public static String dateToStringToView(Date d) {

		if (d == null)
			return "";
		return dateFormat_StringToDate.format(d);
	}
	
	//У������,���ڸ�ʽ��yyyy-mm-dd
	public static boolean checkDate(String date) {
        if (date == null) {
            return false;
        }
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            dateFormat.setLenient(false);
            dateFormat.parse(date);
            return true;
        } catch (Exception e) {
        	return false;
        }
    }
}
