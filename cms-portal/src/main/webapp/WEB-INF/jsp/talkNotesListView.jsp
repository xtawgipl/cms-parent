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
    <title>谈话记录列表</title>
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
        <label class="col-sm-1 control-label" style="text-align: right; margin-top:5px">领导：</label>
        <div class="col-sm-2">
            <input type="text" class="form-control" name="leader" id="search_leader"  placeholder="谈话领导姓名"/>
        </div>
        <label class="col-sm-2 control-label" style="text-align: right; margin-top:5px">谈话对象：</label>
        <div class="col-sm-2">
            <input type="text" class="form-control" name="conversation" id="search_conversation"  placeholder="谈话对象姓名"/>
        </div>
        <label class="col-sm-1 control-label" style="text-align: right; margin-top:5px">类型：</label>
        <div class="col-sm-2">
            <select class="form-control" name="flag" id="search_flag">
                <option value="0">正常谈话</option>
                <option value="1">异常谈话</option>
            </select>
        </div>
        <div class="col-sm-1">
            <button class="btn btn-primary" id="search_btn">查询</button>
        </div>
    </div>
</div>
<table id="mytab" class="table table-hover"></table>
<div id="toolbar" class="btn-group pull-right" style="margin-right: 20px;">
    <%--<button id="btn_edit" type="button" class="btn btn-default" style="display: none; border-radius: 0">
        <span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>修改
    </button>
    <button id="btn_delete" type="button" class="btn btn-default" style="display: none;">
        <span class="glyphicon glyphicon-remove" aria-hidden="true"></span>删除
    </button>--%>
    <button id="btn_add" type="button" class="btn btn-primary" data-toggle="modal" data-target="#addTalkNote">
        <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>新增
    </button>
</div>

<div class="modal fade" id="addTalkNote" tabindex="-1" role="dialog" aria-labelledby="addTalkNoteLabel" aria-hidden="true">
    <form role="form" id="addTalkNoteForm" method="post" enctype="multipart/form-data">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h4 class="modal-title" id="addTalkNoteLabel">添加谈话记录</h4>
                </div>
                <div class="modal-body">
                    <div class="form-group">
                        <label class="col-sm-2 form-label" for="leaderOrg">领导部门</label>
                        <div class="col-sm-4">
                            <input type="text" name="leaderOrg" class="form-control" id="leaderOrg" placeholder="领导所属部门" />
                        </div>
                        <label class="col-sm-2 form-label" for="leaderId">领导</label>
                        <div class="col-sm-4">
                            <select class="form-control" name="leaderId" id="leaderId">

                            </select>
                        </div>
                    </div>
                    <div class="form-group" style="padding-top: 30px;">
                        <label class="col-sm-2 form-label" for="conversationOrg">党员部门</label>
                        <div class="col-sm-4">
                            <input type="text" name="conversationOrg" class="form-control" id="conversationOrg" placeholder="谈话对象所属部门" />
                        </div>
                        <label class="col-sm-2 form-label" for="conversationId">谈话对象</label>
                        <div class="col-sm-4">
                            <select class="form-control" name="conversationId" id="conversationId">

                            </select>
                        </div>
                    </div>
                    <div class="form-group" style="padding-top: 30px;">
                        <label class="col-sm-2 form-label" for="time">谈话时间</label>
                        <div class="col-sm-4">
                            <input type="text" name="time" class="datepicker form-control" id="time" placeholder="请选择日期" />
                        </div>
                        <label class="col-sm-2 form-label" for="duration">谈话时长</label>
                        <div class="col-sm-4">
                            <input type="text" name="duration" class="form-control" id="duration" placeholder="谈话时长" />
                        </div>
                    </div>
                    <div class="form-group" style="padding-top: 30px;">
                        <label class="col-sm-2 form-label" for="flag">谈话类型</label>
                        <div class="col-sm-4">
                            <select class="form-control" name="flag" id="flag">
                                <option value="0">正常谈话</option>
                                <option value="1">异常谈话</option>
                            </select>
                        </div>
                    </div>
                    <div class="form-group" style="padding-top: 30px;">
                        <label class="col-sm-2 form-label" for="content">谈话内容</label>
                        <div class="col-sm-10">
                            <textarea rows="3" name="content" class="form-control" id="content" placeholder="谈话内容"></textarea>
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
                    <button id="addTalkNoteBtn" type="button" class="btn btn-primary">添加</button>
                </div>
            </div>
        </div>
    </form>
