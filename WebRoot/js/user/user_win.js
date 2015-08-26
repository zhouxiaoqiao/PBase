//拖拽 
var win_child;
function initUserWin() {

	// 初始化拖拽编辑窗口
	$(function() {
		// 拖拽
		dragAndDrop(".ewTop", ".editWin");
		// 初始化位置
		initPosition(".editWin");
		// 点击按钮
		clickShowBtn(".editWin");
	});

	// 初始化拖拽查找窗口
	$(function() {
		// 拖拽
		dragAndDrop(".fwTop", ".findWin");
		// 初始化位置
		initPosition(".findWin");
		// 点击按钮
		clickShowBtn(".findWin");
	});

}

function dragAndDrop(classTop, classMain) {
	var _move = false;// 移动标记
	var _x=0, _y=0;// 鼠标离控件左上角的相对位置
	$(classTop).mousedown(function(e) {
		_move = true;
		_x = e.pageX - parseInt($(classMain).css("left"));
		_y = e.pageY - parseInt($(classMain).css("top"));
		// $(".wTop").fadeTo(20,0.5);//点击开始拖动并透明显示
	});
	$(document).mousemove(function(e) {
		if (_move) {
			var x = e.pageX - _x;// 移动时鼠标位置计算控件左上角的绝对位置
			var y = e.pageY - _y;
			$(classMain).css({
				top : y,
				left : x
			});// 控件新位置
		}
	}).mouseup(function() {
		_move = false;
		// $(".wTop").fadeTo("fast",1);//松开鼠标后停止移动并恢复成不透明
	});
}
// 初始化拖拽div的位置
function initPosition(classMain) {
	// 计算初始化位置
	var itop = ($(document).height() - $(classMain).height()) / 2;
	var ileft = ($(document).width() - $(classMain).width()) / 1.8;
	// 设置被拖拽div的位置
	$(classMain).css({
		top : itop,
		left : ileft
	});
}
// 点击显示按钮
function clickShowBtn(classMain) {
	$("#show").click(function() {
		$(classMain).show(1000);
	});

	$("#hidden").click(function() {
		$(classMain).hide(1000);
	});
}
// 关闭;
function closeWin(classMain) {
	$(classMain).hide(1000);
	if(win_child!=undefined&&win_child!=null)
		   win_child.close();
}

// /提交至后台
function submitFrom(formID, url) {
	document.getElementById(formID).action = url;
 
	document.getElementById(formID).submit();

}
// 拼 where语句
function getWhere(where, whereKey) {

	var key = whereKey.replace("_find", "");// 去掉 与数据库保持一致
	if ($('#' + whereKey).val() != undefined
			&& $('#' + whereKey).val().length > 0) {
		if (where.length > 0)
			where = where + " and  " + key + "='" + $('#' + whereKey).val()
					+ "'";
		else
			where = where + " " + key + " ='" + $('#' + whereKey).val() + "'";
	}
	return where;
}

// 拼 where like语句
function getWhereLike(where, whereKey, like) {

	var key = whereKey.replace("_find", "");// 去掉 与数据库保持一致
	if ($('#' + whereKey).val() != undefined
			&& $('#' + whereKey).val().length > 0) {
		if (where.length > 0) {
			if (like)
				where = where + " and  " + key + "like '%25"
						+ $('#' + whereKey).val() + "%25'";
			else
				where = where + " and  " + key + "='" + $('#' + whereKey).val()
						+ "'";
		} else {
			if (like)
				where = where + " " + key + " like '%25"
						+ $('#' + whereKey).val() + "%25'";
			else
				where = where + " " + key + " ='" + $('#' + whereKey).val()
						+ "'";
		}
	}
	return where;
}
/**
 * 打开新窗口
 * 
 * @param url
 * @param win_name
 * @param width
 * @param height
 */
function openPOPWin(url, titleName) {

	window.location.href=url; 
	
//	var a = document.createElement("a");
//	a.setAttribute("href", url);
//	a.setAttribute("target", "_blank");
//	a.setAttribute("id", "openwin");
//	a.setAttribute("title", titleName);
//	document.body.appendChild(a);
//	a.click();

}
/**
 * 
 * @param url
 * @param win_name
 * @param width
 * @param height
 */
