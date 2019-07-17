//	删除新增的收费项
function deleteTr(obj) {
    alert("是否删除");
    var rsfundsmoney = $(obj).parents().prev().prev().prev().children().next()
        .val();
    $("#summoney").val(Number($("#summoney").val()) - Number(rsfundsmoney));
    // alert(rsfundsmoney);
    $(obj).parents("tr").remove();
}

// 把周结、月结、欠款里的费用一个个遍历出来
function addreceipt(data) {
    for (var i = 0; i < data.length; i++) {
        chargeH = "<tr style='margin:0 auto;'>"
            + "<td style='margin-left:10px;display:inline-block;' ><label>费用名称</label> <input value='"
            + data[i].chname
            + "' type='text' readonly='readonly' name='receiptSonFormMap.rsname' id='rsname' style='width:90px;' /></td>"
            + "<td style='margin-left:10px;display:inline-block;'><label>支付方式</label> "
            + "<input type='text' readonly='readonly' value='"
            + data[i].chpaytype
            + "' name='receiptSonFormMap.rsablemoney' id='newchpaytype"
            + i
            + "' style='width:90px;' /></td>"
            + "<td style='margin-left:10px;display:inline-block;'><label>应收金额</label> "
            + "<input type='text' readonly='readonly' value='"
            + data[i].chmoney
            + "' name='receiptSonFormMap.rsablemoney' id='newrsablemoney"
            + i
            + "' style='width:90px;' /></td>"
            + "<td style='margin-left:10px;display:inline-block;'><label>实收金额</label><input type='text' name='receiptSonFormMap.rsfundsmoney' value='"
            + data[i].chmoney
            + "' id='newrsfundsmoney"
            + i
            + "' style='width:90px;' /></td>"
            + "<td style='margin-left:10px;display:none;'><label>欠款</label><input type='text' readonly='readonly' name='receiptSonFormMap.rrsowemoney' value='0' id='newrsowemoney"
            + i
            + "' style='width:90px;' /></td>"
            + "<td><label>收费区间</label> <input readonly='readonly' type='text' name='receiptSonFormMap.rsfundsmoney' value='"
            + data[i].resummary
            + "' id='newresummary"
            + i
            + "' /></td>"
            + "</tr>"
        // 把拼接好的html追加到表单后面
        $("#Mytable").append(chargeH);
    }
    for (var i = 0; i < data.length; i++) {
        // 实收金额ID
        var rsfundsmoneyId = "#newrsfundsmoney" + i;
        // 欠款ID
        var rsowemoneyId = "#newrsowemoney" + (i - 1);
        $(rsfundsmoneyId).change(
            function () {
                // 获取输入的实收金额钱
                var rsfundsmoney = $(this).val().trim();
                // 获取应收的金额
                var rsablemoney = $(this).parents().prev().children()
                    .next().val().trim();
                // 欠款的定位
                var rsowemoneyId = $(this).parents().next().children()
                    .next();
                console.log("实收金额钱：" + rsfundsmoney + ",应收金额钱："
                    + rsablemoney);
                $(rsowemoneyId).val(rsablemoney - rsfundsmoney);
            });
    }
}