</div>
<div id="leader_treePanle" style="display: none; background-color: #FFFFFF; z-index: 1051;">
    <div id="leader_treeClose" style="width: 100%; height: 20px; z-index: 2000;"><span style="float: right;">&nbsp;&nbsp;&nbsp;X&nbsp;&nbsp;&nbsp;</span></div>
    <div style="width: 100%;" id="leader_orgTree">

    </div>
</div>

<div id="conversation_treePanle" style="display: none; background-color: #FFFFFF; z-index: 1051;">
    <div id="conversation_treeClose" style="width: 100%; height: 20px; z-index: 2000;"><span style="float: right;">&nbsp;&nbsp;&nbsp;X&nbsp;&nbsp;&nbsp;</span></div>
    <div style="width: 100%;" id="conversation_orgTree">

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
    $(".datepicker").datepicker({
        language: "zh-CN",
        autoclose: true,//选中之后自动隐藏日期选择框
        clearBtn: true,//清除按钮
        todayBtn: true,//今日按钮
        format: "yyyy-mm-dd"//日期格式，详见 http://bootstrap-datepicker.readthedocs.org/en/release/options.html#format
    });
    $('#addTalkNote').on('show.bs.modal', function () {
        $(':input','#addTalkNoteForm')
            .not(':button, :submit, :reset, :file')
            .val('');
    });
    $("#addTalkNoteBtn").click(function () {
        $("#addTalkNoteForm").ajaxSubmit({
            type:'post',
            url:'${basePath}talk/manager/addTalkNotes',
            dataType: 'json',
            success:function(data){
                console.log(data);
                if(data.success){
                    $("#addTalkNote").modal("hide");
                    $('#mytab').bootstrapTable('refresh', {url: '${basePath}talk/manager/talkNotesList'});
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
        url:"${basePath}talk/manager/talkNotesList",//要请求数据的文件路径
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
                title:'领导',
                field:'leader',
                formatter: function(value,row,index){
                    return value.xm;
                }
            },
            {
                title:'谈话对象',
                field:'conversation',
                formatter: function(value,row,index){
                    return value.xm;
                }
            },
            {
                title:'谈话时间',
                field:'time',
                sortable:true
            },
            {
                title:'谈话时长',
                field:'duration',
                sortable:true
            },
            {
                title:'谈话内容',
                field:'content'
            },
            {
                title:'谈话照片',
                field:'photo',
                formatter: function(value,row,index){
                    console.log("photo = " + value);
                    return '<img  src="'+value+'" class="img-rounded" />';
                }
            },
            {
                title:'谈话类型',
                field:'flag',
                align:'center',
                //列数据格式化
                formatter: function (value,row,index){
                    if(value==1){
                        return '<i class="fa fa-lock" style="color:red">异常谈话</i>'
                    }else if(value==0){
                        return '<i class="fa fa-unlock" style="color:green">正常谈话</i>'
                    }else{
                        return ''
                    }
                }
            },
            {
                title:'操作',
                align:'center',
                //列数据格式化
                formatter: function(value,row,index){
                    console.log("value = " + value);
                    console.log(row);
                    console.log("row.id -- > " + row.id);
                    console.log("index = " + index);
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
            content: '您确认要删除该谈话记录吗？',
            buttons: {
                删除: function(){
                    $.ajax({
                        type: "POST",
                        url:'${basePath}talk/manager/delTalkNotes',
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
        $('#mytab').bootstrapTable('refresh', {url: '${basePath}talk/manager/talkNotesList'});
    }

    //tableHeight函数
    function tableHeight() {
        //可以根据自己页面情况进行调整
        return $(window).height() - 280;
    }

    $("#leaderOrg").DropTree({
        'xOffset': 357,
        'yOffset': 139,
        'treePanle': 'leader_treePanle',
        'tree': 'leader_orgTree',
        'treeClose': 'leader_treeClose',
        'dataUrl': '${basePath}org/orgTree',
        'callback' : function(id){
            $("#leaderId").DropSelect({
                'dataUrl': '${basePath}policeman/dropSelect',
                'data': {
                    orgId: id
                }
            });
        }
    });

    $("#conversationOrg").DropTree({
        'xOffset': 357,
        'yOffset': 139,
        'treePanle': 'conversation_treePanle',
        'tree': 'conversation_orgTree',
        'treeClose': 'conversation_treeClose',
        'dataUrl': '${basePath}org/orgTree',
        'callback' : function(id){
            $("#conversationId").DropSelect({
                'dataUrl': '${basePath}policeman/dropSelect',
                'data': {
                    orgId: id
                }
            });
        }
    });
</script>
</html>