function openUserWin(url, win_name, width, height) {

	var showx = (window.screen.availWidth - width) / 2;
	var showy = (window.screen.availHeight - height) / 2;
	var window_name = window.location.href;
	if (win_name == '') {
		window_name = window_name.split("?")[0];
		window_name = window_name.split("/")[window_name.split("/").length - 1];
		window_name = window_name.split(".")[0];
	} else
		window_name = win_name;
	var win = window.open(url, window_name, "width=" + width + ",height="
			+ height + ",top=" + showy + ",left=" + showx
			+ ",menubar=no,scrollbars=yes,toolbar=no,resizable=yes,status=no");
	win.focus();
}
/** ****************************************************************************************************************** */
/**
 * jsp中必须定义dataTableId; pagerId;editForm;titeSpan
 * 
 * 四个boolean行参数
 */
function initOperate(add, edit, query, del, print, rowKey) {
	var url = document.getElementById("editForm").action;
	if (add) {
		jQuery("#dataTableId").navButtonAdd('#pagerId', {
			caption : "新增",
			onClickButton : function() {
				$(".editWin").show(1000);
				$('#method').attr('value', 'add');
				clearWin();
				document.getElementById('titeSpan').innerHTML = "新 增";
			}
		}).trigger("reloadGrid"); // 重新载入;

	}

	if (edit) {
		jQuery("#dataTableId").navButtonAdd('#pagerId', {
			caption : "修改",
			onClickButton : function() {
				document.getElementById('titeSpan').innerHTML = "修 改";
				var gr = $("#dataTableId").jqGrid('getGridParam', 'selrow');
				var mulsel = $("#dataTableId").getGridParam('selarrrow');
				if (gr != null && mulsel.length < 2) {
					var rowData = $("#dataTableId").getRowData(gr);
					$(".editWin").show(1000);
					// 调用主页的js
					editCol(rowData);

				} else if (mulsel.length > 1) {
					alert("你不能选择多于一行 ！");
				} else
					alert("你没有选择某行 ！");

			}
		}).trigger("reloadGrid"); // 重新载入;

	}

	if (query) {
		jQuery("#dataTableId").navButtonAdd('#pagerId', {
			caption : "查找",
			onClickButton : function() {
				$(".findWin").show(1000);

			}
		});

	}
	if (del) {
		url = url + "delete.action?";
		jQuery("#dataTableId").navButtonAdd(
				'#pagerId',
				{
					caption : "删除",
					onClickButton : function() {
						var deletes = "";
						var mulsel = $("#dataTableId")
								.getGridParam('selarrrow');

						if (mulsel.length == 0) {

						} else {
							deletes = "";
							for ( var i = 0; i < mulsel.length; i++) {
								data = $("#dataTableId").jqGrid('getRowData',
										mulsel[i]);

								var row_key = eval("data." + rowKey);
								if (i < mulsel.length - 1)
									deletes += row_key + ",";
								else
									deletes += row_key;
							}
						}
						if (deletes != '') {
							var params = "deletes=" + deletes;
							if (!window.confirm("你确定要删除选定的行? ")) {
								return false;
							}
							$
									.ajax({
										type : "POST",
										url : url,
										data : params,
										cache : false,
										dataType : 'json',
										success : function(data) {
											var message = data.success;
											if (message != undefined
													&& message.length > 0) {
												alert(message);
												$("#dataTableId").trigger(
														"reloadGrid");
											} else {
												alert("删除失败！");
											}

										}
									}); // 重新载入;
						} else {
							alert('请至少选择一项！');
						}
					}
				});

	}
	if (print) {
		var printURL = $("#printURL").val();
		jQuery("#dataTableId").navButtonAdd('#pagerId', {
			caption : "打印",
			onClickButton : function() {
				openPOPWin(printURL, "打印");
			}
		});

	}
}
 
