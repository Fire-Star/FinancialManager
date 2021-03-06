<%--
  Created by IntelliJ IDEA.
  User: MoonFollow
  Date: 2017/10/11
  Time: 14:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>运营商查询</title>
    <!-- 引入 BootStrap 全家桶 -->
    <script src="../../res/js/jquery-3.2.1.min.js"></script>
    <script src="../../res/js/bootstrap.min.js"></script>
    <script src="../../res/js/bootstrap-table.js"></script>
    <script src="../../res/js/bootstrap-dialog.js"></script>
    <script src="../../res/js/bootstrap-datepicker.js"></script>
    <script src="../../res/js/bootstrap-datepicker.zh-CN.js"></script>
    <link rel="stylesheet" href="../../res/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="../../res/css/bootstrap-table.css"/>

    <!-- 引入Vue.js -->
    <script src="../../res/js/vue.min.js"></script>
    <style>
        body,html{
            height: 100%;
            overflow: auto;
        }
        p{
            margin: 0 !important;
        }
        .container {
            width: 100%;
            height: 100%;
        }
        .supplier-head{
            height: 50px;
            line-height: 50px;
        }
        .panel-body{
            padding:0px;
        }
        .row{
            margin: 0;
            padding-top: 10px;
            width: 100%;
        }
        .supplier-row .control-label{
            padding: 7px 0 0 7px;
            text-align: right;
        }
        .supplier-row .form-control{
            margin-bottom: 20px;
        }
        .con{
            vertical-align: middle;
            text-align: right;
        }
    </style>
</head>
<body>
<div class="app container">
    <div class="supplier-head">
        供应商管理 > 供应商查询 > 供应商信息
    </div>
    <div class="panel-body" style="padding-bottom:0px;">
        <div class="panel panel-default">
            <div class="panel-heading" style="position: relative;">
                <strong><p class="lead">供应商信息</p></strong>
                <security:authorize access="hasAnyRole('ROLE_ADMIN,ROLE_USER')">
                <button type="button" style="width: 160px;position: absolute;top: 8px;right: 20px;" id="btn_clear" class="btn btn-primary"
                        data-toggle="modal" data-target="#myModal" onclick = "editData()">编辑</button>
                </security:authorize>
            </div>
            <div class="panel-body">
                <form id="formSearch" class="form-horizontal">
                    <div class="form-group" style="margin:15px 0;">
                        <div class="row supplier-row">
                            <label class="control-label col-md-1 col-sm-2 col-xs-2" for="suppliername">供应商：</label>
                            <div class="col-md-2 col-sm-4 col-xs-4">
                                <input type="text" class="form-control" id="suppliername" placeholder="供应商..." readonly="enable">
                            </div>
                            <label class="control-label col-md-1 col-sm-2 col-xs-2" for="suppliercontactname">联系人：</label>
                            <div class="col-md-2 col-sm-4 col-xs-4">
                                <input type="text" class="form-control" id="suppliercontactname" placeholder="联系人..." readonly="enable">
                            </div>
                            <label class="control-label col-md-1 col-sm-2 col-xs-2" for="contacttel">联系电话：</label>
                            <div class="col-md-2 col-sm-4 col-xs-4">
                                <input type="text" class="form-control" id="contacttel" placeholder="电话..." readonly="enable">
                            </div>
                            <label class="control-label col-md-1 col-sm-2 col-xs-2" for="time">签约时间:</label>
                            <div class="col-md-2 col-sm-4 col-xs-4">
                                <input type="text" class="form-control" id="time" placeholder="签约时间..." readonly="enable">
                            </div>
                        </div>
                        <div class="row custom">
                            <div class="col-md-3 col-sm-6 col-xs-6" style="padding: 0;" v-for="(value,key) in custom">
                                <label class="control-label col-md-4 col-sm-4 col-xs-4" :title="key">{{key + "："}}</label>
                                <div class="col-md-8 col-sm-8 col-xs-8">
                                    <input type="text" class="form-control" v-model="custom[key]" readonly="enable">
                                </div>
                            </div>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
    <div style="height: 3%">
        <p>
        <h4>设备采购记录</h4>
        </p>
    </div>
    <div id="toolbar" class="btn-group">
        <button id="btn_result" type="button" class="btn btn-default">
            <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>导出结果
        </button>
        <button id="btn_print" type="button" class="btn btn-default">
            <span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>打印
        </button>
    </div>
    <div class="tablecontent">
        <table class="bootstrap-table fixed-table-container" id="tb_departments"></table>
    </div>
    <!-- Modal 弹出框的结构 -->
    <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">×</span></button>
                    <h4 class="modal-title" id="myModalLabel">供应商信息修改</h4>
                </div>
                <div class="modal-body" style="max-height: 385px;overflow-y: auto;">
                    <form id="formSubmit" class="form-horizontal">
                        <div class="form-group row">
                            <label class="control-label item-left col-sm-4 col-xs-4 con">供应商ID：</label>
                            <div class="col-sm-7 col-xs-7">
                                <input type="text" id="submitSupId" class="form-control form-control-x" name="phone"
                                       value="" disabled="true">
                            </div>
                        </div>
                        <div class="form-group row">
                            <label class="control-label item-left col-sm-4 col-xs-4 con" for="submitSupName">
                                供应商：</label>
                            <div class="col-sm-7 col-xs-7">
                                <input class="form-control form-control-x" propName="供应商" id="submitSupName"
                                       name="entryTime" placeholder="供应商..." type="text" v-model="supName">
                            </div>
                        </div>
                        <div class="form-group row">
                            <label class="control-label item-left col-sm-4 col-xs-4 con"
                                   for="supSubmitCont">联系人：
                            </label>
                            <div class="col-sm-7 col-xs-7">
                                <input class="form-control form-control-x" propName="联系人" id="supSubmitCont"
                                       name="entryTime" placeholder="联系人..." type="text" v-model="supCont">
                            </div>
                        </div>
                        <div class="form-group row">
                            <label class="control-label item-left col-sm-4 col-xs-4 con" for="supSubmitTel">电话：
                            </label>
                            <div class="col-sm-7 col-xs-7">
                                <input class="form-control form-control-x" propName="电话" id="supSubmitTel"
                                       name="entryTime" placeholder="电话..." type="text" v-model="supTel">
                            </div>
                        </div>
                        <div class="form-group row">
                            <label class="control-label item-left col-sm-4 col-xs-4 con" for="supSubmitDate">
                                签约时间：</label>
                            <div class="col-sm-7 col-xs-7">
                                <input class="form-control form-control-x" propName="签约时间" id="supSubmitDate"
                                       name="entryTime" placeholder="选择签约时间..." type="text" v-model="supDate">
                            </div>
                        </div>
                        <div class="form-group row" v-for="(value,key) in custom">
                            <label class="control-label item-left col-sm-4 col-xs-4 con">{{key + "："}}</label>
                            <div class="col-sm-7 col-xs-7">
                                <input class="form-control form-control-x" v-model="custom[key]" type="text">  
                            </div>
                        </div>
                    </form>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                    <button type="button" class="btn btn-primary" @click="submitEditSup()">提交</button>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
