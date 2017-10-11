<%--
  Created by IntelliJ IDEA.
  User: MoonFollow
  Date: 2017/10/11
  Time: 14:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>设备信息</title>
    <!-- 引入 BootStrap 全家桶 -->
    <script src="../../res/js/jquery-3.2.1.min.js"></script>
    <script src="../../res/js/bootstrap.min.js"></script>
    <script src="../../res/js/bootstrap-table.js"></script>
    <script src="../../res/js/bootstrap-dialog.js"></script>
    <script src="../../res/js/bootstrap-datepicker.js"></script>
    <script src="../../res/js/bootstrap-datepicker.zh-CN.js"></script>
    <link rel="stylesheet" href="../../res/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="../../res/css/bootstrap-table.css"/>
    <link rel="stylesheet" href="../../res/css/bootstrap-dialog.css"/>


    <!-- 引入Vue.js -->
    <script src="../../res/js/vue.min.js"></script>
    <script src="../../res/js/loadash.js"></script>
    <style>
        body,html{
            height: 100%;
            /*background-color: #f1f1f1;*/
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
            /*height: 30%;*/
        }
        .row{
            padding-top: 10px;
            margin: 0;
            width: 100%;
        }
        #formSearch .control-label{
            padding: 7px 0 0 7px;
            text-align: right;
            white-space: nowrap;
            text-overflow: ellipsis;
            overflow: hidden;
        }
        #formSearch .form-control{
            margin-bottom: 20px;
        }
        .tablecontent_borrow{
            margin-bottom: 50px;
        }
        .con{
            /*vertical-align: middle;*/
            text-align: right;
        }
        /*.col-sm-3{
            padding-left: 0px;
        }*/
        .addAreaDiv{
            padding-left: 0px;
        }
        .addBorRow{
            padding-top: 20px;
        }
        .borDiv{
            height: 100%;
            width: 100%;
            margin-top: 10px;
        }
    </style>
