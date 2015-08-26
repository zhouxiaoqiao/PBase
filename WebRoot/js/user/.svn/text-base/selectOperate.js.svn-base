
//设置jqgrid列字段下拉数据

function setColSelect(pageId,keyName,url){
     pageId='#'+pageId; 
     var keyNameEdit=keyName+'Edit';
     var selectMap=initSelectData(url);
     $(pageId).setColProp(keyNameEdit, { editoptions: { value: selectMap} });
  }	
	//从后台获得下拉框数据
  function initSelectData(url){    
				var	params="null";
				var selects='';
					$.ajax({
						type:"POST",
						url:url,
						data:params,
						cache:false,
						async:false,
						dataType:'json',
						success: function(data,status,XHR){
							var respMsg = data.RESPCODE;
							if(respMsg!='0000'){
								alert(" Operate mistake! ");
								checkPass=false;
							}else{
								 selects = data.selects;

							}
						}
					});
           return selects;
	}
/*
	初始化下拉框选项
	 编辑前调用
	*/
	 function initOption(pageId,keyName){
	     pageId='#'+pageId;
	     var mulsel=$(pageId).getGridParam('selarrrow');
	     var keyNameEdit=keyName+'Edit';
	 	 var objSelect=  document.getElementById(keyNameEdit);  
	     objSelect.click;	
		 var selVal= $(pageId).getCell(mulsel,keyName);
		 for (var i = 0; i < objSelect.options.length; i++) {      
           if (objSelect.options[i].text == selVal) {      
             objSelect[i].selected=true;      
             break;        
            }        
         } 
	   }
   /*
     动态获得下拉框数据
	 
	*/  
	  function getSelectDataInit(object,url,sql){ 
	            url+='&sql='+sql;
				var	params="null";
					$.ajax({
						type:"POST",
						url:url,
						data:params,
						cache:false,
						async:false,
						dataType:'json',
						success: function(data,status,XHR){
							var respMsg = data.RESPCODE;
							if(respMsg!='0000'){
								alert("操作出错！");
								checkPass=false;
							}else{
								var keys = data.keys;
								var values = data.values;
								var len = keys.length;
								var msg="";
								if(len>0){
								object.length=0;
								object.click;
								for(var k=0;k<len;k++){
				                    addSelect(object,keys[k],values[k]); 

									 
									}
								}
							}
						}
					});
	 } 
	 /*
	  增加下拉框项
	 */
	  function addSelect(object,str,value){
        var selectObj =object;    
        var op = window.document.createElement("option");          
        op.value = value;                            
        op.innerHTML =str;
        selectObj.appendChild(op);
       
  } 
/*------------------------------------------------------------打开部门机构树--------------------------------------------------*/ 
//deptID,deptName存在本jsp(调用)的控件,自动赋值
 function openDeptTree(deptID,deptName){
      deptID="#"+deptID;
      deptName="#"+deptName;
	    var para   = new Array();
		var url = basePath +'/view/fixreport/deptTree.jsp';
		var sFeatures = "dialogWidth=300px;dialogHeight=500px;";		
		//弹出一个showModalDialog,并以returnValue来获取返回值
		var returnValue = window.showModalDialog(url,para,sFeatures);
		if(returnValue!=undefined && returnValue.length>0){
       	$(deptID).attr('value',returnValue[0]);
        $(deptName).attr('value',returnValue[1]);

		}
  } 
  /*-----------------------------------------------------------------给部门组建赋值---------------------------------------------------------*/ 
 //查询时选择部门 
  function selQueryDept(){	 
	   openDeptTree('deptId','deptName');
  }
  //新增时选择部门 1 
  function selAddF_Dept(){	   
	   openDeptTree('F_DEPT_ID','F_DEPT_NAME');
  }
   //新增时选择部门2  
  function selAddDept(){	   
	  openDeptTree('deptId','deptName');
  }  
    //新增时选择部门3  
  function selAddDGddwUnit(){	   
	  openDeptTree('DGddwUnit','DGddwUnitName');
  }
  //导入Excel时选择部门   
  function selExcelDept(){	   
	   openDeptTree('deptIdExcel','deptNameExcel');
  }
  //小写  
  function selLowerDept(){	 
	   openDeptTree('dgddwunit','dgddwunitname');
  }
 