function initOperateWindow(add_url, edit_url, query, del_url, print, rowKey) {
//var url = "";//document.getElementById("editForm").action;
if (add_url!=null&&add_url.length>0) {
	jQuery("#dataTableId").navButtonAdd('#pagerId', {
		caption : "新增",
		onClickButton : function() {
			window.location.href=add_url; 
		}
	}).trigger("reloadGrid"); // 重新载入;

}

if (edit_url!=null&&edit_url.length>0) {
	jQuery("#dataTableId").navButtonAdd('#pagerId', {
		caption : "修改",
		onClickButton : function() {
			
			var gr = $("#dataTableId").jqGrid('getGridParam', 'selrow');
			var mulsel = $("#dataTableId").getGridParam('selarrrow');
			if (gr != null && mulsel.length < 2) {
				var rowData = $("#dataTableId").getRowData(gr);
				var keyID=eval("rowData." + rowKey);
				window.location.href=edit_url+"&keyID="+keyID;
			} else if (mulsel.length > 1) {
				alert("你不能选择多于一行 ！");
			} else
				alert("你没有选择某行 ！");

		}
	}).trigger("reloadGrid"); // 重新载入;

}

if (query) {
	jQuery("#dataTableId").navButtonAdd('#pagerId', {
		caption : "查找",
		onClickButton : function() {
			$(".findWin").show(1000);

		}
	});

}
if (del_url!=null&&del_url.length>0) {
	//url = url + "delete.action?";
	jQuery("#dataTableId").navButtonAdd(
			'#pagerId',
			{
				caption : "删除",
				onClickButton : function() {
					var deletes = "";
					var mulsel = $("#dataTableId")
							.getGridParam('selarrrow');
                           alert(del_url);
					if (mulsel.length == 0) {

					} else {
						deletes = "";
						for ( var i = 0; i < mulsel.length; i++) {
							data = $("#dataTableId").jqGrid('getRowData',
									mulsel[i]);

							var row_key = eval("data." + rowKey);
							if (i < mulsel.length - 1)
								deletes += row_key + ",";
							else
								deletes += row_key;
						}
					}
					if (deletes != '') {
						var params = "deletes=" + deletes;
						if (!window.confirm("你确定要删除选定的行? ")) {
							return false;
						}
						$
								.ajax({
									type : "POST",
									url : del_url,
									data : params,
									cache : false,
									dataType : 'json',
									success : function(data) {
										var message = data.success;
										if (message != undefined
												&& message.length > 0) {
											alert(message);
											$("#dataTableId").trigger(
													"reloadGrid");
										} else {
											alert("删除失败！");
										}

									}
								}); // 重新载入;
					} else {
						alert('请至少选择一项！');
					}
				}
			});

}
if (print) {
	var printURL = $("#printURL").val();
	jQuery("#dataTableId").navButtonAdd('#pagerId', {
		caption : "打印",
		onClickButton : function() {
			openPOPWin(printURL, "打印");
		}
	});

}
}

// /提交至后台
function save(form_id) {
	var url = document.getElementById(form_id).action;

	if (url.indexOf("?") > 0) {// 清除之前的提交参数
		url = url.substring(0, url.indexOf("?"));
	}	
	var form = document.getElementById(form_id);
	var alert_inf = '';
	var row = 1;
	for ( var i = 0; i < form.length; i++) {
		if (form.elements[i].type != "hidden") {
			if (form.elements[i].value == "") {
				if (alert_inf != '')
					alert_inf = alert_inf + ',第' + row + "行值为空!";
				else
					alert_inf = "第" + row + "行值为空!";
			}
			row++;
		}

	}
	if (alert_inf != '') {
		if (!window.confirm(alert_inf + ",你确定要保存? "))
			return;
	}
	var parm=getPageParms("dataTableId");
	parm=parm.replace("&",""); //去掉第一个& 
	url = url + $("#method").val()+ ".action?"  + parm;
	submitFrom(form_id, url);
	$("#dataTableId").trigger("reloadGrid");
	if(win_child!=undefined&&win_child!=null)
	   win_child.close();
}

function getPageParms(dataTableId) {
	var pam_url = '';
	var page = $("#" + dataTableId).getGridParam('page');
	if (page != '' && page != null)
		pam_url = '&page=' + page;
	var rows = $("#" + dataTableId).getGridParam('rows');
	if (rows != '' && rows != null)
		pam_url = pam_url + '&rows=' + rows;
	var sidx = $("#" + dataTableId).getGridParam('sidx');
	if (sidx != '' && sidx != null)
		pam_url = pam_url + '&sidx=' + sidx;
	var sord = $("#" + dataTableId).getGridParam('sord');
	if (sord != '' && sord != null)
		pam_url = pam_url + '&sord=' + sord;

	return pam_url;
}