</head>
<body>
<div class="app container">
    <div class="supplier-head">
        设备管理 > 设备查询 > 设备信息
    </div>
    <div class="panel-body" style="padding-bottom:0px;">
        <div class="panel panel-default">
            <div class="panel-heading" style="position: relative;">
                <strong><p class="lead">设备信息</p></strong>
                <security:authorize access="hasAnyRole('ROLE_ADMIN,ROLE_USER')">
                <button type="button" style="width: 160px;position: absolute;top: 8px;right: 20px;" id="btn_clear" class="btn btn-primary"
                        data-toggle="modal" data-target="#myModal" onclick = "editData()">编辑</button>
                </security:authorize>
            </div>
            <div class="panel-body">
                <form id="formSearch" class="form-horizontal">
                    <div class="form-group" style="margin:15px 0;">
                        <div class="row">
                            <label class="control-label col-md-1 col-sm-2 col-xs-2" for="eqID">设备ID：</label>
                            <div class="col-md-2 col-sm-4 col-xs-4">
                                <input type="text" class="form-control" id="eqID" placeholder="ID..." readonly="enable">
                            </div>
                            <label class="control-label col-md-1 col-sm-2 col-xs-2" for="eqType">设备类型：</label>
                            <div class="col-md-2 col-sm-4 col-xs-4">
                                <input type="text" class="form-control" id="eqType" placeholder="类型..." readonly="enable">
                            </div>
                            <label class="control-label col-md-1 col-sm-2 col-xs-2" for="eqName">设备名称：</label>
                            <div class="col-md-2 col-sm-4 col-xs-4">
                                <input type="text" class="form-control" id="eqName" placeholder="名称..." readonly="enable">
                            </div>
                            <label class="control-label col-md-1 col-sm-2 col-xs-2" for="supplier">供应商：</label>
                            <div class="col-md-2 col-sm-4 col-xs-4">
                                <input type="text" class="form-control" id="supplier" placeholder="供应商..." readonly="enable">
                            </div>
                            <label class="control-label col-md-1 col-sm-2 col-xs-2" for="belongDepart">归属部门：</label>
                            <div class="col-md-2 col-sm-4 col-xs-4">
                                <input type="text" class="form-control" id="belongDepart" placeholder="部门..." readonly="enable">
                            </div>
                            <label class="control-label col-md-1 col-sm-2 col-xs-2" for="eqState">当前状态：</label>
                            <div class="col-md-2 col-sm-4 col-xs-4">
                                <input type="text" class="form-control" id="eqState" placeholder="状态..." readonly="enable">
                            </div>
                            <label class="control-label col-md-1 col-sm-2 col-xs-2" for="time">购买时间：</label>
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
    <div id="borrow_toolbar">
        <span style="font-size: 21px;">设备借调记录</span>
        <security:authorize access="hasAnyRole('ROLE_ADMIN,ROLE_USER')">
        <button type="button" class="btn btn-default" style="margin-left:50px" onclick = "addBorrowLog()"
                data-toggle="modal" data-target="#borrowModal">
            <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>添加借调记录
        </button>
        </security:authorize>
    </div>
    <div class="tablecontent_borrow">
        <table class="bootstrap-table fixed-table-container" id="tb_equipment_borrow"></table>
    </div>
    <div id="fix_toolbar">
        <span style="font-size: 21px;">设备维护记录</span>
        <security:authorize access="hasAnyRole('ROLE_ADMIN,ROLE_USER')">
        <button type="button" class="btn btn-default" style="margin-left:50px" onclick = "addFixLog()"
                data-toggle="modal" data-target="#fixModal">
            <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>添加维护记录
        </button>
        </security:authorize>
    </div>
    <div class="tablecontent_fix">
        <table class="bootstrap-table fixed-table-container" id="tb_equipment_fix"></table>
    </div>
    <!-- 编辑设备详情Modal-->
    <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">×</span></button>
                    <h4 class="modal-title" id="myModalLabel">设备信息修改</h4>
                </div>
                <div class="modal-body" style="height: 385px;overflow-y: auto;">
                    <form id="formSubmit" class="form-horizontal">
                        <div class="form-group row">
                            <label class="control-label item-left col-sm-4 col-xs-4 con">设备ID：</label>
                            <div class="col-sm-7 col-xs-7">
                                <input type="text" id="eqSubmitId" class="form-control" name="phone" value="" disabled="true">
                            </div>

                        </div>
                        <div class="form-group row">
                            <label class="control-label item-left col-sm-4 col-xs-4 con">设备其他ID：</label>
                            <div class="col-sm-7 col-xs-7">
                                <input type="text" id="eqSubmitOtherId" class="form-control" name="phone" value="" disabled="true">
                            </div>

                        </div>
                        <div class="form-group row">
                            <label class="control-label item-left col-sm-4 col-xs-4 con" for="eqSubmitType">设备类型：</label>
                            <div class="col-sm-7 col-xs-7">
                                <select class="form-control" propName="设备类型" id="eqSubmitType" @change="loadEqName" v-model="eqTypeValue"
                                        data-toggle="tooltip" data-placement="top">
                                    <option selected value="">---请选择设备类型---</option>
                                    <option v-for="(item , index) in equipmentType" :value="item.equipmentTypeName">{{item.equipmentTypeName}}</option>
                                </select>
                            </div>
                        </div>
                        <div class="form-group row">
                            <label class="control-label item-left col-sm-4 col-xs-4 con" for="eqSubmitName">设备名称：</label>
                            <div class="col-sm-7 col-xs-7">
                                <select class="form-control" propName="设备名称" id="eqSubmitName" v-model="eqNameValue" data-toggle="tooltip" data-placement="top">
                                    <option selected value="">---请选择设备名称---</option>
                                    <option v-for="(item , index) in equipmentName" :value="item.eqName">{{item.eqName}}</option>
                                </select>
                            </div>
                        </div>
                        <div class="form-group row">
                            <label class="control-label item-left col-sm-4 col-xs-4 con" for="eqSubmitSupplier">供应商：</label>
                            <div class="col-sm-7 col-xs-7">
                                <select class="form-control" propName="供应商" id="eqSubmitSupplier" v-model="supplier"
                                        data-toggle="tooltip" data-placement="top">
                                    <option selected value="">---请选择设供应商---</option>
                                    <option v-for="(item , index) in suppliers" :value="item.name">{{item.name}}</option>
                                </select>
                            </div>
                        </div>
                        <div class="form-group row">
                            <label class="control-label item-left col-sm-4 col-xs-4 con">归属部门：</label>
                            <div class="col-sm-7 col-xs-7" >
                                <div class="form-inline" role="group">
                                    <div class="btn-group" style="width: 100%">
                                        <select class="btn btn-default form-control select-left" id="eqSubmitCity"
                                                data-toggle="tooltip" data-placement="top" @change="loadBelongDep"
                                                v-model="city" style="width: 50%">
                                            <option selected value="" id="d">-城市-</option>
                                            <option v-for="(item , index) in citys" :value="item.city">{{item.city}}</option>
                                        </select>
                                        <select class="btn btn-default form-control select-right"
                                                id="eqSubmitBelongDepart" data-toggle="tooltip" v-model="beDepValue"
                                                data-placement="top" style="width: 50%">
                                            <option selected value="" id="dsf">--部门--</option>
                                            <option v-for="(item , index) in belongDepartment" :value="item.department">{{item.department}}</option>
                                        </select>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="form-group row">
                            <label class="control-label item-left col-sm-4 col-xs-4 con" for="eqSubmitState">当前状态：</label>
                            <div class="col-sm-7 col-xs-7">
                                <select class="form-control" propName="设备状态" id="eqSubmitState" v-model="eqState"
                                        data-toggle="tooltip" data-placement="top">
                                    <option selected value="">---请选择设备状态---</option>
                                    <option v-for="(item , index) in allState" :value="item.state">{{item.state}}</option>
                                </select>
                            </div>
                        </div>
                        <div class="form-group row">
                            <label class="control-label item-left col-sm-4 col-xs-4 con" for="eqSubmitDate">购买时间：</label>
                            <div class="col-sm-7 col-xs-7">
                                <input class="form-control form-control-x" propName="采购时间" id="eqSubmitDate" name="entryTime" placeholder="选择采购时间..." type="text">
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
                    <button type="button" class="btn btn-primary" @click="submitEditEq()">提交</button>
                </div>
            </div>
        </div>
    </div>

    <div class="borrow-modal modal fade" id="borrowModal" tabindex="-1" role="dialog"
         aria-labelledby="borrowModalLabel">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">×</span></button>
                    <h4 class="modal-title" id="borrowModalLabel">设备借调</h4>
                </div>
                <div class="modal-body addBorRow">
                    <div style="height: 200px">
                        <div class="row">
                            <div class="col-sm-4 col-xs-4">
                                <label class="radio-inline">
                                    <input id="eqUsed" type="radio" name="borrowState" value="使用">使用
                                </label>
                            </div>
                            <div class="col-sm-4 col-xs-4">
                                <label class="radio-inline">
                                    <input id="eqNoUsed" type="radio" name="borrowState" value="闲置">闲置
                                </label>
                            </div>
                            <div class="col-sm-4 col-xs-4">
                                <label class="radio-inline">
                                    <input id="eqUnUsed" type="radio" name="borrowState" value="报废">报废
                                </label>
                            </div>
                        </div>
                        <div class="borDiv">
                            <form id="formBorrowSubmit" class="form-horizontal">
                                <div id="eqUsedDiv" hidden>
                                    <div class="form-group row">
                                        <label class="control-label item-left col-sm-3 col-xs-3 con">使用人：</label>
                                        <div class="col-sm-8 col-xs-8">
                                            <div class="form-group form-inline from-line item-right" role="group">
                                                <div class="btn-group">
                                                    <select class="btn btn-default select-left"
                                                            id="eqBorrowCity"
                                                            data-toggle="tooltip" data-placement="top"
                                                            @change="loadBorBelongDep" v-model="eqBorCityValue">
                                                        <option selected value="">-城市-</option>
                                                        <option v-for="(item , index) in citys" :value="item.city">{{item.city}}</option>
                                                    </select>
                                                    <select class="btn btn-default select-center"
                                                            id="eqBorrowBelongDepart" data-toggle="tooltip"
                                                            @change="loadBorUserName" v-model="eqBorBelongValue" data-placement="top" >
                                                        <option selected value="">--部门--</option>
                                                        <option v-for="(item , index) in eqBorBelong" :value="item.department">{{item.department}}</option>
                                                    </select>
                                                    <select class="btn btn-default select-right"
                                                            id="eqAddBorUsedUser" data-toggle="tooltip"
                                                            v-model="eqBorNameValue"
                                                            data-placement="top" >
                                                        <option selected value="">--使用人--</option>
                                                        <option v-for="(item , index) in eqBorName" :value="item.staffId">
                                                            {{item.name}}</option>
                                                    </select>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="form-group row">
                                        <label class="item-left col-sm-3 col-xs-3 con">备注：</label>
                                        <div class="col-sm-8 col-xs-8 addAreaDiv">
                                    <textarea class="form-control addAreaDiv" rows="3" id="eqAddBorUsedDetail"
                                              name="eqAddBorUsedDetail" v-model="eqBorDetailValue"></textarea>
                                        </div>
                                    </div>
                                </div>
                                <div id="eqNoUsedDiv" hidden>
                                    <div class="form-group row">
                                        <label class="control-label item-left col-sm-3 col-xs-3 con">闲置部门：</label>
                                        <div class="col-sm-8 col-xs-8">
                                            <div class="form-group form-inline from-line item-right" role="group">
                                                <div class="btn-group">
                                                    <select class="btn btn-default select-left"
                                                            id="eqAddBorNoUse"
                                                            data-toggle="tooltip" data-placement="top"
                                                            @change="loadBorNoBelongDep" v-model="eqBorNoCityValue">
                                                        <option selected value="">-城市-</option>
                                                        <option v-for="(item , index) in citys" :value="item.city">{{item.city}}</option>
                                                    </select>
                                                    <select class="btn btn-default select-"
                                                            id="eqAddBorNoUsedUser" data-toggle="tooltip" v-model="eqBorNoBelongValue" data-placement="top" >
                                                        <option selected value="">--部门--</option>
                                                        <option v-for="(item , index) in eqBorNoBelong" :value="item.department">{{item.department}}</option>
                                                    </select>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="form-group row">
                                        <label class="item-left col-sm-3 col-xs-3 con">备注：</label>
                                        <div class="col-sm-8 col-xs-8 addAreaDiv">
                                    <textarea class="form-control addAreaDiv" rows="3" id="eqAddBorNoUsedDetail"
                                              name="eqAddBorNoUsedDetail" v-model="eqBorNoDetailValue"></textarea>
                                        </div>
                                    </div>
                                </div>
                                <div id="eqUnUsedDiv" hidden style="margin-top: 35px;">
                                    <div class="form-group row">
                                        <label class="item-left col-sm-3 col-xs-3 con">备注：</label>
                                        <div class="col-sm-8 col-xs-8 addAreaDiv">
                                    <textarea class="form-control addAreaDiv" rows="3" id="eqAddBorUnUsedDetail"
                                              name="eqAddBorUnUsedDetail" v-model="eqBorUnDetailValue"></textarea>
                                        </div>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                        <button type="button" class="btn btn-primary" @click="submitBorlog()">提交</button>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <div class="fix-modal modal fade" id="fixModal" tabindex="-1" role="dialog" aria-labelledby="fixModalLabel">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">×</span></button>
                    <h4 class="modal-title" id="fixModalLabel">维护记录</h4>
                </div>
                <div class="modal-body">
                    <form id="formfixSubmit" class="form-horizontal">
                        <div class="form-group row">
                            <label class="item-left col-sm-3 col-xs-3 con" style="padding-left: 20px;">维护时间：</label>
                            <div class="col-sm-8 col-xs-8">
                                <input type="text" id="eqAddFixDate" class="form-control" name="eqAddFixDate" value="">
                            </div>
                        </div>
                        <div class="form-group row">
                            <label class="item-left col-sm-3 col-xs-3 con" style="padding-left: 20px;">维护类型：</label>
                            <div class="col-sm-8 col-xs-8">
                                <input type="text" id="eqAddFixType" class="form-control" name="eqAddFixType">
                            </div>
                        </div>
                        <div class="form-group row">
                            <label class="item-left col-sm-3 col-xs-3 con" style="padding-left: 20px;">维护详情：</label>
                            <div class="col-sm-8 col-xs-8 addAreaDiv">
                                <textarea class="form-control addAreaDiv" rows="3" id="eqAddFixDetail"
                                          name="eqAddFixDetail"></textarea>
                            </div>
                        </div>
                    </form>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                    <button type="button" class="btn btn-primary" @click="submitFixlog()">提交</button>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