<script>
    var data = {
        supName:"",
        supCont:"",
        supTel:"",
        supDate:"",
        custom:{}
    };
    var vm = new Vue({
        el:'.app',
        data:data
    });
    $(function () {
        //1.初始化Table
        var oTable = new TableInit();
        oTable.Init();
        //2.初始化Button的点击事件
        var oButtonInit = new ButtonInit();
        oButtonInit.Init();
    });
    var TableInit = function () {
        var oTableInit = new Object();
        //初始化Table
        oTableInit.Init = function () {
            $('#tb_departments').bootstrapTable({
                url: 'findEquipBySuppId',         //请求后台的URL（*）
                method: 'post',                      //请求方式（*）
                contentType : "application/x-www-form-urlencoded",//因为如果不指定contentType 的话就会默认以application/json的方式发送数据，java就不能用getParameter拿到内容了，但是可以用输入流接收
                toolbar: '#toolbar',                //工具按钮用哪个容器
                striped: true,                      //是否显示行间隔色
                // cache: false,                       //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
                pagination: true,                   //是否显示分页（*）
                // sortable: false,                     //是否启用排序
                // sortOrder: "asc",                   //排序方式
                queryParams: oTableInit.queryParams,//传递参数（*）
                sidePagination: "client",           //分页方式：client客户端分页，server服务端分页（*）
                pageNumber:1,                       //初始化加载第一页，默认第一页
                pageSize: 8,                       //每页的记录行数（*）
                pageList: [8, 25, 50, 100],        //可供选择的每页的行数（*）
                // search: true,                       //是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大
                strictSearch: true,
                showColumns: true,                  //是否显示所有的列
                showRefresh: true,                  //是否显示刷新按钮
                minimumCountColumns: 2,             //最少允许的列数
                clickToSelect: true,                //是否启用点击选中行
                //   height: 400,                        //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
                uniqueId: "ID",                     //每一行的唯一标识，一般为主键列
                showToggle:true,                    //是否显示详细视图和列表视图的切换按钮
                cardView: false,                    //是否显示详细视图
                detailView: false,                   //是否显示父子表
                columns: [{
                    checkbox: true
                }, {
                    field: 'index',
                    title: '序列号'
                }, {
                    field: 'equipID',
                    title: '设备ID',
                    formatter:function(value,row,index) {
                        var e = '<a href="#" mce_href="#" onclick="checkToEqDetail(\'' + row.id + '\')">' + value +
                            '</a> ';
                        return e;
                    }
                }, {
                    field: 'equipType',
                    title: '设备类型'
                }, {
                    field: 'equipName',
                    title: '设备名称'
                }, {
                    field: 'brand',
                    title: '品牌'
                }, {
                    field: 'purchasDepart',
                    title: '采购部门'
                }, {
                    field: 'belongDepart',
                    title: '归属部门'
                }, {
                    field: 'state',
                    title: '状态'
                }, {
                    field: 'purchasDate',
                    title: '采购时间'
                }, {
                    field: 'purchasPrice',
                    title: '采购价格'
                }, {
                    field: 'fixTime',
                    title: '维修次数'
                }, {
                    field: 'useTime',
                    title: '使用年限'
                }]
            });
        };
        //得到查询的参数
        oTableInit.queryParams = function (params) {
            var suppId = $.getUrlParam('suppId');
            var temp = {   //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
                limit: params.limit,   //页面大小
                offset: params.offset,  //页码
                suppId: suppId
            };
            return temp;
        };
        return oTableInit;
    };
    var ButtonInit = function () {
        var oInit = new Object();
        var postdata = {};
        oInit.Init = function () {
            //初始化页面上面的按钮事件

        };
        return oInit;
    };
    function editData() {
        vm.supName = preSupName;
        vm.supCont = preSupCont;
        vm.supTel = preSupTel;
        vm.supDate = preSupDate;
    }
    $('#supSubmitDate').datepicker({
        language: 'zh-CN',
        autoclose: true,
        todayHighlight: true
    });
    $(document).ready(function(){
        var suppId = $.getUrlParam('suppId');
        var param = {"suppId":suppId};
        $.ajax({
            url: "findSuppBySuppID",
            type: 'post',
            dataType: 'json',
            timeout: 1000,
            data:param,
            success: function (data, status) {
                if(data.customMessage !== ''){
                    vm.custom = Object.assign({},vm.custom,JSON.parse(data.customMessage));
                }
                $('#suppliername').val(data.name);
                $('#suppliercontactname').val(data.contactName);
                $('#contacttel').val(data.tel);
                $('#time').val(data.contractTime);
                //加载员工信息修改界面默认值
                $('#submitSupId').val(data.id);
                preSupName = data.name;
                preSupCont = data.contactName;
                preSupTel = data.tel;
                preSupDate = data.contractTime;
            },
            fail: function (err, status) {
                //alert("741147");
            }
        });
    });
    (function($){
        $.getUrlParam = function(name){
            var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
            var r = window.location.search.substr(1).match(reg);
            if (r != null) return decodeURI(r[2]); return null;
        }
    })(jQuery);
    function checkToEqDetail(equipId) {
        window.parent.document.getElementById('iframeContent').src="equip/detail?equipId="+equipId;
        // alert(equipId);
    }
    function checkDetail(suppId) {
        window.parent.document.getElementById('iframeContent').src="supplier/detail?suppId="+suppId;
    }
    function submitEditSup() {
        var supIdValue = $('#submitSupId').val();
        var supNameValue = vm.supName;
        var supContValue = vm.supCont;
        var suptelValue = vm.supTel;
        var supDateValue = $('#supSubmitDate').val();
        var customMessage = "";
        for(var key in vm.custom){
            if(vm.custom[key] == ""){
                alert("编辑设备信息时，字段不能为空！！");
                return;
            }
            customMessage += key+"="+vm.custom[key]+";";
        }
        //修改日期检查
        var supDateFormatValue = new   Date(Date.parse(supDateValue.replace(/(\d{4}).(\d{1,2}).(\d{1,2}).+/mg,
            '$1-$2-$3')));
        var curDate = new Date();
        if(curDate <= supDateFormatValue) {
            $('#supSubmitDate').parent().addClass('has-error');
            $('#supSubmitDate').tooltip({
                title: "购买时间不能小于当前时间！请重新填写！"
            });
            return ;
        }
        if(supIdValue==""||supNameValue==""||supContValue==""||suptelValue==""||supDateValue==""){
            alert("编辑设备信息时，字段不能为空！！");
        }else {
            $.post("../../supplier/editSupDetail",
                {
                    supId : supIdValue,
                    supName : supNameValue,
                    supCont : supContValue,
                    supTel : suptelValue,
                    supDate : supDateValue,
                    customMessage : customMessage
                },
                function(data,status) {
                    var _data = data;
                    if(eval(_data)){
                        alert(eval(_data).success);
                        checkDetail(supIdValue);
                    }else{
                        alert(_data);
                    }
                });
        }
        $("#myModal").modal('hide');
    }
</script>
</html>