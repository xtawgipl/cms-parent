<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<%
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
	request.getSession().setAttribute("basePath", basePath);
	System.out.println("basePath = " + basePath);
%>
	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
		<meta charset="utf-8" />
		<title>基于java的内容管理系统</title>
		<meta name="description" content="overview &amp; stats" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />
        <link href="${basePath}resource/bootstrap/css/bootstrap.min.css" rel="stylesheet">
        <script src="${basePath}resource/jQuery/jquery.min.js"></script>
        <script src="${basePath}resource/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
		<script type="text/javascript">
			$(function() {
				var height=document.documentElement.clientHeight;
				document.getElementById('iframe-page-content').style.height=height+'px';
			});
			
			var menuClick = function(menuUrl) {
				$("#iframe-page-content").attr('src',menuUrl);
			};
		</script>
	</head>

<body>
	<div style="width: 100%; height: 100%;">
        <div id="header-container">
            <h1 style="width: 500px;" class="center-block">基于java的内容管理系统</h1>
        </div>
		<div id="main-container">
			<div id="sidebar" class="col-md-2 column">				
				<!-- 创建菜单树 -->
				<div class="col-md-12">
                <ul id="main-nav" class="nav nav-tabs nav-stacked" style="">
                    <li>
                        <a href="#systemSetting" class="nav-header collapsed"  style="font-size: 20px;" data-toggle="collapse">
                            <i class="glyphicon glyphicon-cog"></i>用户管理
                               <span class="pull-right glyphicon glyphicon-chevron-down"></span>
                        </a>
                        <ul id="systemSetting" class="nav nav-list collapse secondmenu" style="height: 0px; padding-left: 20px;">
                            <li><a href="#" onclick="menuClick('${basePath}admin/userInfoListView')"><i class="glyphicon glyphicon-user"></i>用户列表</a></li>
                        </ul>
                    </li>
                    <li>
                        <a href="#systemSetting1" class="nav-header collapsed"  style="font-size: 20px;" data-toggle="collapse">
                            <i class="glyphicon glyphicon-cog"></i>模板管理
                            <span class="pull-right glyphicon glyphicon-chevron-down"></span>
                        </a>
                        <ul id="systemSetting1" class="nav nav-list collapse secondmenu" style="height: 0px; padding-left: 20px;">
                            <li><a href="#" onclick="menuClick('${basePath}partyCulture/manager/partyCultureView')"><i class="glyphicon glyphicon-user"></i>模板列表</a></li>
                        </ul>
                    </li>
                    <li>
                        <a href="#systemSetting2" class="nav-header collapsed"  style="font-size: 20px;" data-toggle="collapse">
                            <i class="glyphicon glyphicon-cog"></i>内容管理
                            <span class="pull-right glyphicon glyphicon-chevron-down"></span>
                        </a>
                        <ul id="systemSetting2" class="nav nav-list collapse secondmenu" style="height: 0px; padding-left: 20px;">
                            <li><a href="#" onclick="menuClick('${basePath}partyCulture/manager/partyCultureView')"><i class="glyphicon glyphicon-user"></i>新闻列表</a></li>
                        </ul>
                    </li>
                    <li>
                        <a href="#systemSetting3" class="nav-header collapsed" style="font-size: 20px;"  data-toggle="collapse">
                            <i class="glyphicon glyphicon-cog"></i>权限管理
                               <span class="pull-right glyphicon glyphicon-chevron-down"></span>
                        </a>
                        <ul id="systemSetting3" class="nav nav-list collapse secondmenu" style="height: 0px; padding-left: 20px;">
                            <li><a href="#" onclick="menuClick('${basePath}partyCulture/manager/partyCultureView')"><i class="glyphicon glyphicon-user"></i>角色列表</a></li>
                            <li><a href="#" onclick="menuClick('${basePath}partyCulture/manager/partyCultureView')"><i class="glyphicon glyphicon-user"></i>权限操作</a></li>
                            <li><a href="#" onclick="menuClick('${basePath}partyCulture/manager/partyCultureView')"><i class="glyphicon glyphicon-user"></i>个人信息修改</a></li>
                        </ul>
                    </li>
                </ul>
            </div>
		</div>
		<div class="col-md-10 column">
			<div>
				<iframe id="iframe-page-content" src="${basePath}admin/userInfoListView" width="100%" height="100%"  frameborder="no" border="0" marginwidth="0" marginheight="0" scrolling="yes" allowtransparency="yes"></iframe>
			</div>				
		</div>
		</div>
	</div>
</body>
</html>