<script>
    var data = {
        equipmentType:"",
        equipmentName:"",
        eqNameValue:"",
        eqTypeValue:"",
        belongDepartment:[],
        beDepValue:"",
        citys:[],
        city:"",
        suppliers :[],
        supplier:"",
        allState:[],
        eqState:"",
        //设备借调记录的数据使用
        eqBorName:[],
        eqBorBelong:[],
        eqBorNameValue:"",
        eqBorCityValue:"",
        eqBorBelongValue:"",
        eqBorDetailValue:"",
        //设备借调记录的数据闲置
        eqBorNoBelong:[],
        eqBorNoCityValue:"",
        eqBorNoBelongValue:"",
        eqBorNoDetailValue:"",
        //
        eqBorUnDetailValue:"",
        custom:{}
    };
    var vm = new Vue({
        el:'.app',
        data:data,
        methods: {
            loadEqName() {
                /**
                 * 加载设备名称选项
                 */
                $.get("../../findAllEquipmentNameByEqTypeName?eqTypeId=" + vm.eqTypeValue, function (data, status) {
                    if (status == "success") {
                        var _equipmentName = eval(data);
                        console.log(_equipmentName);
                        vm.equipmentName = _equipmentName;
                    }
                });
            },
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
            loadBorBelongDep(){
                $.get("../../department/findDepartment?city=" + vm.eqBorCityValue, function (data, status) {
                    console.log(data);
                    if (status == "success") {
                        var _departmentData = eval(data);
                        console.log(_departmentData);
                        vm.eqBorBelong = _departmentData;
                    }
                });
            },
            loadBorUserName(){
                if( vm.eqBorBelongValue!=""&&vm.eqBorCityValue!=""){
                    $.get("../../staff/findStaffByCity?department=" + vm.eqBorBelongValue + "&city="
                        + vm.eqBorCityValue,function (data, status) {
                        alert(JSON.stringify(data));
                        console.log(data);
                        if (status == "success") {
                            var _userData = eval(data);
                            console.log(_userData);
                            vm.eqBorName = _userData;
                        }
                    });
                }else {
                    //alert();
                }

            },
            loadBorNoBelongDep(){
                $.get("../../department/findDepartment?city=" + vm.eqBorNoCityValue, function (data, status) {
                    console.log(data);
                    if (status == "success") {
                        var _departmentData = eval(data);
                        console.log(_departmentData);
                        vm.eqBorNoBelong = _departmentData;
                    }
                });
            }
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
            $('#tb_equipment_borrow').bootstrapTable({
                url: '../../borrow/serch',         //请求后台的URL（*）
                method: 'post',                      //请求方式（*）
                contentType : "application/x-www-form-urlencoded",//因为如果不指定contentType 的话就会默认以application/json的方式发送数据，java就不能用getParameter拿到内容了，但是可以用输入流接收
                toolbar: '#borrow_toolbar',                //工具按钮用哪个容器
                striped: true,                      //是否显示行间隔色
                pagination: true,                   //是否显示分页（*）
                queryParams: oTableInit.queryParams,//传递参数（*）
                sortable: true,                     //是否启用排序
                sortOrder: "desc",                   //排序方式 "asc desc
                sidePagination: "client",           //分页方式：client客户端分页，server服务端分页（*）
                pageNumber:1,                       //初始化加载第一页，默认第一页
                pageSize: 3,                       //每页的记录行数（*）
                pageList: [3, 5, 10, 20],        //可供选择的每页的行数（*）
                strictSearch: true,
                showColumns: true,                  //是否显示所有的列
                showRefresh: true,                  //是否显示刷新按钮
                minimumCountColumns: 2,             //最少允许的列数
                clickToSelect: true,                //是否启用点击选中行
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
                    field: 'status',
                    title: '状态'
                }, {
                    field: 'userName',
                    title: '使用人',
                    formatter:function(value,row,index) {
                        var e = '<a href="#" mce_href="#" onclick="checkDetail(\'' + row.userId + '\')">' + value + '</a> ';
                        return e;
                    }
                }, {
                    field: 'operatorTime',
                    title: '操作时间'
                }, {
                    field: 'position',
                    title: '使用部门'
                } ]
            });
            $('#tb_equipment_fix').bootstrapTable({
                url: '../../eqfix/query',         //请求后台的URL（*）
                method: 'post',                      //请求方式（*）
                contentType : "application/x-www-form-urlencoded",//因为如果不指定contentType 的话就会默认以application/json的方式发送数据，java就不能用getParameter拿到内容了，但是可以用输入流接收
                toolbar: '#fix_toolbar',                //工具按钮用哪个容器
                striped: true,                      //是否显示行间隔色
                pagination: true,                   //是否显示分页（*）
                queryParams: oTableInit.queryParams,//传递参数（*）
                sortable: true,                     //是否启用排序
                sortOrder: "desc",                   //排序方式 "asc desc
                sidePagination: "client",           //分页方式：client客户端分页，server服务端分页（*）
                pageNumber:1,                       //初始化加载第一页，默认第一页
                pageSize: 3,                       //每页的记录行数（*）
                pageList: [3, 5, 10, 20],        //可供选择的每页的行数（*）
                strictSearch: true,
                showColumns: true,                  //是否显示所有的列
                showRefresh: true,                  //是否显示刷新按钮
                minimumCountColumns: 2,             //最少允许的列数
                clickToSelect: true,                //是否启用点击选中行
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
                    field: 'fixTime',
                    title: '维护时间'
                }, {
                    field: 'fixType',
                    title: '维护类型'
                }, {
                    field: 'fixDetail',
                    title: '维护详情'
                }]
            });
        };
        //得到查询的参数
        oTableInit.queryParams = function (params) {
            var equipId = $.getUrlParam('equipId');
            var temp = {   //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
                "equipId":equipId
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
    function checkDetail(staffId) {
        window.parent.document.getElementById('iframeContent').src="staff/detail?staffId="+staffId;
    };
    function preLoadEqName() {
        /**
         * 页面加载之前加载设备名称选项
         */
        //alert("现在在执行加载name");
        $.get("../../findAllEquipmentNameByEqTypeName?eqTypeId=" + vm.eqTypeValue, function (data, status) {
            if (status == "success") {
                var _equipmentName = eval(data);
                console.log(_equipmentName);
                vm.equipmentName = _equipmentName.data;
            }
        });
    };
    function preLoadDepName() {
        /**
         * 页面加载之前加载部门名称选项
         */
        $.get("../../department/findDepartment?city=" + vm.city, function (data, status) {
            console.log(data);
            if (status == "success") {
                var _departmentData = eval(data);
                console.log(_departmentData);
                vm.belongDepartment = _departmentData;
                //vm.beDepValue = beDepData;
            }
        });
    };
    $(document).ready(function(){
        var equipId = $.getUrlParam('equipId');
        var param = {"equipId":equipId};
        //alert("页面显示之前加在数据,设备ID："+equipId);
        $.ajax({
            url: "findEquipByEquipID",
            type: 'post',
            dataType: 'json',
            timeout: 1000,
            data:param,
            success: function (data, status) {
                if(data.customMessage !== ''){
                    vm.custom = Object.assign({},vm.custom,JSON.parse(data.customMessage));
                }
                
                $('#eqID').val(data.eqOtherId);
                $('#eqType').val(data.eqType);
                $('#eqName').val(data.eqName);
                $('#supplier').val(data.supplier);
                $('#belongDepart').val(data.city+data.belongDepart);
                $('#eqState').val(data.eqStateId);
                $('#time').val(data.purchasTime);

                //加载设备信息修改界面默认值
                $('#eqSubmitId').val(data.eqId);
                $('#eqSubmitOtherId').val(data.eqOtherId);
                eqTypeData = data.eqType;
                vm.eqTypeValue = data.eqType;
                preLoadEqName();
                eqNameData = data.eqName;
                supplierData = data.supplier;
                cityData = data.city;
                vm.city = data.city;
                preLoadDepName();
                beDepData = data.belongDepart;
                eqStateData = data.eqStateId;
                eqPurDate = data.purchasTime;
                //预制设备使用状态
                if(data.eqStateId == "使用"){
                    $('#eqUsed').attr("disabled",true);
                }
                if(data.eqStateId == "闲置"){
                    $('#eqNoUsed').attr("disabled",true);
                }
                if(data.eqStateId == "报废"){
                    $('#eqUsed').attr("disabled",true);
                    $('#eqNoUsed').attr("disabled",true);
                    $('#eqUnUsed').attr("disabled",true);
                }
            },
            fail: function (err, status) {
                alert("设备详情加载时获取数据失败！");
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
    $('#eqSubmitDate').datepicker({
        language: 'zh-CN',
        autoclose: true,
        todayHighlight: true
    });
    $('#eqAddFixDate').datepicker({
        language: 'zh-CN',
        autoclose: true,
        todayHighlight: true
    });
    $("#equipmentType").change(function () {
        loadEqName();
    });
    /**
     * 加载当前用户的所有设备类型
     */
    $.get("../../showAllEquipmentType",function(data,status){
        if(status=="success"){
            var _equipmentTypes = eval(data);
            console.log(_equipmentTypes);
            vm.equipmentType = _equipmentTypes;
        }
    });
    /**
     * 加载当前用户的所有提供商
     */
    $.get("../../supplier/findAllSupplier",function (data, status) {
        if(status=="success"){
            var _suppliers = eval(data);
            console.log(_suppliers);
            vm.suppliers = _suppliers;
        }
    });
    /**
     * 加载当前用户的所有城市
     */
    $.get("../../city/findAllCity",function (data, status) {
        if(status=="success"){
            var _cityData = eval(data);
            vm.citys = _cityData;
        }
    })
    /**
     * 加载设备状态类型
     */
    $.get("../../equipState/findAll",function (data, status) {
        if(status=="success"){
            var _allState = eval(data);
            vm.allState = _allState;
        }
    })
    function editData(){
        vm.eqTypeValue = eqTypeData;
        vm.eqNameValue = eqNameData;
        vm.supplier = supplierData;
        vm.city = cityData;
        vm.beDepValue = beDepData;
        vm.eqState = eqStateData;
        $('#eqSubmitDate').val(eqPurDate);
    };
    function addFixLog() {
        $('#eqAddFixDate').val("");
        $('#eqAddFixType').val("");
        $('#eqAddFixDetail').val("");
    };
    function addBorrowLog() {
        vm.eqBorNameValue="";
        vm.eqBorCityValue="";
        vm.eqBorBelongValue="";
        vm.eqBorDetailValue="";
        vm.eqBorName="";
        vm.eqBorBelong="";
        vm.eqBorNoBelong="";
        vm.eqBorNoBelong="";
        vm.eqBorNoCityValue="";
        vm.eqBorNoBelongValue="";
        vm.eqBorNoDetailValue="";
        vm.eqBorUnDetailValue="";
    };
    //给radio绑定点击事件
    $("#eqUsed").bind("click", function(event) {
        $("#eqUsedDiv").show();
        $("#eqNoUsedDiv").hide();
        $("#eqUnUsedDiv").hide();
    });
    $("#eqNoUsed").bind("click", function(event) {
        $("#eqUsedDiv").hide();
        $("#eqNoUsedDiv").show();
        $("#eqUnUsedDiv").hide();
    });
    $("#eqUnUsed").bind("click", function(event) {
        $("#eqUsedDiv").hide();
        $("#eqNoUsedDiv").hide();
        $("#eqUnUsedDiv").show();
    });
    function submitEditEq() {
        var eqId = $('#eqSubmitId').val();
        var time = $('#eqSubmitDate').val();
        var customMessage = "";
        for(var key in vm.custom){
            if(vm.custom[key] == ""){
                alert("编辑设备信息时，字段不能为空！！");
                return;
            }
            customMessage += key+"="+vm.custom[key]+";";
        }
        console.log(customMessage,'++++++++++++++++')
        if(vm.eqTypeValue==""||vm.eqNameValue==""||vm.supplier==""||vm.city==""||vm.beDepValue==""||vm.eqState==""||time==""){
            alert("编辑设备信息时，字段不能为空！！");
        }else {
            $.post("../equipDetail/editEqDetail",
                {
                    eqID : eqId,
                    eqType : vm.eqTypeValue,
                    eqName : vm.eqNameValue,
                    supplier : vm.supplier,
                    belongDepart : vm.beDepValue,
                    eqState : vm.eqState,
                    time : time,
                    userCity  : vm.city,
                    customMessage: customMessage
                },
                function(data,status) {
                    var _data = data;
                    checkDetailEqDetail(eqId);
                    if(eval(_data)){
                        alert(eval(_data).success);
                    }else{
                        alert(_data);
                    }
                });
        }
        $("#myModal").modal('hide');
    }
    function submitFixlog() {
        var eqID = $('#eqSubmitId').val();
        var eqAddFixDate = $('#eqAddFixDate').val();
        var eqAddFixType = $('#eqAddFixType').val();
        var eqAddFixDetail = $('#eqAddFixDetail').val();
        if(eqID==""||eqAddFixDate==""||eqAddFixType==""||eqAddFixDetail==""){
            alert("字段不能为空！！");
        }else {
            $.post("../../equipFix/addEqFixLog",
                {
                    eqID : eqID,
                    fixTime : eqAddFixDate,
                    fixType : eqAddFixType,
                    fixDetail : eqAddFixDetail
                },
                function(data,status) {
                    var _data = data;
                    if(_data!="s"){
                        alert(_data);
                        $('#tb_equipment_fix').bootstrapTable('refresh');
                    }
                });
        }
        $("#fixModal").modal('hide');
    }
    function submitBorlog() {
        var val=$('input:radio[name="borrowState"]:checked').val();
        if(val==null){
            alert("没有选中状态，请重新选择！");
        }else{
            //alert(val);
            var eqID = $('#eqSubmitId').val();
            if(val=="使用"){
                if(eqID==""||vm.eqBorCityValue==""||vm.eqBorBelongValue==""||vm.eqBorNameValue==""||vm.eqBorDetailValue=="") {
                    alert("字段不能为空！！");
                    return;
                }else{
                    $.post("../../equipBor/addEqBorLog",
                        {
                            eqID : eqID,
                            eqState:val,
                            eqBorCity : vm.eqBorCityValue,
                            eqBorBelong : vm.eqBorBelongValue,
                            eqBorName : vm.eqBorNameValue,
                            eqBorDetail: vm.eqBorDetailValue
                        },
                        function(data,status) {
                            var _data = data;
                            if(_data!=""){
                                checkDetailEqDetail(eqID);
                                alert(_data);
                            }
                        });
                }
            }else if(val=="闲置"){
                if(eqID==""||vm.eqBorNoCityValue==""||vm.eqBorNoBelongValue==""||vm.eqBorNoNameValue==""||vm.eqBorNoDetailValue=="") {
                    alert("字段不能为空！！");
                }else{
                    $.post("../../equipBor/addEqBorLog",
                        {
                            eqID : eqID,
                            eqState:val,
                            eqBorNoCity : vm.eqBorNoCityValue,
                            eqBorNoBelong : vm.eqBorNoBelongValue,
                            eqBorNoDetail: vm.eqBorNoDetailValue
                        },
                        function(data,status) {
                            var _data = data;
                            if(_data!="s"){
                                checkDetailEqDetail(eqID);
                                alert(_data);
                            }
                        });
                }
            }else if(val=="报废"){
                if(eqID==""||vm.eqBorUnDetailValue=="") {
                    alert("字段不能为空！！");
                }else{
                    $.post("../../equipBor/addEqBorLog",
                        {
                            eqID : eqID,
                            eqState:val,
                            eqBorUnDetail : vm.eqBorUnDetailValue
                        },
                        function(data,status) {
                            var _data = data;
                            if(_data!="s"){
                                checkDetailEqDetail(eqID);
                                alert(_data);
                            }
                        });
                }
            }else {
                alert("选择的设备状态不对，请重新选择！");
            }
            $("#borrowModal").modal('hide');
        }
    }
    function checkDetailEqDetail(equipId) {
        window.parent.document.getElementById('iframeContent').src="equip/detail?equipId="+equipId;
    }
</script>
</html>