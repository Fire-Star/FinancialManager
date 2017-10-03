<%--
  Created by IntelliJ IDEA.
  User: MoonFollow
  Date: 2017/9/30
  Time: 15:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <meta http-equiv="Content-Type" content="multipart/form-data; charset=utf-8" />
    <title>员工添加</title>
    <!-- 引入 BootStrap 全家桶 -->
    <script src="../res/js/jquery-3.2.1.min.js"></script>
    <script src="../res/js/bootstrap.min.js"></script>
    <script src="../res/js/bootstrap-datepicker.js"></script>
    <script src="../res/js/bootstrap-datepicker.zh-CN.js"></script>

    <script src="../res/js/fileinput.min.js"></script>
    <script src="../res/js/piexif.js" type="text/javascript"></script>
    <script src="../res/js/sortable.min.js" type="text/javascript"></script>
    <script src="../res/js/purify.min.js" type="text/javascript"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.11.0/umd/popper.min.js"></script>
    <script src="../res/js/theme.min.js" type="text/javascript"></script>
    <script src="../res/js/zh.js"></script>
    <link rel="stylesheet" href="../res/css/fileinput.min.css" media="all" type="text/css">
    <link rel="stylesheet" href="../res/css/bootstrap.min.css"/>

    <!-- 引入Vue.js -->
    <script src="../res/js/vue.min.js"></script>
    <style>
        html,body{
            height: 100%;
            margin: 0;
            padding: 0;
        }
        p{
            margin: 0;
            padding: 0;
        }
        .supplier-head{
            height: 50px;
            line-height: 50px;
        }
        .hr{
            margin-top: 0;
            border-bottom: 1px solid #9d9d9d;
        }
        .btn-submit{
            width: 200px;
        }
        .datepicker:hover,
        .x:hover,
        #dep,
        #city,
        #calender{
            cursor: pointer;
        }
        .borderx{
            height: 38px;
            line-height: 38px;
            border-bottom: 1px solid #9d9d9d;
            margin: 10px 0px 35px 0px;
            position: relative;
        }
        .borderx span,
        .customTitle{
            display: inline-block;
            height: 38px;
            font-size: 21px;
        }
        .pibu{
            position: absolute;
            right: 10px;
            top: 0;
        }
        .row .control-label{
            padding: 7px 0;
            text-align: right;
        }
        .eqUpButton{
            width: 280px !important;
        }
        .file-up-in:hover{
            cursor: pointer;
        }
        #fileSource{
            display: none;
        }
    </style>
