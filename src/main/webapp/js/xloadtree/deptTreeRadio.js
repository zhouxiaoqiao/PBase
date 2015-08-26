var prjPath = getBasePath();
webFXTreeConfig.rootIcon		= prjPath+"/js/xloadtree/images/xp/folder.png";
webFXTreeConfig.openRootIcon	= prjPath+"/js/xloadtree/images/xp/openfolder.png";
webFXTreeConfig.folderIcon		= prjPath+"/js/xloadtree/images/xp/folder.png";
webFXTreeConfig.openFolderIcon	= prjPath+"/js/xloadtree/images/xp/openfolder.png";
webFXTreeConfig.fileIcon		= prjPath+"/js/xloadtree/images/xp/file.png";
webFXTreeConfig.lMinusIcon		= prjPath+"/js/xloadtree/images/xp/Lminus.png";
webFXTreeConfig.lPlusIcon		= prjPath+"/js/xloadtree/images/xp/Lplus.png";
webFXTreeConfig.tMinusIcon		= prjPath+"/js/xloadtree/images/xp/Tminus.png";
webFXTreeConfig.tPlusIcon		= prjPath+"/js/xloadtree/images/xp/Tplus.png";
webFXTreeConfig.iIcon			= prjPath+"/js/xloadtree/images/xp/I.png";
webFXTreeConfig.lIcon			= prjPath+"/js/xloadtree/images/xp/L.png";
webFXTreeConfig.tIcon			= prjPath+"/js/xloadtree/images/xp/T.png";
webFXTreeConfig.blankIcon       = prjPath+"/js/xloadtree/images/blank.png";
webFXTreeConfig.type            = "radio"; //checkbox or radio

var actionSuffix = '.action';
var namespace = '/Controller';
var loadRootNodeAction = 'loadRootNode_DeptTree';
var initAction = 'init_DeptTree';
var addNodeAction = 'addNode_DeptTree';
var saveOrUpdateRootNodeAction = 'saveOrUpdateRootNode_DeptTree';
var deleteNodeAction = 'deleteNode_DeptTree';
var loadChildNodeAction = 'loadChildNode_DeptTree';
var updateNodeAction = 'updateNode_DeptTree';
var loadTreeUrl = prjPath + namespace+'/' + loadRootNodeAction + actionSuffix;
var loadChildNodeUrl = prjPath + namespace+'/' + loadChildNodeAction + actionSuffix;
var initTreeUrl = prjPath + namespace+'/' + initAction + actionSuffix;
var addNodeUrl = prjPath + namespace+'/' + addNodeAction + actionSuffix;
var saveOrUpdateRootNodeUrl = prjPath + namespace+'/' + saveOrUpdateRootNodeAction + actionSuffix;
var deleteNodeUrl = prjPath + namespace+'/' + deleteNodeAction + actionSuffix;
var updateNodeUrl = prjPath + namespace+'/' + updateNodeAction + actionSuffix;
var tree;

$(function(){
	loadTree();
	
});

function showA(){
	var c  = arguments[0] + arguments[1]+2;
	$("#con").append(c);	
}

function loadTree(){
	$.ajax({
		url:loadTreeUrl,
		dataType:'json',
		success:function(rootNode){
			var id = rootNode.id;
			 
			if(id != '1'){
				updateTree(rootNode);
			}else{
				$('#rootId').html('null');
				$('#rootParent_org').html('null');
			}
		}
	});
}

function updateTree(rootNode){
	$('#rootText').val(rootNode.name);
	$('#rootAction').val(rootNode.action);
	$('#rootId').html(rootNode.id);
	$('#rootParent_org').html('null');
	tree = new WebFXLoadTree(rootNode.name,initTreeUrl,rootNode.action);
	tree.data = rootNode;
	$('#radioDiv').html(tree.toString());
}



function addNode(){
	var selectNode = tree.selectNode;
	if(selectNode==null){
		alert('选中一个节点进行新增操作!');
		return;
	}
	var treeNode = new Object();
	treeNode.name = $("#title").val();
	if($("#url").val()=='')
		treeNode.url ='javascript:void(0);';
	else
	    treeNode.url = $("#url").val();
	treeNode.parent_org = $("#parent_org").val();

	if(treeNode.name==''){
		alert('title必须填写!');
		return;
	}
	
	$.ajax({
		type:'POST',
		url:addNodeUrl,
		data:treeNode,
		dataType:'json',
		success:function(newNode){
			//直接添加即可
			selectNode.add(new WebFXTreeItem({
				text:newNode.name,
				action:newNode.url,
				data:newNode
			}));
			selectNode.src = newNode.src;
			selectNode.expand();
			//清空内容
			$("#title").val('');
			$("#url").val('');
		}
	});
}

function saveOrUpdateRootNode(){
	var treeNode = new Object();
	treeNode.name = $("#rootText").val();
	treeNode.action = $("#rootAction").val();
	treeNode.id = $("#rootId").html();
	if(treeNode.name==''){
		alert('name不能为空');
		$("#rootText").focus();
		return;
	}
	if(treeNode.id == 'null')treeNode.id = -1;
	$.ajax({
		type:'POST',
		url:saveOrUpdateRootNodeUrl,
		data:treeNode,
		dataType:'json',
		success:function(rootNode){
			$("#"+tree.id+"-anchor").html(rootNode.text);
		}
	});
}

function flushNode(){
	var selectNode = tree.selectNode;
	if(selectNode==null){
		alert('选中一个节点进行刷新操作!');
		return;
	}
	if(selectNode.childNodes.length!=0){
		if(selectNode.reload){
			selectNode.reload();
		}else{
			var parentNode = selectNode.parentNode;
		 
			if(parentNode){
				parentNode.reload();
			}
		}	
	}
}

function delNode(){
	var selectNode = tree.selectNode;
	if(selectNode==null){
		alert('选中一个节点进行删除操作!');
		return;
	}
	var data = selectNode.data;
	if(typeof(data) == 'string'){
		data = jQuery.parseJSON(data);
	}
	$.ajax({
		type:'POST',
		url:deleteNodeUrl,
		data:{id:data.id},
		success:function(result){
			if(Number(result)!=0){
				selectNode.remove();
				tree.selectNode = null;
			}
		}
	});
}

function editNode(){
	var selectNode = tree.selectNode;
	if(selectNode==null){
		alert('选中一个节点进行编辑操作!');
		return;
	}
	var data = selectNode.data;
	if(typeof(data) == 'string'){
		data = jQuery.parseJSON(data);
	}
	var treeNode = new Object();
	treeNode.name = $("#title").val();
	if($("#url").val()=='')
		treeNode.url ='javascript:void(0);';
	else
	    treeNode.url = $("#url").val();
	treeNode.id = data.id;
	if(treeNode.name==''){
		alert('菜单名不能为空');
		$("#title").focus();
		return;
	}
	$.ajax({
		type:'POST',
		url:updateNodeUrl,
		data:treeNode,
		dataType:'json',
		success:function(updateNode){
			$("#"+selectNode.id+"-anchor").html(updateNode.title);
			$("#"+selectNode.id+"-anchor").attr('href',updateNode.url);
			selectNode.data = updateNode;
		}
	});
	
}