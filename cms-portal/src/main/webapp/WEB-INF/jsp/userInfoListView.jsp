<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="zh">
<%
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
    request.getSession().setAttribute("basePath", basePath);
    System.out.println("basePath = " + basePath);
%>
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
    <meta charset="utf-8"/>
    <title>用户信息列表</title>
    <meta name="description" content="overview &amp; stats"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0"/>
    <link href="${basePath}resource/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="${basePath}resource/bootstrap-table/bootstrap-table.min.css" rel="stylesheet">
    <link href="${basePath}resource/jQuery-confirm/jquery-confirm.min.css" rel="stylesheet">
    <link href="${basePath}resource/bootstrap-treeview/bootstrap-treeview.min.css" rel="stylesheet">

    <script src="${basePath}resource/jQuery/jquery.min.js"></script>
    <script src="${basePath}resource/jQuery/jquery.form.js"></script>
    <script src="${basePath}resource/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
    <script src="${basePath}resource/bootstrap-table/bootstrap-table.min.js"></script>
    <script src="${basePath}resource/bootstrap-table/locale/bootstrap-table-zh-CN.min.js"></script>
    <script src="${basePath}resource/jQuery-confirm/jquery-confirm.min.js"></script>
    <script src="${basePath}resource/bootstrap-treeview/bootstrap-treeview.min.js"></script>
    <script src="${basePath}resource/bootstrap-treeview/dropTree.js"></script>
</head>
<body style="width: 100%; height: 100px;">
<div class="panel panel-default" style="margin-top: 20px;">
    <%--   <div class="panel-heading">
        查询条件
    </div>--%>
    <div class="panel-body form-group" style="margin-bottom:0px;">
        <label class="col-sm-2 control-label" style="text-align: right; margin-top:5px">真实姓名：</label>
        <div class="col-sm-2">
            <input type="text" class="form-control" name="org" id="search_org"/>
        </div>
        <label class="col-sm-1 control-label" style="text-align: right; margin-top:5px">网名：</label>
        <div class="col-sm-2">
            <input type="text" class="form-control" name="name" id="search_name"/>
        </div>
        <div class="col-sm-1">
            <button class="btn btn-primary" id="search_btn">查询</button>
        </div>
    </div>
</div>
<table id="mytab" class="table table-hover"></table>

<div id="treePanle" style="display: none; background-color: #FFFFFF; z-index: 1051;">
    <div id="treeClose" style="width: 100%; height: 20px; z-index: 2000;"><span style="float: right;">&nbsp;&nbsp;&nbsp;X&nbsp;&nbsp;&nbsp;</span></div>
    <div style="width: 100%;" id="orgTree">

    </div>
