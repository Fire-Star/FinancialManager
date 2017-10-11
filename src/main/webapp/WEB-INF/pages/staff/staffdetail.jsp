<%--
  Created by IntelliJ IDEA.
  User: MoonFollow
  Date: 2017/10/11
  Time: 14:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>员工查询</title>
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
        .staffDetailRow .control-label{
            padding: 7px 0 0 7px;
            text-align: right;
        }
        .staffDetailRow .form-control{
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
        员工管理 > 员工查询 > 员工信息
    </div>
    <div class="panel-body" style="padding-bottom:0px;">
        <div class="panel panel-default">
            <div class="panel-heading" style="position: relative;">
                <strong><p class="lead">员工信息</p></strong>
                <security:authorize access="hasAnyRole('ROLE_ADMIN,ROLE_USER')">
                <button type="button" style="width: 160px;position: absolute;top: 8px;right: 20px;" id="btn_clear" class="btn btn-primary"
                        data-toggle="modal" data-target="#myModal" onclick = "editData()">编辑</button>
                </security:authorize>
            </div>
            <div class="panel-body">
                <form id="formSearch" class="form-horizontal">
                    <div class="form-group" style="margin:15px 0;">
                        <div class="row staffDetailRow">
                            <label class="control-label col-md-1 col-sm-2 col-xs-2" for="name">姓名：</label>
                            <div class="col-md-2 col-sm-4 col-xs-4">
                                <input type="text" class="form-control" id="name" placeholder="姓名..." readonly="enable">
                            </div>
                            <label class="control-label col-md-1 col-sm-2 col-xs-2" for="department">部门：</label>
                            <div class="col-md-2 col-sm-4 col-xs-4">
                                <input type="text" class="form-control" id="department" placeholder="部门..." readonly="enable">
                            </div>
                            <label class="control-label col-md-1 col-sm-2 col-xs-2" for="position">岗位:</label>
                            <div class="col-md-2 col-sm-4 col-xs-4">
                                <input type="text" class="form-control" id="position" placeholder="岗位..." readonly="enable">
                            </div>
                            <label class="control-label col-md-1 col-sm-2 col-xs-2" for="tel">联系电话:</label>
                            <div class="col-md-2 col-sm-4 col-xs-4">
                                <input type="text" class="form-control" id="tel" placeholder="联系电话..." readonly="enable">
                            </div>
                            <label class="control-label col-md-1 col-sm-2 col-xs-2" for="entrytime">入职时间:</label>
                            <div class="col-md-2 col-sm-4 col-xs-4">
                                <input type="text" class="form-control" id="entrytime" placeholder="入职时间..." readonly="enable">
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
    <div id="toolbar" class="btn-group">
        <div class="col-sm-12">
            <p><h4>设备借调记录</h4></p>
        </div>
    </div>
    <div class="tablecontent">
        <table class="bootstrap-table fixed-table-container" id="tb_staff_eq"></table>
    </div>
    <!-- Modal 弹出框的结构 -->
    <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">×</span></button>
                    <h4 class="modal-title" id="myModalLabel">员工信息修改</h4>
                </div>
                <div class="modal-body" style="max-height: 385px;overflow-y: auto;">
                    <form id="formSubmit" class="form-horizontal">
                        <div class="form-group row">
                            <label class="control-label item-left col-sm-4 col-xs-4 con">员工ID：</label>
                            <div class="col-sm-7 col-xs-7">
                                <input type="text" id="staffSubmitId" class="form-control" name="phone" value=""
                                       disabled="true">
                            </div>

                        </div>
                        <div class="form-group row">
                            <label class="control-label item-left col-sm-4 col-xs-4 con">归属部门：</label>
                            <div class="col-sm-7 col-xs-7" >
                                <!-- <div class="form-group form-inline from-line item-right" role="group"> -->
                                <div class="btn-group" style="width: 100%;margin: 0;">
                                    <select style="width: 50%" class="btn btn-default form-control select-left" id="staffSubmitCity"
                                            data-toggle="tooltip" data-placement="top" v-model="city" @change="loadBelongDep">
                                        <option selected value="" id="d">-城市-</option>
                                        <option v-for="(item , index) in citys" :value="item.city">{{item.city}}</option>
                                    </select>
                                    <select style="width: 50%" class="btn btn-default form-control select-right"
                                            id="staffSubmitBelongDepart" data-toggle="tooltip" v-model="beDepValue"
                                            data-placement="top" >
                                        <option selected value="" id="dsf">--部门--</option>
                                        <option v-for="(item , index) in belongDepartment" :value="item.department">{{item.department}}</option>
                                    </select>
                                </div>
                                <!-- </div> -->
                            </div>
                        </div>
                        <div class="form-group row">
                            <label class="control-label item-left col-sm-4 col-xs-4 con" for="staffSubmitPosition">
                                岗位：</label>
                            <div class="col-sm-7 col-xs-7">
                                <input class="form-control form-control-x" propName="岗位" id="staffSubmitPosition"
                                       name="entryTime" placeholder="选择岗位..." type="text" v-model="prePosition">
                            </div>
                        </div>
                        <div class="form-group row">
                            <label class="control-label item-left col-sm-4 col-xs-4 con" for="staffSubmitTel">电话：</label>
                            <div class="col-sm-7 col-xs-7">
                                <input class="form-control form-control-x" propName="电话" id="staffSubmitTel"
                                       name="entryTime" placeholder="电话..." type="text" v-model="preTel">
                            </div>
                        </div>
                        <div class="form-group row">
                            <label class="control-label item-left col-sm-4 col-xs-4 con" for="staffSubmitDate">签约时间：</label>
                            <div class="col-sm-7 col-xs-7">
                                <input class="form-control form-control-x" propName="签约时间" id="staffSubmitDate"
                                       name="entryTime" placeholder="选择签约时间..." type="text" v-model="preDate">
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
                    <button type="button" class="btn btn-primary">提交</button>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
<script>
    var data = {
        citys:[],
        city:"",
        belongDepartment:[],
        beDepValue:"",
        prePosition:"",
        preTel:"",
        preDate:"",
        custom:{}
    };
    var vm = new Vue({
        el:'.app',
        data:data,
        methods:{
            loadBelongDep(){
                $.get("../../department/findDepartment?city=" + vm.city, function (data, status) {
                    console.log(data);
                    if (status == "success") {
                        var _departmentData = eval(data);
                        console.log(_departmentData);
                        vm.belongDepartment = _departmentData;
                    }
                });
            },
        }
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
            $('#tb_staff_eq').bootstrapTable({
                url: 'findEqByStaffId',         //请求后台的URL（*）
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
                pageSize: 5,                       //每页的记录行数（*）
                pageList: [5, 15, 25, 50],        //可供选择的每页的行数（*）
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
                    field: 'eqOtherID',
                    title: '设备ID',
                    formatter:function(value,row,index){
                        var e = '<a href="#" mce_href="#" onclick="checkDetail(\''+ row.eqId + '\')">'+value+'</a> ';
                        return e ;
                    }
                }, {
                    field: 'eqName',
                    title: '设备名称'
                }, {
                    field: 'opratorTime',
                    title: '操作时间'
                }, {
                    field: 'status',
                    title: '状态'
                }]
            });
        };
        //得到查询的参数
        oTableInit.queryParams = function (params) {
            var staffId = $.getUrlParam('staffId');
            var temp = {   //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
                limit: params.limit,   //页面大小
                offset: params.offset,  //页码
                staffId: staffId
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
    function checkDetail(equipId) {
        window.parent.document.getElementById('iframeContent').src="equip/detail?equipId="+equipId;
    }
    function editData() {
        vm.city = preStaffCity;
        vm.beDepValue = preStaffDepartment;
        vm.prePosition = preStaffPosition;
        vm.preTel = preStafftel;
        vm.preDate = preStaffDate;
    };
    function preLoadBelongDep() {
        $.get("../../department/findDepartment?city=" + vm.city, function (data, status) {
            console.log(data);
            if (status == "success") {
                var _departmentData = eval(data);
                console.log(_departmentData);
                vm.belongDepartment = _departmentData;
            }
        });
        // $('#depIDOp').attr('selected', true);
    };
    $('#staffSubmitDate').datepicker({
        language: 'zh-CN',
        autoclose: true,
        todayHighlight: true
    });
    $(document).ready(function(){
        //alert("员工详情界面数据预加载");
        var staffId = $.getUrlParam('staffId');
        var param = {"staffId":staffId};
        $.ajax({
            url: "findStaffByStaffID",
            type: 'post',
            dataType: 'json',
            timeout: 1000,
            data:param,
            success: function (data, status) {
                if(data.customMessages !== ''){
                    vm.custom = Object.assign({},vm.custom,JSON.parse(data.customMessages));
                }
                $('#name').val(data.name);
                $('#department').val(data.dep);
                $('#position').val(data.position);
                $('#tel').val(data.tel);
                $('#entrytime').val(data.entryTime);
                //加载员工信息修改界面默认值
                $('#staffSubmitId').val(data.staffId);
                preStaffCity = data.city;
                vm.city = data.city;
                preLoadBelongDep();
                preStaffDepartment = data.dep;
                preStaffPosition = data.position;
                preStafftel = data.tel;
                preStaffDate = data.entryTime;
            },
            fail: function (err, status) {
                alert("获取员工详情失败");
            }
        });
    });
    /**
     * 加载当前用户的所有城市
     */
    $.get("../../city/findAllCity",function (data, status) {
        if(status=="success"){
            var _cityData = eval(data);
            vm.citys = _cityData;
        }
    });
    (function($){
        $.getUrlParam = function(name){
            var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
            var r = window.location.search.substr(1).match(reg);
            if (r != null) return decodeURI(r[2]); return null;
        }
    })(jQuery);
</script>
</html>