<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="java.io.PrintWriter"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>未知错误</title>
<%@ include file="/jsp/taglibs.jsp"%>
<script type="text/javascript">
	$(function(){
		$("#toggle").click(function(){
			if($(this).text() == "详细"){
				$("#detail").show();
				$(this).text("收起");
			}else{
				$("#detail").hide();
				$(this).text("详细");
			}
		});
	});
</script>
</head>
<body>
	<div style="padding-top: 200px; color: red; text-align: center;">
		<h1>貌似配置文件是不是有问题?
		<span><a href="#" id="toggle">详细</a></span></h1>
	</div>
	<div id="detail" style="display: none;margin-left:100px;">
		<p>
			<%
				PrintWriter pw = new PrintWriter(out);
			    if(exception != null){
			    	exception.printStackTrace(pw);
			    }else{
			        out.print("无详细信息");
			    }
			%>
		</p>
	</div>
</body>
</html>