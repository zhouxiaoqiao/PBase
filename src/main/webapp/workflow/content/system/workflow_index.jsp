<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html lang="en">
<head>
	<title>Snaker</title>
</head>
<frameset rows="*" cols="*" frameborder="no" border="0" framespacing="0">
  <frameset id="myFrame" cols="230,*" frameborder="no" border="0" framespacing="0">
    <frame src="${ctx }/left" name="leftFrame" scrolling="no" noresize="noresize" id="leftFrame" title="leftFrame" />	
	<frame src="${ctx }/right" name="mainFrame" scrolling="auto" noresize="noresize" id="mainFrame" title="mainFrame" />
  </frameset>
</frameset>
</html>

