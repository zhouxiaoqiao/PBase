function checkFile(obj){
	var filePath=$(obj).attr('value');
	if(filePath.length<=0){
		alert("请选择文件！");
		return false;
	}
	var lastIndex = filePath.lastIndexOf(".");
	var filePrefix = filePath.substring(lastIndex+1,filePath.length);
	if(filePrefix!='xls'){
		alert("文件格式错误！应为.xls文件");
		return false;
	}
	return true;
}
function importExcelFile(){
	var obj = $('#excelFile');
	if(checkFile(obj)){
		return true;
	}else{
		return false;
	}
}