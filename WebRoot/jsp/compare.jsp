<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/jsp/taglibs.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript"
	src="<%=basePath%>/js/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="<%=basePath%>/js/public.js"></script>
<script type="text/javascript"
	src="<%=basePath%>/js/jquery-ui-1.8.23.custom.min.js"></script>
<script type="text/javascript"
	src="<%=basePath%>/js/jquery.dynatree.min.js"></script>


<link type="text/css" rel="stylesheet"
	href="<%=basePath%>/skin/dynatree/ui.dynatree.css" />
<link type="text/css" rel="stylesheet"
	href="<%=basePath%>/skin/easydialog/easydialog.css" />
<link type="text/css" rel="stylesheet"
	href="<%=basePath%>/skin/main.css" />

<title>数据库schema比较设置</title>
</head>
<body>
	
	<div id="setting" class="setting">
		<div class="title">
			<strong>Compare Setting</strong>
		</div>
		<form:form  id="form-setting"  action='#' method="post" modelAttribute="setting" >
			<fieldset title="Source" style="float: left">
				<legend>Source</legend>
				<div class="item">
					<label for="source_adress">address</label> <input
						id="source_adress" type="text" tabindex="1" value="${setting.sourceDatabaseSetting.address}"
						name="sourceDatabaseSetting.address" style="color: rgb(0, 0, 0);">
				</div>
				<div class="item">
					<label for="source_user">user</label> <input id="source_user"
						class="text" type="text" tabindex="2" value="${setting.sourceDatabaseSetting.user}" name="sourceDatabaseSetting.user">
				</div>
				<div class="item">
					<label for="source_pwd">password</label> <input id="source_pwd"
						class="text" type="password" tabindex="3" value="${setting.sourceDatabaseSetting.password}"name="sourceDatabaseSetting.password">
				</div>
				<div class="item">
					<label for="source_port">port</label> <input id="source_port"
						class="text" type="text" tabindex="4" value="${setting.sourceDatabaseSetting.port}"name="sourceDatabaseSetting.port">
				</div>
				<div class="item">
					<label for="source_database">database</label> <input
						id="source_database" class="text" type="text" tabindex="4"
						value="${setting.sourceDatabaseSetting.dbname}" name="sourceDatabaseSetting.dbname">
				</div>
			</fieldset>
			<fieldset title="Target" style="float: right">
				<legend>Target</legend>
				<div class="item">
					<label for="target_adress">address</label> <input
						id="target_adress" type="text" tabindex="5"value="${setting.targetDatabaseSetting.address}"
						name="targetDatabaseSetting.address" style="color: rgb(0, 0, 0);">
				</div>
				<div class="item">
					<label for="target_user">user</label> <input id="target_user"
						class="text" type="text" tabindex="6" name="targetDatabaseSetting.user" value="${setting.targetDatabaseSetting.user}">
				</div>
				<div class="item">
					<label for="target_pwd">password</label> <input id="target_pwd"
						class="text" type="password" tabindex="7" value="${setting.targetDatabaseSetting.password}" name="targetDatabaseSetting.password" >
				</div>
				<div class="item">
					<label for="target_port">port</label> <input id="target_port"
						class="text" type="text" tabindex="8" value="${setting.targetDatabaseSetting.port}" name="targetDatabaseSetting.port">
				</div>
				<div class="item">
					<label for="target_database">database</label> <input
						id="target_database" class="text" type="text" tabindex="4" value="${setting.targetDatabaseSetting.dbname}"
						name="targetDatabaseSetting.dbname">
				</div>
			</fieldset>
			<div id="include-tables">
				<label for=“include_tables”>include tables</label><input
					id="include_tables" title="以 ; 隔开" class="text" tabindex="9" value="${setting.includeTables}"
					name="includeTables" />
			</div>
			<div class='btns'>
				<div class="item1" style="float: left">
					<input class="bn-submit" type="submit" tabindex="10"
						value="confirm" onclick="return check(true);">
				</div>
				<div class="item1" style="float: right">
					<input class="bn-submit" type="submit" onclick='return check(false)'
						tabindex="10" value="cancel">
				</div>
			</div>
		</form:form>
	</div>

</body>
<script type="text/javascript" src="<%=basePath%>/js/easydialog.min.js"></script>
<script type="text/javascript">
function check(isChanged){
	$("#form-setting").attr("action", "<%=basePath%>/setting/save?isChanged="+isChanged);
	$("#form-setting").submit();
	return true;
}

	//TODO 弹一个框
	$(function() {
		easyDialog.open({
			container : 'setting',
		//autoClose : 10000
		});

		// Attach the dynatree widget to an existing <div id="tree"> element
		// and pass the tree options as an argument to the dynatree() function:
		$("#tree")
				.dynatree(
						{
							onCreate : function(node, span) {
								//alert($(node.span).find('img'));
								//$(node.span).find('a').after($('<img src="/skin/dynatree/equal.png" />'));
								//node.data.title='<img src="/skin/dynatree/equal.png" />'+node.data.title;
								//$(node.span).css({'background-image':'/skin/dynatree/equal.png',position:'absolute'})
							},
							onPostInit : function(dtnode) {
								//alert('init');
								//node.data.title='<img src="/skin/dynatree/equal.png" />'+node.data.title;
								//$(node.span).find('img').after($('<img src="/skin/dynatree/equal.png" />'));
							},
							imagePath : "${basePath}/skin/dynatree/",
							children : [ {
								title : '<img src="/skin/dynatree/diff.png" />'
										+ 'Tables' + '(Total:10,diff:7,miss:3)',
								//isFolder : false,
								icon : 'tables.ico',
								children : [ {
									title : '<img src="/skin/dynatree/diff.png" />'
											+ 'admin',
									icon : 'table.ico',
									//	isFolder : false,
									children : [
											{
												title : '<img src="/skin/dynatree/diff.png" />'
														+ 'Columns'
														+ '(Total:5,miss:3)',
												icon : 'columns.ico',
												//isFolder : false,
												children : [
														{
															title : '<img src="/skin/dynatree/miss.png" />'
																	+ 'audit',
															icon : 'column.ico',
															diffContent : ''//TODO
														},
														{
															title : '<img src="/skin/dynatree/miss.png" />'
																	+ 'creator',
															icon : 'column.ico',
															diffContent : ''//TODO
														} ]
											},
											{
												title : '<img src="/skin/dynatree/diff.png" />'
														+ 'Indexes'
														+ '(Total:7,diff:7)',
												icon : 'indexes.ico',
											//isFolder : false,
											} ]
								} ]
							} ]
						});
	});
</script>
</html>