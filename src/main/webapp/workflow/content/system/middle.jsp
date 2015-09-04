<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<script type="text/javascript">
	/* 
	 function addTab(data) {
	 t = $('#view_layout_center_tabs');
	 if (t.tabs('exists', data.title)) {
	 t.tabs('select', data.title);
	 } else {
	 t.tabs('add', data);
	 }
	 }
	 */
	function Open(text, url, action) {

		if (url != '#') {
			if (action != null && action != "")
				document.getElementById("main_Frame").src = '${pageContext.request.contextPath}'
						+ url + "?action=" + action;
			else
				document.getElementById("main_Frame").src = '${pageContext.request.contextPath}'
						+ url;
		} else
			alert("url路径为空!");
	}
</script>
<iframe src="${pageContext.request.contextPath}/advertise_admin/collect_inf/display_interveiw.jsp?action=/adminAdvetise/resumeAdmin_" id="main_Frame" name="main_Frame" height="100%"
	width="100%"></iframe>