</div>
</body>
<script>
    //根据窗口调整表格高度
    $(window).resize(function() {
        $('#mytab').bootstrapTable('resetView', {
            height: tableHeight()
        });
    });

    //生成用户数据
    $('#mytab').bootstrapTable({
        method: 'post',
        contentType: "application/x-www-form-urlencoded",//必须要有！！！！
        url:"${basePath}admin/userInfoList",//要请求数据的文件路径
        height:tableHeight(),//高度调整
        striped: true, //是否显示行间隔色
        dataField: "rows",//bootstrap table 可以前端分页也可以后端分页，这里
        //我们使用的是后端分页，后端分页时需返回含有total：总记录数,这个键值好像是固定的
        //rows： 记录集合 键值可以修改  dataField 自己定义成自己想要的就好
        pageNumber: 1, //初始化加载第一页，默认第一页
        pagination:true,//是否分页
        queryParamsType:'',// queryParamsType的默认值为 'limit' ,在默认情况下 传给服务端的参数为：offset,limit,sor设置为 ''  在这种情况下传给服务器的参数为：pageSize,pageNumber
        queryParams:queryParams,//请求服务器时所传的参数
        sidePagination:'server',//指定服务器端分页
        pageSize:10,//单页记录数
        pageList:[5,10,20,30],//分页步进值
        showRefresh:false,//刷新按钮
        showColumns:false,
        clickToSelect: true,//是否启用点击选中行
        toolbarAlign:'right',//工具栏对齐方式
        buttonsAlign:'right',//按钮对齐方式
        columns:[
            {
                title:'全选',
                field:'select',
                //复选框
                checkbox:true,
                width:25,
                align:'center',
                valign:'middle'
            },
            {
                title:'id',
                field:'id',
                visible:false
            },
            {
                title:'真实姓名',
                field:'realName'
            },
            {
                title:'网名',
                field:'loginName'
            },{
                title:'是否为管理员',
                field:'isAdmin',
                formatter: function(value,row,index){
                    return "是";
                }
            }, {
                title: '角色',
                field: 'role',
                formatter: function(value,row,index){
                    return "编辑人员";
                }
            }, {
                title:'操作',
                align:'center',
                //列数据格式化
                formatter: function(value,row,index){
                    console.log(row);
                    var __role = row.role;
                    var html = '';
                    html += '<button  type="button" class="glyphicon-plus btn-danger" data-toggle="modal">删除用户</button>';
                    html += '<button  type="button" class="glyphicon-minus btn-warning" data-toggle="modal">编辑用户</button>';
                    return html;
                }
            }
        ],
        locale:'zh-CN',//中文支持,
        responseHandler:function(res){
            //在ajax获取到数据，渲染表格之前，修改数据源
            console.log(res);
            return res;
        }
    });

    function downgrade(__id) {
        console.log("downgrade id = " + __id);
        $.confirm({
            title: '警告',
            content: '您确认要将该部门管理员降级为普通成员吗？',
            buttons: {
                升级: function(){
                    $.ajax({
                        type: "POST",
                        url:'${basePath}user/downgrade',
                        async: true,
                        data: {
                            id: __id
                        },
                        timeout: 5000,
                        dataType: 'json',
                        beforeSend: function (xhr) {
                            console.log("ajax beforeSend");
                            console.log(xhr);
                        },
                        success: function (data, textStatus, jqXHR) {
                            console.log(data);
                            console.log("ajax success");
                        },
                        error: function (xhr, testStatus) {
                            console.log("ajax error");
                        },
                        complete: function () {
                            console.log("ajax complete");
                        }
                    });
                    refresh();
                },
                取消: function(){
                    console.log("cancel");
                }
            }
        });
    }

    function upgrade(__id) {
        console.log("upgrade id = " + __id);
        $.confirm({
            title: '警告',
            content: '您确认要升级该党员权限为部门管理员吗？',
            buttons: {
                升级: function(){
                    $.ajax({
                        type: "POST",
                        url:'${basePath}user/upgrade',
                        async: true,
                        data: {
                            id: __id
                        },
                        timeout: 5000,
                        dataType: 'json',
                        beforeSend: function (xhr) {
                            console.log("ajax beforeSend");
                            console.log(xhr);
                        },
                        success: function (data, textStatus, jqXHR) {
                            console.log(data);
                            console.log("ajax success");
                        },
                        error: function (xhr, testStatus) {
                            console.log("ajax error");
                        },
                        complete: function () {
                            console.log("ajax complete");
                        }
                    });
                    refresh();
                },
                取消: function(){
                    console.log("cancel");
                }
            }
        });
    }
    //请求服务数据时所传参数
    function queryParams(params){
        console.log(params);
        return{
            //第多少页
            pageNo: params.pageNumber,
            //页大小
            pageSize:params.pageSize,
            leader:$('#search_leader').val(),
            conversation:$('#search_conversation').val(),
            flag:$('#search_flag').val()
        }
    }
    //查询按钮事件
    $('#search_btn').click(function(){
        refresh();
    });

    function refresh() {
        $('#mytab').bootstrapTable('refresh', {url: '${basePath}user/userInfoList'});
    }

    //tableHeight函数
    function tableHeight() {
        //可以根据自己页面情况进行调整
        return $(window).height() - 280;
    }
    $("#search_org").DropTree({
        'xOffset': 105,
        'yOffset': 70,
        'treePanle': 'treePanle',
        'tree': 'orgTree',
        'treeClose': 'treeClose',
        'dataUrl': '${basePath}org/orgTree'
    });

</script>
</html>
