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
    <title>党组织生活列表</title>
    <meta name="description" content="overview &amp; stats"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0"/>
    <link href="${basePath}resource/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="${basePath}resource/bootstrap-table/bootstrap-table.min.css" rel="stylesheet">
    <link href="${basePath}resource/bootstrap-datepicker/css/bootstrap-datepicker3.css" rel="stylesheet">
    <link href="${basePath}resource/jQuery-confirm/jquery-confirm.min.css" rel="stylesheet">
    <link href="${basePath}resource/bootstrap-treeview/bootstrap-treeview.min.css" rel="stylesheet">

    <script src="${basePath}resource/jQuery/jquery.min.js"></script>
    <script src="${basePath}resource/jQuery/jquery.form.js"></script>
    <script src="${basePath}resource/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
    <script src="${basePath}resource/bootstrap-table/bootstrap-table.min.js"></script>
    <script src="${basePath}resource/bootstrap-table/locale/bootstrap-table-zh-CN.min.js"></script>
    <script src="${basePath}resource/bootstrap-datepicker/js/bootstrap-datepicker.js"></script>
    <script src="${basePath}resource/bootstrap-datepicker/locales/bootstrap-datepicker.zh-CN.min.js"></script>
    <script src="${basePath}resource/jQuery-confirm/jquery-confirm.min.js"></script>
    <script src="${basePath}resource/bootstrap-treeview/bootstrap-treeview.min.js"></script>
    <script src="${basePath}resource/bootstrap-treeview/dropTree.js"></script>
</head>
<body>
<div class="panel panel-default" style="margin-top: 20px;">
    <%--   <div class="panel-heading">
        查询条件
    </div>--%>
    <div class="panel-body form-group" style="margin-bottom:0px;">
        <label class="col-sm-1 control-label" style="text-align: right; margin-top:5px">标题：</label>
        <div class="col-sm-2">
            <input type="text" class="form-control" name="title" id="search_title"  placeholder="党组织生活标题"/>
        </div>
        <label class="col-sm-2 control-label" style="text-align: right; margin-top:5px">关键字：</label>
        <div class="col-sm-2">
            <input type="text" class="form-control" name="content" id="search_content"  placeholder="党组织生活内容关键字"/>
        </div>
        <div class="col-sm-1">
            <button class="btn btn-primary" id="search_btn">查询</button>
        </div>
    </div>
</div>
<table id="mytab" class="table table-hover"></table>
<div id="toolbar" class="btn-group pull-right" style="margin-right: 20px;">
    <button id="btn_add" type="button" class="btn btn-primary" data-toggle="modal" data-target="#addPartyCulture">
        <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>新增
    </button>
</div>

<div class="modal fade" id="addPartyCulture" tabindex="-1" role="dialog" aria-labelledby="addPartyCultureLabel" aria-hidden="true">
    <form role="form" id="addPartyCultureForm" method="post" enctype="multipart/form-data">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h4 class="modal-title" id="addPartyCultureLabel">添加党组织生活</h4>
                </div>
                <div class="modal-body">
                    <div class="form-group">
                        <label class="col-sm-2 form-label" for="title">标题</label>
                        <div class="col-sm-4">
                            <input type="text" name="title" class="form-control" id="title" placeholder="党组织生活标题" />
                        </div>
                        <label class="col-sm-2 form-label" for="dateTime">组织时间</label>
                        <div class="col-sm-4">
                            <input type="text" name="dateTime" class="datepicker form-control" id="dateTime" placeholder="请选择日期" />
                        </div>
                    </div>
                    <div class="form-group" style="padding-top: 30px;">
                        <label class="col-sm-2 form-label" for="content">党组织生活内容</label>
                        <div class="col-sm-10">
                            <textarea rows="3" name="content" class="form-control" id="content" placeholder="党组织生活内容"></textarea>
                        </div>
                    </div>
                    <div class="form-group" style="padding-top: 70px;">
                        <label class="col-sm-2 form-label" for="photoFile">谈话照片</label>
                        <div class="col-sm-10">
                            <input class="form-control" name="photoFile" id="photoFile" type="file" />
                        </div>
                    </div>
                </div>
                <div class="modal-footer" style="padding-top: 50px;">
                    <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                    <button id="addPartyCultureBtn" type="button" class="btn btn-primary">添加</button>
                </div>
            </div>
        </div>
    </form>
</div>

</body>
<script>
    //根据窗口调整表格高度
    $(window).resize(function() {
        $('#mytab').bootstrapTable('resetView', {
            height: tableHeight()
        });
    });
    $(".datepicker").datepicker({
        language: "zh-CN",
        autoclose: true,//选中之后自动隐藏日期选择框
        clearBtn: true,//清除按钮
        todayBtn: true,//今日按钮
        format: "yyyy-mm-dd"//日期格式，详见 http://bootstrap-datepicker.readthedocs.org/en/release/options.html#format
    });
    $('#addPartyCulture').on('show.bs.modal', function () {
        $(':input','#addPartyCultureForm')
            .not(':button, :submit, :reset, :file')
            .val('');
    });
    $("#addPartyCultureBtn").click(function () {
        $("#addPartyCultureForm").ajaxSubmit({
            type:'post',
            url:'${basePath}partyCulture/manager/addPartyCulture',
            dataType: 'json',
            success:function(data){
                console.log(data);
                if(data.success){
                    $("#addPartyCulture").modal("hide");
                    refresh();
                }else{
                    alert("添加失败");
                }

            },
            error:function(XmlHttpRequest,textStatus,errorThrown){
                console.log(XmlHttpRequest);
                console.log(textStatus);
                console.log(errorThrown);
                alert("添加失败");
            }
        });
    });
    //生成用户数据
    $('#mytab').bootstrapTable({
        method: 'post',
        contentType: "application/x-www-form-urlencoded",//必须要有！！！！
        url:"${basePath}partyCulture/manager/partyCultureList",//要请求数据的文件路径
        height:tableHeight(),//高度调整
        toolbar: '#toolbar',//指定工具栏
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
        toolbar:'#toolbar',//指定工作栏
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
                title:'标题',
                field:'title'
            },
            {
                title:'内容',
                field:'content'
            },
            {
                title:'组织时间',
                field:'dateTime',
                sortable:true
            },
            {
                title:'照片',
                field:'photo',
                formatter: function(value,row,index){
                    console.log("photo = " + value);
                    return '<img  src="'+value+'" class="img-rounded" />';
                }
            },
            {
                title:'操作',
                align:'center',
                formatter: function(value,row,index){
                    return '<button onclick="del('+row.id+')" id="btn_del" type="button" class="glyphicon-minus btn-danger" data-toggle="modal">删除</button>';
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

    function del(__id) {
        console.log("del id = " + __id);
        $.confirm({
            title: '警告',
            content: '您确认要删除该党组织生活记录吗？',
            buttons: {
                删除: function(){
                    $.ajax({
                        type: "POST",
                        url:'${basePath}partyCulture/manager/delPartyCulture',
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
            title:$('#search_title').val(),
            content:$('#search_content').val()
        }
    }
    //查询按钮事件
    $('#search_btn').click(function(){
        refresh();
    });

    function refresh() {
        $('#mytab').bootstrapTable('refresh', {url: '${basePath}partyCulture/manager/partyCultureList'});
    }

    //tableHeight函数
    function tableHeight() {
        //可以根据自己页面情况进行调整
        return $(window).height() - 280;
    }

</script>
</html>
