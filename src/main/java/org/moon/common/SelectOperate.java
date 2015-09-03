package org.moon.common;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import org.moon.common.db.MDBTool;
import org.springframework.stereotype.Service;
@Service
public class SelectOperate {
	 private MDBTool geneDao=new MDBTool();

	/**
	 * ���ȱ��ԭ�����2011/4/2��С��
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public String getFaultClassExcel(String excelValue)throws Exception  {	
		String sql="SELECT p.xl_id,p.xl_name FROM dw_dim_gddw_pdqxyyxl p";
		List depts=new ArrayList();
		List keys=new ArrayList<String>();
		List values=new ArrayList<String>();
		String value="0";
		if(excelValue!=null&&!"".equals(excelValue)){
		 excelValue=excelValue.trim();
		 try{
			depts=this.getSelectData(sql,"XL_NAME","XL_ID");
			keys=(List)depts.get(0);
			values=(List)depts.get(1);
			for(int i=0;i<keys.size();i++){
				String key=(String)keys.get(i);
				key=key.trim();
				if(excelValue.equals(key)) {
					value=(String)values.get(i);
					break;
				}
			}
		  }catch(Exception e){

		 }
	  }		return value;
	  
	}
	public String getFaultClassChina(String xl_id){
		String sql="SELECT p.xl_name FROM dw_dim_gddw_pdqxyyxl p where p.xl_id='"+xl_id+"'";	
		String value="0";
		 try{
			 ResultSet rs=geneDao.executeQuery(sql,null);	
			 if(rs.next()){
				 value=rs.getString("xl_name");			
				 }
		  }catch(Exception e){
			 e.printStackTrace(); 
	       }		
		  return value;
		
	}
	/**
	 * ���ȱ�����2011/4/2��С��
	 * @param request
	 * @param response
	 * @throws Exception
	*/
	@SuppressWarnings("rawtypes")
	public String getDeviceTypeExcel(String excelValue,String type)throws Exception  {
		List depts=new ArrayList();
		String sql="SELECT z.sbxl_id,z.sbxl_name FROM dw_dim_gddw_zysblx z where  z.type<>'N' and  z.type like'%"+type+"%' order by sbxl_id ";
		List keys=new ArrayList<String>();
		List values=new ArrayList<String>();
		String value="0";
		if(excelValue!=null&&!"".equals(excelValue)){
		 excelValue=excelValue.trim();
		 try{
			depts=this.getSelectData(sql,"sbxl_name","sbxl_id");
			keys=(List)depts.get(0);
			values=(List)depts.get(1);
			for(int i=0;i<keys.size();i++){
				String key=(String)keys.get(i);
				key=key.trim();
				if(excelValue.equals(key)) {
					value=(String)values.get(i);
					break;
				}
			}
		  }catch(Exception e){
           e.printStackTrace();
		 }
	  }		return value;
	  
	}	
	