</head>
<body>
<div class="app container-fluid width-100per top-content">
    <div class="supplier-head">设备管理 > 新增设备信息</div>
    <div class="borderx">
        <span>设备基本信息添加</span>
        <security:authorize access="hasAnyRole('ROLE_ADMIN')">
        <button class="btn btn-success pibu col-sm-1" data-toggle="modal" data-target="#pibudialog">批量导入</button>
        </security:authorize>
    </div>
    <div class="row">
        <label class="control-label col-md-1 col-sm-2 col-xs-2" for="equipmentType">设备类型：</label>
        <div class="form-group col-md-3 col-sm-4 col-xs-4">
            <select class="form-control x" propName="设备类型" id="equipmentType" @change="loadEqName" data-toggle="tooltip" data-placement="top"  v-model="eqTypeValue" placeholder="请输入设备类型..." >
                <option selected value="">---请选择设备类型---</option>
                <option v-for="(item , index) in equipmentType" :value="item.equipmentTypeName">{{item.equipmentTypeName}}</option>
            </select>
        </div>
        <label for="equipmentName" class="control-label col-md-1 col-sm-2 col-xs-2">设备名称：</label>
        <div class="form-group col-md-3 col-sm-4 col-xs-4">
            <select class="form-control x" id="equipmentName" propName="城市" data-toggle="tooltip" data-placement="top">
                <option selected value="">---请选择设备名称---</option>
                <option v-for="(item , index) in equipmentName" :value="item.eqName">{{item.eqName}}</option>
            </select>
        </div>
        <label for="pingpaiName" class="control-label col-md-1 col-sm-2 col-xs-2">品牌名称：</label>
        <div class="form-group col-md-3 col-sm-4 col-xs-4" >
            <input class="form-control form-control-x" propName="品牌名称" id="pingpaiName" name="pingpaiName" v-model="pingpaiName" placeholder="输入品牌名称..." type="text">
        </div>
        <label for="x"  class="control-label col-md-1 col-sm-2 col-xs-2">采购部门：</label>
        <div class="form-group col-md-3 col-sm-4 col-xs-4 form-inline" role="group">
            <div class="btn-group" style="width: 100%">
                <select class="btn btn-default form-control select-left" v-model="buyCity" @change="loadBuyDep" propName="品牌名称" data-toggle="tooltip" data-placement="top"  style="width: 50%">
                    <option selected value="" id="">城市</option>
                    <option v-for="(item , index) in citys" :value="item.city">{{item.city}}</option>
                </select>
                <select class="btn btn-default form-control select-right" propName="采购部门" id="buyDep" data-toggle="tooltip" data-placement="top"  style="width: 50%">
                    <option selected value="" id="fd">--部门--</option>
                    <option v-for="(item , index) in buyDepartments" :value="item.department">{{item.department}}</option>
                </select>
            </div>
        </div>
        <label for="d" class="control-label col-md-1 col-sm-2 col-xs-2">归属部门：</label>
        <div class="form-group col-md-3 col-sm-4 col-xs-4 form-inline" role="group">
            <div class="btn-group" style="width: 100%">
                <select class="btn btn-default form-control select-left" id="x" v-model="belongCity" @change="loadBelongDep" propName="品牌名称" data-toggle="tooltip" data-placement="top"  style="width: 50%">
                    <option selected value="" id="d">城市</option>
                    <option v-for="(item , index) in citys" :value="item.city">{{item.city}}</option>
                </select>
                <select class="btn btn-default form-control select-right" id="belongDep" propName="品牌名称" data-toggle="tooltip" data-placement="top"  style="width: 50%">
                    <option selected value="" id="dsf">--部门--</option>
                    <option v-for="(item , index) in belongDepartment" :value="item.department">{{item.department}}</option>
                </select>
            </div>
        </div>
        <label for="calender" class="control-label col-md-1 col-sm-2 col-xs-2">采购时间：</label>
        <div class="form-group col-md-3 col-sm-4 col-xs-4">
            <input class="form-control form-control-x" propName="采购时间" id="calender" name="entryTime" placeholder="选择采购时间..." type="text">
        </div>
        <label for="supplier" class="control-label col-md-1 col-sm-2 col-xs-2">供应商：</label>
        <div class="form-group col-md-3 col-sm-4 col-xs-4">
            <select class="form-control form-control-x x" id="supplier" propName="供应商" data-toggle="tooltip" data-placement="top" v-model="supplier" >
                <option selected value="">---请选择供应商---</option>
                <option v-for="(item , index) in autoSuppliers" :value="item.name">{{item.name}}</option>
            </select>
        </div>
        <label for="calender" class="control-label col-md-1 col-sm-2 col-xs-2">采购价格：</label>
        <div class="form-group col-md-3 col-sm-4 col-xs-4">
            <input class="form-control form-control-x" propName="采购价格" id="price" v-model="buyPrice" name="entryTime" placeholder="输入采购价格..." type="text">
        </div>
        <label for="buyCount" class="control-label col-md-1 col-sm-2 col-xs-2">购买数量：</label>
        <div class="form-group col-md-3 col-sm-4 col-xs-4">
            <input type="number" id="buyCount" class="form-control form-control-x"  propName="购买数量" placeholder="请输入购买数量..." data-toggle="tooltip" data-placement="top" v-model="buyCount" >
        </div>
    </div>
    <br><br>
    <p class="customTitle">员工自定义信息添加</p>
    <hr class="hr">
    <input type="hidden" name="customKey">
    <input type="hidden" name="customValue">
    <div class="form-group form-inline row" v-for="(item,index) in customFields">
        <div class="col-md-2 col-sm-2 col-xs-2"></div>
        <div class="col-md-4 col-sm-4 col-xs-4">
            <input type="text" style="width: 100%" class="form-control form-control-x" v-model="customFields[index].key" placeholder="自定义属性">
        </div>
        <div class="col-md-4 col-sm-4 col-xs-4">
            <input type="text" style="width: 100%" class="form-control form-control-x" v-model="customFields[index].value" placeholder="属性值">
        </div>
        <div class="col-md-2 col-sm-2 col-xs-2">
            <span class="btn btn-sm btn-danger glyphicon glyphicon-minus" @click="delField(index)"></span>
            <span class="btn btn-sm btn-primary glyphicon glyphicon-plus" v-if="index==(customFields.length-1)" @click="addField(index,customFields[index])"></span>
        </div>
    </div>
    <div class="col-sm-12 col-xs-12" style="text-align: center;margin-top: 50px;">
        <input type="button" data-loading-text="提交中..." id="submitBtn" class="btn btn-primary btn-submit" @click="submitData()" value="提交">
        <a class="btn btn-warning" href="downloadEqInsertSuccessFile" v-if="showDownloadSuccessEqFile">下载插入成功设备列表</a>
    </div>
    <security:authorize access="hasAnyRole('ROLE_ADMIN')">
        <div class="modal fade" id="pibudialog" tabindex="-1" role="dialog" aria-labelledby="gridSystemModalLabel">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                        <h4 class="modal-title" id="gridSystemModalLabel">批量导入</h4>
                    </div>
                    <div class="modal-body">
                        <form name="fileUpload">
                            <input type="file" id="fileSource">
                        </form>
                        <div class="input-group">
                            <input type="text" class="form-control file-up-in" id="fileSource-show" readonly placeholder="请选择文件..." @click="choseFile()">
                            <span class="input-group-btn">
                                <button class="btn btn-primary" type="button" @click="uploadFileAction()">上传并导入</button>
                        </span>
                        </div>
                    </div>
                    <div class="modal-footer" >
                        <a class="btn btn-warning" v-if="showDownloadError" href="downloadErrorExcel">下载未导入设备信息</a>
                        <button type="button" class="btn btn-danger" data-dismiss="modal">Close</button>
                    </div>
                </div><!-- /.modal-content -->
            </div><!-- /.modal-dialog -->
        </div><!-- /.modal -->
    </security:authorize>

    <button id="modalButton" type="button" hidden data-toggle="modal" data-target="#myModal"></button>
    <!-- Modal -->
    <div class="modal fade" id="myModal" tabindex="-1" role="dialog">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title" id="myModalLabel">提示</h4>
                </div>
                <div class="modal-body">
                    {{modalStatus}}
                </div>
            </div>
        </div>
    </div>
