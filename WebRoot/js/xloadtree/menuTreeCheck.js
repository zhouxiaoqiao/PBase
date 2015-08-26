var prjPath =getBasePath();
webFXTreeConfig.rootIcon = prjPath + "/js/xloadtree/images/xp/folder.png";
webFXTreeConfig.openRootIcon = prjPath
		+ "/js/xloadtree/images/xp/openfolder.png";
webFXTreeConfig.folderIcon = prjPath + "/js/xloadtree/images/xp/folder.png";
webFXTreeConfig.openFolderIcon = prjPath
		+ "/js/xloadtree/images/xp/openfolder.png";
webFXTreeConfig.fileIcon = prjPath + "/js/xloadtree/images/xp/file.png";
webFXTreeConfig.lMinusIcon = prjPath + "/js/xloadtree/images/xp/Lminus.png";
webFXTreeConfig.lPlusIcon = prjPath + "/js/xloadtree/images/xp/Lplus.png";
webFXTreeConfig.tMinusIcon = prjPath + "/js/xloadtree/images/xp/Tminus.png";
webFXTreeConfig.tPlusIcon = prjPath + "/js/xloadtree/images/xp/Tplus.png";
webFXTreeConfig.iIcon = prjPath + "/js/xloadtree/images/xp/I.png";
webFXTreeConfig.lIcon = prjPath + "/js/xloadtree/images/xp/L.png";
webFXTreeConfig.tIcon = prjPath + "/js/xloadtree/images/xp/T.png";
webFXTreeConfig.blankIcon = prjPath + "/js/xloadtree/images/blank.png";
webFXTreeConfig.type = "checkbox"; // checkbox or radio

var checkedBoxIDs = '';
var uncheckedBoxIDs = '';
var actionSuffix = '.action';
var namespace = '/Controller';
var loadRootNodeAction = 'loadRootNode_MenuTree';
var initAction = 'init_MenuTree';
var loadChildNodeAction = 'loadChildNode_MenuTree';
var saveUserRightAction = 'saveUserRight_MenuTree';
var loadTreeUrl = prjPath + namespace + '/' + loadRootNodeAction + actionSuffix;
var initTreeUrl = prjPath + namespace + '/' + initAction + actionSuffix;
var saveUserRightUrl = prjPath + namespace + '/' + saveUserRightAction+ actionSuffix;
var tree;


function showA() {
	var c = arguments[0] + arguments[1] + 2;
	$("#con").append(c);
}

function loadTree() {
	$.ajax({
		url : loadTreeUrl,
		dataType : 'json',
		success : function(rootNode) {
			var id = rootNode.id;
			if (id != '1') {
				updateTree(rootNode);
			} else {
				$('#rootId').html('null');
				$('#rootPid').html('null');
			}
		}
	});
	
}

function updateTree(rootNode) {
	$('#rootText').val(rootNode.title);
	$('#rootAction').val(rootNode.action);
	$('#rootId').html(rootNode.id);
	$('#rootPid').html('null');
	tree = new WebFXLoadTree(rootNode.title, initTreeUrl, rootNode.action);
	tree.data = rootNode;
	$('#checkDiv').html(tree.toString());

}

function selectRight(data, checked) {

	if (typeof (data) == 'string') {
		data = jQuery.parseJSON(data);
		if (checked) {
			if (checkedBoxIDs == '')
				checkedBoxIDs = '' + data.id;
			else if (!stringIsInclude(checkedBoxIDs, '' + data.id, ',')) {
				checkedBoxIDs = checkedBoxIDs + ',' + data.id;

			}
			if (uncheckedBoxIDs.indexOf(',', 0) < 0)
				uncheckedBoxIDs = uncheckedBoxIDs.replace('' + data.id, '');
			else {
				uncheckedBoxIDs = uncheckedBoxIDs.replace(',' + data.id, '');
				uncheckedBoxIDs = uncheckedBoxIDs.replace(data.id + ",", '');
			}

		} else {

			if (uncheckedBoxIDs == '')
				uncheckedBoxIDs = '' + data.id;
			else if (!stringIsInclude(uncheckedBoxIDs, '' + data.id, ','))
				uncheckedBoxIDs = uncheckedBoxIDs + ',' + data.id;

			if (checkedBoxIDs.indexOf(',', 0) < 0)
				checkedBoxIDs = checkedBoxIDs.replace('' + data.id, '');
			else {
				checkedBoxIDs = checkedBoxIDs.replace(',' + data.id, '');
				checkedBoxIDs = checkedBoxIDs.replace(data.id + ",", '');
			}
		}

	}

}

function saveUserRightNode() {
	var saveUserUrl = saveUserRightUrl + "?checkedBoxIDs=" + checkedBoxIDs
			+ "&uncheckedBoxIDs=" + uncheckedBoxIDs+"&user_id="+user_id;
	$.ajax({
		url : saveUserUrl,
		dataType : 'json',
		success : function(msg) {
			alert(msg.success);
		}
	});
	checkedBoxIDs='';
	uncheckedBoxIDs='';
}
function JSRefresh() {
	location.reload();
}
function stringIsInclude(str, str_incude, separator) {

	var ret = false;
	if (str != '' && str_incude != '' && separator != '') {
		var str_a = str.split(separator);
		for ( var i = 0; i < str_a.length; i++)
			if (str_a[i] == str_incude)
				return true;
	}
	return ret;
}