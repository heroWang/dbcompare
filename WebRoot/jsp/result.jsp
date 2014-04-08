<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/jsp/taglibs.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript"
	src="<%=basePath%>${basePath}/js/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="<%=basePath%>/js/public.js"></script>
<script type="text/javascript"
	src="<%=basePath%>${basePath}/js/jquery-ui-1.8.23.custom.min.js"></script>
<script type="text/javascript"
	src="<%=basePath%>${basePath}/js/jquery.dynatree.min.js"></script>


<link type="text/css" rel="stylesheet"
	href="<%=basePath%>${basePath}/skin/dynatree/ui.dynatree.css" />
<link type="text/css" rel="stylesheet"
	href="<%=basePath%>${basePath}/skin/easydialog/easydialog.css" />
<link type="text/css" rel="stylesheet"
	href="<%=basePath%>${basePath}/skin/main.css" />

<title>比较结果</title>
</head>
<body>
	<div style="width: auto; height:auto" id="tree" style="display:none"></div>
</body>
<script type="text/javascript">
	//Attach the dynatree widget to an existing <div id="tree"> element
	// and pass the tree options as an argument to the dynatree() function:
	$(function() {
		$("#tree").dynatree({
			onCreate : function(node, span) {
				//alert($(node.span).find('img'));
				//$(node.span).find('a').after($('<img src="/skin/dynatree/equal.png" />'));
				//node.data.title='<img src="/skin/dynatree/equal.png" />'+node.data.title;
				//$(node.span).css({'background-image':'/skin/dynatree/equal.png',position:'absolute'})
				var source = node.data.sourceContent;
				var target = node.data.targetContent;
				var isFolder = node.data.isFolder;

				var diffContent = "<br/>";
				//if (isFolder=='false') {
				//alert('isLeaf');
				if (source != null) {
					diffContent += source;
				}
				if (target != null) {
					diffContent += "<br/>" + target;
				}

				$(diffContent).appendTo(node.span);
				//}
			},
			onPostInit : function(dtnode) {
				//alert('init');
				//node.data.title='<img src="/skin/dynatree/equal.png" />'+node.data.title;
				//$(node.span).find('img').after($('<img src="/skin/dynatree/equal.png" />'));
			},
			imagePath : "<%=basePath%>/skin/dynatree/",
			initAjax : {
				url : '<%=basePath%>/compare/jsontree'
			},
			strings : {
				loading : "Loading…",
				loadError : "Load error!"
			},
			onPostInit : function(isReloading, isError) {
				if (isError) {
					alert("出错啦!!!看后台日志。重新加载请按F5");
				}
			}

		});
	});
</script>
</html>