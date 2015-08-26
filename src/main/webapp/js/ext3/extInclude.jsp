<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

	<link rel="stylesheet" type="text/css" href='<%=basePath%>js/ext3/resources/css/ext-all.css' />
	<script type="text/javascript" src="<%=basePath%>js/ext3/adapter/ext/ext-base-debug.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/ext3/adapter/ext/ext-all-debug.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/ext3/adapter/ux/ux-all-debug.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/ext3/language/ext-lang-zh_CN.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/ext3/json-grid-demo.js"></script>
	<style type="text/css">
	.x-selectable,.x-selectable * {
		-moz-user-select: text ! important;
		-khtml-user-select: text ! important;
	}
	.x-panel-save-button {
	    background-position:0 0;
	    background-image:url(images/icon/save.gif)!important; 
	}
	.x-panel-add-button {
	    background-position:0 0;
	    background-image:url(images/add.gif)!important; 
	}
	</style>
	<script type="text/javascript">
			if  (!Ext.grid.GridView.prototype.templates) {    
			    Ext.grid.GridView.prototype.templates = {};    
			}    
			Ext.grid.GridView.prototype.templates.cell =  new  Ext.Template(    
			     '<td class="x-grid3-col x-grid3-cell x-grid3-td-{id} x-selectable {css}" style="{style}" tabIndex="0" {cellAttr}>' ,    
			     '<div class="x-grid3-cell-inner x-grid3-col-{id}" {attr}>{value}</div>' ,    
			     '</td>'    
			);  
			Ext.override(Ext.form.ComboBox,{
	
		    onViewClick : function(doFocus){
		        var index = this.view.getSelectedIndexes()[0],
		            s = this.store,
		            r = s.getAt(index);
		        if(r){
		            this.onSelect(r, index);
		        }else {
		            this.collapse();
		        }
		        if(doFocus !== false){
		            this.el.focus();
		        }
	        }
			});
			Ext.BLANK_IMAGE_URL = '<%=basePath%>js/ext3/resources/images/default/s.gif';
			var jsContextPath='<%=basePath%>';
	</script>