	public String getDeviceTypeChina(String sbxl_id,String type){
		String sql="SELECT z.sbxl_name FROM dw_dim_gddw_zysblx z where sbxl_id="+sbxl_id+" and  z.type<>'N' and  z.type like'%"+type+"%' order by sbxl_id ";	
		String value="0";
		 try{
			 ResultSet rs=geneDao.executeQuery(sql,null);		 
			if(rs.next()){
				 value=rs.getString("sbxl_name");	
			 }				
		  }catch(Exception e){
			 e.printStackTrace(); 
	       }		
		  return value;
		
	}
	/**
	 * ����豸����2011/4/2��С��
	 * @param request
	 * @param response
	 * @throws Exception
	*/
	@SuppressWarnings("rawtypes")
	public String getDamageDeviceExcel(String excelValue)throws Exception  {
		List depts=new ArrayList();
		String sql="SELECT z.sbxl_id,z.sbxl_name FROM dw_dim_gddw_zysblx z where  z.type is not null";
		List keys=new ArrayList<String>();
		List values=new ArrayList<String>();
		String value="0";
		if(excelValue!=null&&!"".equals(excelValue)){
		 excelValue=excelValue.trim();
		 try{
			depts=this.getSelectData(sql,"sbxl_name","sbxl_id");
			keys=(List)depts.get(0);
			values=(List)depts.get(1);
			for(int i=0;i<keys.size();i++){
				String key=(String)keys.get(i);
				key=key.trim();
				if(excelValue.equals(key)) {
					value=(String)values.get(i);
					break;
				}
			}
		  }catch(Exception e){
			e.printStackTrace();
		 }
	  }		return value;
	}	
	/**
	 * ��ñ�������2011/4/8��С��
	 * @param request
	 * @param response
	 * @throws Exception
	*/
	@SuppressWarnings("rawtypes")
	public String getProtectTypeExcel(String excelValue)throws Exception {
	List depts=new ArrayList();
	String sql="select dim_id ,dim_name from dw_dim_gddw_bfdzlx dim_id";
    List keys=new ArrayList<String>();
	List values=new ArrayList<String>();
	String value="0";
	if(excelValue!=null&&!"".equals(excelValue)){
	 excelValue=excelValue.trim();
	 try{
		depts=this.getSelectData(sql,"dim_name","dim_id");
		keys=(List)depts.get(0);
		values=(List)depts.get(1);
		for(int i=0;i<keys.size();i++){
			String key=(String)keys.get(i);
			key=key.trim();
			if(excelValue.equals(key)) {
				value=(String)values.get(i);
				break;
			}
		}
	  }catch(Exception e){
		e.printStackTrace();
	    }
       }	
	  return value;
    }
	  

	/**
	 * ��÷����ֶ�2011/4/2��С��
	 * @param request
	 * @param response
	 * @throws Exception
	*/
	public String getDefectWayExcel(String excelValue)throws Exception  {
		String value="0";
		if(excelValue!=null&&!"".equals(excelValue)){
		 excelValue=excelValue.trim();
		 try{
			 String sql="select dim_id,dim_name from dw_dim_gddw_qxfxsd where dim_name='"+excelValue+"'";
			 ResultSet rs=geneDao.executeQuery(sql,null);	
			 if(rs.next()){
				value=rs.getString("dim_id");		
			  }
			}
		  catch(Exception e){
			  e.printStackTrace();
		   }
	     }	
		return value;
	}
	
	public String getDefectWayChina(String dim_id){
		String sql="select dim_id,dim_name from dw_dim_gddw_qxfxsd where dim_id='"+dim_id+"'";
		String value="0";
		 try{
			 ResultSet rs=geneDao.executeQuery(sql,null);	
			 if(rs.next()){
				value=rs.getString("dim_name");		
			 }	
			
		  }catch(Exception e){
			 e.printStackTrace(); 
	       }		
		  return value;
	}
	
	/**
	 * ����غ���� 2011/4/2��С��
	 * @param request
	 * @param response
	 * @throws Exception
	*/
	public String getMatchSucExcel(String excelValue)throws Exception  {
		String value="0";
		if(excelValue!=null&&!"".equals(excelValue)){
		 excelValue=excelValue.trim();
		 try{
			 String sql="  select s.device_id  from  dw_dim_gddw_device s where s.device_name='"+excelValue+"'";
			 ResultSet	rows = geneDao.executeQuery(sql,null);	
			 rows.next();
			 value=rows.getString("device_id");			
		   }
		 catch(Exception e){
			  e.printStackTrace();
		    }
		    return value;
	      }	
		else return "��";
		
	}
	/**
	 * ��÷����ֶ�2011/4/2��С��
	 * @param request
	 * @param response
	 * @throws Exception
	*/
	@SuppressWarnings("rawtypes")
	public String getHappenLocationExcel(String excelValue)throws Exception  {
		List depts=new ArrayList();
		String sql="SELECT z.sbxl_id,z.sbxl_name FROM dw_dim_gddw_zysblx z where  z.type is not null";
        List keys=new ArrayList<String>();
		List values=new ArrayList<String>();
		String value="0";
		if(excelValue!=null&&!"".equals(excelValue)){
		 excelValue=excelValue.trim();
		 try{
			depts=this.getSelectData(sql,"sbxl_name","sbxl_id");
			keys=(List)depts.get(0);
			values=(List)depts.get(1);
			for(int i=0;i<keys.size();i++){
				String key=(String)keys.get(i);
				key=key.trim();
				if(excelValue.equals(key)) {
					value=(String)values.get(i);
					break;
				}
			}
		  }catch(Exception e){
			  e.printStackTrace();
		 }
	  }		return value;
	}	
	 