</div>
</body>
<script>
    <security:authorize access="hasAnyRole('ROLE_ADMIN')">
    $("#fileSource")[0].onchange = function() {
        alert(document.getElementById("fileSource").value);
        document.getElementById("fileSource-show").value = document.getElementById("fileSource").value;
    }
    </security:authorize>

    function choseFile() {
        $("#fileSource").click();
    }
    $("#equipmentType").change(function () {
        loadEqName();
    });


    var data = {
        eqTypeValue:"",
        //equipmentName
        pingpaiName:"",
        buyCity:"",
        //buyDep
        belongCity:"",
        //belongDep
        buyTime:"",
        supplier:"",
        buyPrice:"",
        customFields:[{}],
        buyCount:"",

        equipmentType:"",
        equipmentName:"",
        isError:[],
        modalStatus:"",
        citys:[],
        buyDepartments:[],
        belongDepartment:[],
        autoSuppliers:[],
        showDownloadError:false,
        showDownloadSuccessEqFile:false
    };

    var vm = new Vue({
        el:'.app',
        data:data,
        methods: {
            downloadError(){
                $.get("downloadErrorExcel",function (data, status) {
                    console.log(data);
                })
            },
            addField(index, item) {
                this.customFields.push({});
            },
            delField(index) {
                if (this.customFields.length == 1) {
                    return;
                }
                this.customFields.splice(index, 1);
            },
            submitData() {
                console.log("提交中");
                if (tableCheck()) {
                    return;
                }
                var $btn = $('#submitBtn').button('loading');
                console.log("提交中22");
                var _customFields = "";
                var index;
                for (index in this.customFields) {
                    if (this.customFields[index].key == "" || this.customFields[index].key == undefined) {
                        continue;
                    }
                    _customFields += (this.customFields[index].key + "=" + (this.customFields[index].value == undefined ? "" : this.customFields[index].value) + ";");
                }
                _customFields = _customFields;
                var eqName = $('#equipmentName')[0].value;
                var belongDepart = $('#belongDep')[0].value;
                var purchaseTime = $('#calender')[0].value;
                var purchaseDep = $('#buyDep')[0].value;

                $.post("add",
                    {
                        "eqType":this.eqTypeValue,
                        "eqName": eqName,
                        "brandName": this.pingpaiName,
                        "buyCity": this.buyCity,
                        "purchasDepart":purchaseDep,
                        "city":this.belongCity,
                        "belongDepart":belongDepart,
                        "purchasDate":$('#calender')[0].value,
                        "supplier":this.supplier,
                        "purchasPrice":this.buyPrice,
                        "purchasTime":purchaseTime,
                        "customMessage": _customFields,
                        "buyCount": this.buyCount
                    },
                    function (data, status) {
                        if (status) {
                            var _data = eval(data);
                            if(!_data){
                                vm.modalStatus = "添加数据失败，请联系管理员！！！";
                            }else if(_data.success!=undefined){
                                vm.modalStatus = _data.success;
                            }else if(_data.errorType!=undefined){
                                vm.modalStatus = _data.errorType;
                            }else{
                                vm.modalStatus = "添加数据失败，请联系管理员！！！";
                            }
                            $('#modalButton').click();
                        }
                    });
                refreshSuccessFileEqInsert();
                $btn.button('reset');
            },
            loadBuyDep() {
                $.get("../department/findDepartment?city=" + vm.buyCity, function (data, status) {
                    console.log(data);
                    if (status == "success") {
                        var _departmentData = eval(data);
                        console.log(_departmentData);
                        if (_departmentData.departmentErrorType != undefined) {
                            vm.modalStatus = _departmentData.departmentErrorType;
                            $('#modalButton').click();
                        } else {
                            vm.buyDepartments = _departmentData;
                        }
                    }
                });
                $('#depIDOp').attr('selected', true);
            },
            loadBelongDep(){
                $.get("../department/findDepartment?city=" + vm.belongCity, function (data, status) {
                    console.log(data);
                    if (status == "success") {
                        var _departmentData = eval(data);
                        console.log(_departmentData);
                        if (_departmentData.departmentErrorType != undefined) {
                            vm.modalStatus = _departmentData.departmentErrorType;
                            $('#modalButton').click();
                        } else {
                            vm.belongDepartment = _departmentData;
                        }
                    }
                });
                $('#depIDOp').attr('selected', true);
            },
            loadDep(){

            },
            loadEqName() {
                /**
                 * 加载设备名称选项
                 */
                $.get("../findAllEquipmentNameByEqTypeName?eqTypeId="+vm.eqTypeValue,function(data,status){
                    if(status=="success"){
                        var _equipmentName = eval(data);
                        console.log(_equipmentName);
                        vm.equipmentName = _equipmentName.data;
                    }
                });
            }
        }
    });
    /**
     * 加载设备类型选项
     */
    $.get("../showAllEquipmentType",function(data,status){
        if(status=="success"){
            var _equipmentTypes = eval(data);
            console.log(_equipmentTypes);
            vm.equipmentType = _equipmentTypes;
        }
    });

    /**
     * 加载当前用户的所有提供商
     */
    $.get("../supplier/findAllSupplier",function (data, status) {
        if(status=="success"){
            var _autoSuppliers = eval(data);
            console.log(_autoSuppliers);
            vm.autoSuppliers = _autoSuppliers;
        }
    });


    /**
     * 加载当前用户的所有城市
     */
    $.get("../city/findAllCity",function (data, status) {
        if(status=="success"){
            var _cityData = eval(data);
            vm.citys = _cityData;
        }
    })


    function tableCheck(){
        var inputObj = $('.form-control');
        var i;
        var len = 11;
        var flag = false;
        for(i=0 ;i < len; i++){
            if(inputObj[i].value==""){
                vm.isError[i]=true;
                flag = true;
                var propName = inputObj[i].getAttribute('propName');
                var id = inputObj[i].getAttribute('id');
                $(inputObj[i]).parent().addClass('has-error');
                var errorMessage = propName+"不能有特殊字符也不能为空！";
                $('#'+id).tooltip({
                    title:errorMessage
                });
            }else{
                var id = inputObj[i].getAttribute('id');
                $(inputObj[i]).parent().removeClass('has-error');
                $('#'+id).tooltip('destroy');
            }
        }
        console.log(flag);
        return flag;
    }
    $('#calender').datepicker({
        language: 'zh-CN',
        autoclose: true,
        todayHighlight: true
    });
