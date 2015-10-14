<%@ page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
  
<head>
 <%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<title>JS Bin</title>

<style>
  article, aside, figure, footer, header, hgroup, 
  menu, nav, section { display: block; }
  .west{
    width:200px;
    padding:10px;
  }
  .north{
    height:100px;
  }
</style>
</head>
<script type="text/javascript">
$(function(){	 
    //动态菜单数据
  var user_id=window.parent.document.getElementById("user_id").value;
    var treeData  ;
      $(function() {
		$.ajax({  
        async:false,  
        type:"POST",  
        url:"<%=basePath%>/sysAdmin/main_getMenuTree.action?user_id="+user_id,  
        dataType:"json",  
        cache: false,
        success:function(datas){
 
        	if(datas){ 
        	 
        		 treeData=datas;
        		
        		//实例化树形菜单
        	    $("#tree").tree({
        	        data : treeData,
        	        lines : true,
        	        onClick : function (node) {
        	            if (node.url) {        	             
        	                 Open(node.text, node.url,node.action);
        	            }
        	        }
        	    });
        	}
        }
    }); 
	});
    
    //在右边center区域打开菜单，新增tab
    
    
    //绑定tabs的右键菜单
    $("#tabs").tabs({
        onContextMenu : function (e, title) {
            e.preventDefault();
            $('#tabsMenu').menu('show', {
                left : e.pageX,
                top : e.pageY
            }).data("tabTitle", title);
        }
    });
    
    //实例化menu的onClick事件
    $("#tabsMenu").menu({
        onClick : function (item) {
            CloseTab(this, item.name);
        }
    });
    
    //几个关闭事件的实现
    function CloseTab(menu, type) {
        var curTabTitle = $(menu).data("tabTitle");
        var tabs = $("#tabs");
        
        if (type === "close") {
            tabs.tabs("close", curTabTitle);
            return;
        }
        
        var allTabs = tabs.tabs("tabs");
        var closeTabsTitle = [];
        
        $.each(allTabs, function () {
            var opt = $(this).panel("options");
            if (opt.closable && opt.title != curTabTitle && type === "Other") {
                closeTabsTitle.push(opt.title);
            } else if (opt.closable && type === "All") {
                closeTabsTitle.push(opt.title);
            }
        });
        
        for (var i = 0; i < closeTabsTitle.length; i++) {
            tabs.tabs("close", closeTabsTitle[i]);
        }
    }
});
</script>

<body class="easyui-layout">

 <div class="easyui-panel" title="菜单" data-options="iconCls:'icon-save',border:false,fit:true">
	<div id="view_layout_west_accordion" class="easyui-accordion"   data-options="iconCls:'icon-save',fit:true,border:false">
	 <ul id="tree"></ul>
	</div>
</div>
</body>
</html>