	/*
	  * �����-�����/2011/04/02��С��
	  * 
	  */
	 @SuppressWarnings({ "rawtypes", "unchecked" })
	public List getSelectData(String sql,String keyName,String KeyValue) throws Exception{
			List selects = new ArrayList();
				ResultSet	rows = geneDao.executeQuery(sql,null);
				List keys = new ArrayList();
				List values = new ArrayList();
				while(rows.next()){		
					String key = rows.getString(keyName);
					String value = rows.getString(KeyValue);
					if(key!=null){
						key=key.trim();
					}
					if(value!=null){
						value=value.trim();
					}
					keys.add(key);
					values.add(value);
				} 
				selects.add(keys);
				selects.add(values);
				return selects;		
	 }
  /*
	  * �����-�����/2011/04/02��С��
	  * 
	  */
	 @SuppressWarnings({ "rawtypes", "unchecked" })
	public List getSelectData(String sql) throws Exception{
			List selects = new ArrayList();
				ResultSet	rows = geneDao.executeQuery(sql,null);
				List keys = new ArrayList();
				List values = new ArrayList();
				while(rows.next()){		
					String key = rows.getString(1);//��-������
					String value = rows.getString(2);//��-����ֵ
					if(key!=null){
						key=key.trim();
					}
					if(value!=null){
						value=value.trim();
					}
					keys.add(key);
					values.add(value);
				} 
				selects.add(keys);
				selects.add(values);
				return selects;		
	 }
	 /*
		 * 1�����,0����--��С��2011/04/02
	 */
	  public String getYesNo(String chinese){
				if (chinese!=null&&!"".equals(chinese)){
					chinese=chinese.trim();
					if(chinese.contains("��"))					
						 return "1";					 				 
					else 
											
						return "0";
				  }					
				return "0";
			}
		 /*
		 * 1�����,0����--��С��2011/04/02
	 */
	  public String getFaultTypeExcel(String chinese){
		 String value="";
		 if(chinese!=null&&!"".equals(chinese)){
			 chinese=chinese.trim();
			 if("��ȱ��".equals(chinese)){
					value="J";
				}
				else if("�ش�ȱ��".equals(chinese)) 
					  value="Z";
				else  value="Y";
		 }
		 
			else  value="Y";			
	    return value ;
    }
 /*
 * ������-��
 */
		 public String getSelect(String name) throws SQLException {
				String optins="<select id="+name+">";
		
				optins+="</select>";
				return optins;
			}  
	/*
	 * ��ʼ����-�����
	 */	 
	 @SuppressWarnings({ "rawtypes", "unchecked" })
	public Map  initSelect(String   sql) { 
		      Map	map=new TreeMap();
		      try{
		    	  ResultSet rs=geneDao.executeQuery(sql, null);
		    		while(rs.next()){  			
		    			map.put(String.valueOf(rs.getString(2)), rs.getString(1));
		    		}
		      }
			catch(Exception e){
			  	e.printStackTrace();
			}	  

				return map;		 
		 }

	 
 
 }