</script>

<script>
    <security:authorize access="hasAnyRole('ROLE_ADMIN')">
    $("input[type=file]").change(function(e){
        var path = e.target.value;
        var lastIndex = path.lastIndexOf('\\')+1;
        var value = path.substring(lastIndex);
        $('#fileSource-show')[0].value = value;
    });
    function uploadFileAction() {
        if($('#fileSource-show')[0].value==""){
            vm.modalStatus = '请选择文件！';
            $('#modalButton').click();
            return;
        }
        // 创建
        var form_data = new FormData();
        // 获取文件
        var file_data = $("#fileSource").prop("files")[0];
        // 把所以表单信息
        form_data.append("eqXsl", file_data);
        $.ajax({
            type: "POST",
            url: "upload",
            processData: false,  // 注意：让jQuery不要处理数据
            contentType: false,  // 注意：让jQuery不要设置contentType
            data: form_data,
            success: function(data){//成功方法
                var _data = eval(data);
                if(!_data){
                    vm.modalStatus = '发生未知错误，请联系管理员！';
                    $('#modalButton').click();
                }else if(_data.hasInsertError){
                    vm.modalStatus = _data.hasInsertError;
                    $('#modalButton').click();
                    vm.showDownloadError = true;
                }else if(_data.errorType){
                    vm.modalStatus = _data.errorType;
                    $('#modalButton').click();
                }else if(_data.success){
                    vm.modalStatus = _data.success;
                    $('#modalButton').click();
                    vm.showDownloadError = false;
                }else{
                    vm.modalStatus = '发生未知错误，请联系管理员！';
                    $('#modalButton').click();
                }
                refreshSuccessFileEqInsert();
            },error:function(){//请求失败方法
                alert("系统繁忙,请稍后再试！");
            }
        });
    }
    $.get("hasErrorFile",function (data, status) {
        if(status == "success"){
            var _data = eval(data);
            if(!_data){
                vm.modalStatus = '发生未知错误，请联系管理员！';
                $('#modalButton').click();
            }else if(_data.hasErrorFile){
                if(_data.hasErrorFile=="1"){
                    vm.showDownloadError = true;
                }else{
                    vm.showDownloadError = false;
                }
            }else if(_data.errorType){
                vm.modalStatus = _data.errorType;
                $('#modalButton').click();
            }else{
                vm.modalStatus = '发生未知错误，请联系管理员！';
                $('#modalButton').click();
            }
        }
    });
    </security:authorize>

    refreshSuccessFileEqInsert();
    function refreshSuccessFileEqInsert() {
        $.get("hasEqSuccessFile",function (data, status) {
            if(status == "success"){
                var _data = eval(data);
                if(!_data){
                    vm.modalStatus = '发生未知错误，请联系管理员！';
                    $('#modalButton').click();
                }else if(_data.hasInsertEqSuccessFile){
                    if(_data.hasInsertEqSuccessFile=="1"){
                        vm.showDownloadSuccessEqFile = true;
                    }else{
                        vm.showDownloadSuccessEqFile = false;
                    }
                }else if(_data.errorType){
                    vm.modalStatus = _data.errorType;
                    $('#modalButton').click();
                }else{
                    vm.modalStatus = '发生未知错误，请联系管理员！';
                    $('#modalButton').click();
                }
            }
        })
    }
</script>

</html>