$(function () {
    $("#retimeofreceipt").val(getNowDateToSs());
    $('#retimeofreceipt').datetimepicker({
        format: 'yyyy-mm-dd hh:ii:ss',
        todayBtn: true,
        language: 'zh-CN',
        autoclose: true, // 选中时间后自动关闭
        todayHighlight: true, // 当天时间高亮
        minView: 0,
        startView: 0,
        minuteStep: 2,
        maxView: 1
    });

    var formLength = 0;
    var ac = 0;
    var summoney = 0;
    // 根据车牌号来获取各种收费项
    $("#cplateid").change(function () {
        $("#Mytable").empty();
        carAndOwner(this);
        var data = $("#cid").val();
        // alert(data);
        if (data != "" && data != null) {
            // 判断这个车是不是问题车
            var iserrorurl = rootPath + '/receipt/iserror.shtml?id=' + data;
            var iserror = CommnUtil.ajax(iserrorurl, null, "json");
            // 车没有问题
            if (iserror == "success") {
                // alert(iserror);
                // 1.从周结来查出这个车的收费信息
                var weeklyUrl = rootPath + '/receipt/selectweekly.shtml?cid=' + data;
                var weeklyData = CommnUtil.ajax(weeklyUrl, null, "json");
                // 2.从月结来查出这个车的收费信息
                var monthlyUrl = rootPath + '/receipt/selectmonthly.shtml?cid=' + data;
                var monthlyData = CommnUtil.ajax(monthlyUrl, null, "json");
                // 3.从欠款记录也查出这个车的收费信息
                var debtUrl = rootPath + '/receipt/selectdebt.shtml?cid=' + data;
                var debtData = CommnUtil.ajax(debtUrl, null, "json");
                console.log("周结费用：" + weeklyData + " 月结费用：" + monthlyData + " 欠款费用：" + debtData);
                var a = [];
                for (var obj in weeklyData) {
                    a.push(weeklyData[obj]);
                }
                for (var obj in monthlyData) {
                    a.push(monthlyData[obj]);
                }
                for (var obj in debtData) {
                    a.push(debtData[obj]);
                }
                console.log("拼接好的串：" + a);
                for (var i = 0; i < a.length; i++) {
                    summoney = summoney + a[i].chmoney;
                }
                $("#summoney").val(summoney);
                // 把周结、月结、欠款里的费用一个个遍历出来
                addreceipt(a);
            } else {
                layer.alert("此车为问题车辆！！", 3);
            }
        }
    });

    // 动态添加收费项
    $("#buttonone").click(function () {
        // alert("1111++" + chargeSonData);
        var chargeH = "" + "<tr id='tr"
            + ac
            + "'>"
            + "<td>"
            + "<label>费用名称</label> "
            + "<select id='rsname"
            + ac
            + "'><option value='请选择费用项'>请选择费用项</option></select></td>"
            + "<td><label>支付方式</label> "
            + "<select id='chpaytype"
            + ac
            + "'><option value='请选择支付方式'>请选择支付方式</option></select></td>"
            + "<td><label>应收金额</label> <input type='text' name='receiptSonFormMap.rsablemoney' value='' id='rsablemoney"
            + ac
            + "' /></td>"
            + "<td><label>实收金额</label> <input type='text' name='receiptSonFormMap.rsfundsmoney' value='' id='rsfundsmoney"
            + ac
            + "' /></td>"
            + "<td hidden><label>欠款</label> <input type='text' readonly='readonly' name='receiptSonFormMap.rrsowemoney' value='' id='rsowemoney"
            + ac
            + "' /></td>"
            + "<td><label>收费区间</label> <input type='text' readonly='readonly' name='receiptSonFormMap.rsfundsmoney' value='' id='resummary"
            + ac
            + "' /></td>"
            + "<td><button onclick='deleteTr(this)' id='delete0' >删除</button></td>"
            + "</tr>";
        $("#Mytable").append(chargeH);

        // 动态获取车的收费信息各种
        // 获取字典子表，收费项都有哪些
        var rsnameUrl = rootPath
            + '/receipt/selectrsname.shtml';
        var rsnameData = CommnUtil
            .ajax(rsnameUrl, null, "json");
        console.log("收费项大小:" + rsnameData.length);
        var chpaytypeUrl = rootPath
            + '/receipt/selectchpaytype.shtml';
        var chpaytypeData = CommnUtil.ajax(chpaytypeUrl, null,
            "json");
        console.log("支付方式大小:" + chpaytypeData.length);
        // 把费用项动态加入下拉框
        var h = "<option >请选择费用项</option>";
        for (var i = 0; i < rsnameData.length; i++) {
            h += "<option value='" + rsnameData[i].dstypename
                + "' data-id='" + rsnameData[i].id + "'>"
                + rsnameData[i].dstypename + "</option>";
        }
        var h2 = "<option >请选择支付方式</option>";
        for (var i = 0; i < chpaytypeData.length; i++) {
            h2 += "<option value='"
                + chpaytypeData[i].dstypename
                + "' data-id='" + chpaytypeData[i].id
                + "'>" + chpaytypeData[i].dstypename
                + "</option>";
        }
        var renameId = "#rsname" + ac;
        var chpaytypeId = "#chpaytype" + ac;
        var rsablemoneyId = "#rsablemoney" + ac;
        var rsfundsmoneyId = "#rsfundsmoney" + ac;
        var rsowemoneyId = "#rsowemoney" + ac;
        var resummaryId = "#resummary" + ac;
        $(renameId).html(h);
        $(chpaytypeId).html(h2);
        // alert("chpaytypeId:"+chpaytypeId+"h2:"+h2);
        // 把收费区间根据支付方式动态改变
        $(chpaytypeId).change(function () {
            var chpaytype = $(this).val()
                .trim();
            if (chpaytype == "周结") {
//												 alert("asdsad");
                $(resummaryId).val(getNextWeek()[0]
                    + "~"
                    + getNextWeek()[1]);
            }
            if (chpaytype == "月结") {
                $(resummaryId).val(getNextStartMonth(getBeforeDate(0))
                    + "~"
                    + getNextLastMonth(getNextStartMonth(getBeforeDate(0))));
            }
            if (chpaytype == "一次性结") {
                $(resummaryId).val("一次性结");
            }
        });
        // 把实收金额跟欠款，根据应收金额动态改变
        var status = 0;
        var oldrsablemoney = 0;
        $(rsablemoneyId).change(function () {
            // alert(status);
            // 获取输入的应收金额
            var rsablemoney = $(this).val();
            // 这里为了防止，第一次写了应收金额，添加进去总金额里了，结果又把应收金额给改变了
            if (status == 0) {
                var summoney = Number($("#summoney")
                        .val())
                    + Number(rsablemoney);
                // 给上面的总金额加钱
                $("#summoney").val(summoney);
            } else {
                var summoney = Number($("#summoney")
                        .val())
                    - Number(oldrsablemoney);
                // alert(summoney);
                // 给上面的总金额加钱
                $("#summoney").val(
                    Number(summoney)
                    + Number(rsablemoney));
            }
            oldrsablemoney = rsablemoney;
            // 给实收金额钱赋值
            $(this).parents().next().children().next()
                .val(Number(rsablemoney));
            // 给欠款赋值
            $(this).parents().next().next().children()
                .next().val(0);
            status = -1;
        });
        // 当实收金额改变
        $(rsfundsmoneyId).change(function () {
            // alert("dasdsa");
            // 获取输入的实收金额钱
            var rsfundsmoney = $(this).val().trim();
            // 获取应收的金额
            var rsablemoney = $(this).parents().prev()
                .children().next().val().trim();
            // 给上面的总金额加钱
            var summoney = Number($("#summoney").val())
                - Number(rsablemoney)
                + Number(rsfundsmoney);
            // alert(summoney);
            $("#summoney").val(summoney);
            // 欠款的定位
            var rsowemoneyId = $(this).parents().next()
                .children().next();
            console.log("实收金额钱：" + rsfundsmoney
                + ",应收金额钱：" + rsablemoney);
            $(rsowemoneyId).val(
                rsablemoney - rsfundsmoney);
        });
        ac++;
    });

    // 点击提交按钮触发，获取表单元素的值
    $("#addAll").click(function () {
        var data = $("#cid").val();
        // alert(data);
        if (data != "") {
            var iserrorurl = rootPath
                + '/receipt/iserror.shtml?id=' + data;
            var iserror = CommnUtil.ajax(iserrorurl, null,
                "json");
            if (iserror == "success") {
                // 定义数组保存表单所有数据
                var receiptList = new Array();
                var receipt = new Object();
                receipt.reredocumentid = $("#reredocumentid")
                    .val();
                receipt.cid = $("#cid").val();
                receipt.cplateid = $("#cplateid").val();
                receipt.coname = $("#coname").val();
                receipt.rechamberlain = $("#rechamberlain")
                    .val();
                receipt.retimeofreceipt = $("#retimeofreceipt")
                    .val();
                receiptList[0] = receipt;

                console.log("固定表单元素值" + receiptList);
                // 遍历获取动态的表单元素值
                var otbl = document.getElementById('Mytable'); // 获取table对象，名字为otbl
                var anjianName = "";
                var anjianNum = "";
                var len = otbl.rows.length // 获取otbl的行数
                // alert(len);
                for (var i = 0; i < len; i++) {
                    var receiptSon = new Object();

                    receiptSon.rsname = otbl.rows[i].cells[0].children[1].value;
                    receiptSon.chpaytype = otbl.rows[i].cells[1].children[1].value;
                    receiptSon.rsablemoney = otbl.rows[i].cells[2].children[1].value;
                    receiptSon.rsfundsmoney = otbl.rows[i].cells[3].children[1].value;
                    receiptSon.rsowemoney = otbl.rows[i].cells[4].children[1].value;
                    receiptSon.resummary = otbl.rows[i].cells[5].children[1].value;
                    receiptSon.retimeofreceipt = $(
                        "#retimeofreceipt").val();
                    console.log(receiptSon.rsname + "+"
                        + receiptSon.rsablemoney + "+"
                        + receiptSon.rsfundsmoney + "+"
                        + receiptSon.rsowemoney + "+"
                        + receiptSon.resummary + "+"
                        + receiptSon.retimeofreceipt);
                    receiptList.push(receiptSon);
                }
                // 创建后台的接收路由
                var jsonInfo = JSON.stringify(receiptList);
                console.log("传递JSON总" + jsonInfo);
                var url = rootPath
                    + '/receipt/saveAllEntity.shtml';
                var data = CommnUtil.ajax(url, {
                    info: jsonInfo
                }, "json");
                console.log(data);
                if (data == "success") {
                    window.location.href = rootPath
                        + '/receipt/showrsUI.shtml?reredocumentid='
                        + receipt.reredocumentid;
                    /*
                     * var showrsurl = var data =
                     * CommnUtil.ajax(showrsurl, null, null);
                     */
                    /*
                     * layer.confirm('保存成功!是否关闭窗口?',
                     * function(index) { parent.grid.loadData();
                     * parent.layer.close(parent.pageii); return
                     * true; });
                     */
                } else {
                    layer.alert('添加失败！', 3);
                }
            } else {
                layer.alert("此车为问题车辆！！", 3);
            }
        }

    });

    $("#reredocumentid").val(getDh());
    $('#retimeofreceipt').datetimepicker({
        format: 'yyyy-mm-dd',// 日期格式
        todayBtn: true, // 是否显示今天那个按钮
        language: 'zh-CN', // 设置中文显示 需要引入几个js
        minView: 2, // 最小视图
        maxView: 1
        // 最大视图
    });

});
