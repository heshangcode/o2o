    // 点击删除按钮
    function deleteTr(obj) {
    //	alert("sadasd");
        $(obj).parents("tr").remove();
    }

    //计算两个日期之间相差的天数
    function datedifference(sDate1, sDate2) {    //sDate1和sDate2是2006-12-18格式
        var dateSpan,
            tempDate,
            iDays;
        sDate1 = Date.parse(sDate1);
        sDate2 = Date.parse(sDate2);
        dateSpan = sDate2 - sDate1;
        dateSpan = Math.abs(dateSpan);
        iDays = Math.floor(dateSpan / (24 * 3600 * 1000));
        return iDays;
    };
    var summoney = 0;
    $(function () {
        var iDays = 1;
        var ac = 0;
        // 动态生成车牌号下拉框
        $("#retimeofreceipt").val(getNowDateToSs());
        $("#reredocumentid").val(getDh());
        $('#retimeofreceipt').datetimepicker({
            format: 'yyyy-mm-dd hh:ii:ss',
            todayBtn: true,
            language: 'zh-CN',
            autoclose: true,	//选中时间后自动关闭
            todayHighlight: true,	//当天时间高亮
            minView: 0,
            startView: 0,
            minuteStep: 2,
            maxView: 1
        });
        $('#restarttime').datetimepicker({
            format: 'yyyy-mm-dd',// 日期格式
            todayBtn: true, // 是否显示今天那个按钮
            language: 'zh-CN', // 设置中文显示 需要引入几个js
            autoclose: true,	//选中时间后自动关闭
            todayHighlight: true,	//当天时间高亮
            minView: 2, // 最小视图
            maxView: 1 // 最大视图
        });
        $('#reendtime').datetimepicker({
            format: 'yyyy-mm-dd',// 日期格式
            todayBtn: true, // 是否显示今天那个按钮
            autoclose: true,	//选中时间后自动关闭
            todayHighlight: true,	//当天时间高亮
            language: 'zh-CN', // 设置中文显示 需要引入几个js
            minView: 2, // 最小视图
            maxView: 1 // 最大视图
        });


    //	当车牌号被改变的时候
        $("#cplateid").change(function () {

            $("#Mytable").empty();
            carAndOwner(this);
            var data = $("#cid").val();
    //		alert(data);
    //		验证cid是否有值
            if (data != "" && data != null) {

                // 判断这个车是不是问题车
                var iserrorurl = rootPath + '/receipt/iserror.shtml?id=' + data;
                var iserror = CommnUtil.ajax(iserrorurl, null, "json");
    //			alert(iserror);
                // 车没有问题
                if (iserror == "success") {

    //				判断车是否在周结，月结，欠款那还有未交清的钱
                    var isReceiptUrl = rootPath + '/receiptbefore/isreceipt.shtml?cid=' + data;
                    var isreceipt = CommnUtil.ajax(isReceiptUrl, null, "json");
                    console.log("此车是否有问题：" + isreceipt);

                    if (isreceipt == "success") {
                        // 动态获取车的收费信息各种
                        var urlChargeSon = rootPath + '/receipt/selectchargeson.shtml?cid=' + data;
                        var chargeSonData = CommnUtil.ajax(urlChargeSon, null, "json");
                        console.log("收费的各种信息:" + chargeSonData.length);

                        console.log("从收费表查出这个车的收费并添加开始");
                        if (chargeSonData.length > 0) {
                            var chargeH = "";
                            formLength = chargeSonData.length;

    //					获取费用名称 为日租金的index
                            var chpaytype = "日租金";
                            var money = 0;
                            var index = 0;
                            // 把应收金额存进去
                            for (var i = 0; i < formLength; i++) {
                                if (chargeSonData[i].chname == "日租金") {
                                    index = i;
                                    money = chargeSonData[i].chmoney;
                                }
                                chargeH = "<tr style='margin:0 auto;'>"
                                    + "<td style='margin-left:15px;display:inline-block;'><label>费用名称</label> <input value='"
                                    + chargeSonData[i].chname
                                    + "' type='text' readonly='readonly' name='receiptSonFormMap.rsname' id='rsname' style='width:90px;' /></td>"
                                    + "<td style='margin-left:27px;display:inline-block;'><label>支付方式</label> "
                                    + "<input type='text' readonly='readonly' value='"
                                    + chargeSonData[i].chpaytype
                                    + "' name='receiptSonFormMap.rsablemoney' id='chpaytype"
                                    + i
                                    + "' style='width:90px;' /></td>"
                                    + "<td style='margin-left:27px;display:inline-block;'><label>应收金额</label> "
                                    + "<input type='text' readonly='readonly' value='"
                                    + chargeSonData[i].chmoney
                                    + "' name='receiptSonFormMap.rsablemoney' id='rsablemoney"
                                    + i
                                    + "'  style='width:90px;'/></td>"
                                    + "<td style='margin-left:27px;display:inline-block;'><label>实收金额</label><input type='text' name='receiptSonFormMap.rsfundsmoney' value='"
                                    + chargeSonData[i].chmoney
                                    + "' id='rsfundsmoney"
                                    + i
                                    + "' style='width:90px;'/></td>"
                                    + "<td hidden ><label>欠款</label><input type='text' readonly='readonly' name='receiptSonFormMap.rrsowemoney' value='0' id='rsowemoney"
                                    + i + "' style='width:90px;' /></td>"
                                    + "<td style='margin-left:27px;display:inline-block;'><label>预收周/月数</label><input type='text' name='receiptSonFormMap.recount' value='' id='recount"
                                    + i
                                    + "' style='width:90px;'/></td>"
                                    + "<td style='margin-left:27px;display:inline-block;'><label>预收区间</label><input readonly='readonly' type='text' name='receiptSonFormMap.recount' value='' id='resummary"
                                    + i
                                    + "'/></td>"
                                    + "</tr>"
                                //拼接好的html追加到表单后面
                                $("#Mytable").append(chargeH);
                                summoney = summoney + chargeSonData[i].chmoney;
                                $("#summoney").val(summoney);
                                var oldsummoney =  $("#summoney").val();
                                alert("前"+oldsummoney);
                            }
                        } else {
                            layer.msg('此车未设置收费信息，请添加');
                        }

                        console.log("从收费表查出这个车的收费并添加结束");

                        //遍历监听，实收金额，预收周数/月数，
                        for (var i = 0; i < formLength; i++) {

                            // 实收金额ID
                            var rsfundsmoneyId = "#rsfundsmoney" + i;
                            // 欠款ID
                            var rsowemoneyId = "#rsowemoney" + i;
                            // 预收周/月数ID
                            var recountId = "#recount" + i;
                            // 预收区间ID
                            var resummaryId = "#resummary" + i;
    //						alert(recountId);

    //					当预收周/月数改变
                            $(recountId).change(function () {

    //						每次周/月数改变给欠款赋值为0
                                $(this).parents().prev().children().next().val(0);
                                var id = 0;
                                // 获取输入的周/月数
                                var rsfundsmoney = $(this).val().trim();
    //							alert(rsfundsmoney);
    //                          费用名
                                var rsname = $(this).parents().prev().prev().prev().prev().prev().children().next().val();
                                // 支付方式
                                var chpaytype = $(this).parents().prev().prev().prev().prev().children().next().val();
                                // 收费区间的定位
                                var resummaryId = $(this).parents().next().children().next();
                                // 获取应收金额的日租金
                                if (recountId.indexOf("recount") >= 0) {
                                    id = recountId.substr(8);
                                }
                                // 获取这个收费项这一行的应收金额
                                var chmoney = chargeSonData[id].chmoney;
                                var money = 0;
                                // 如果为周结,计算钱和预收区间,且为日租金的改变
                                if (chpaytype == "周结") {
                                    if (rsname == "日租金") {
                                        money = chmoney * 7 * Number(rsfundsmoney);
                                    } else {
                                        money = chmoney * Number(rsfundsmoney);
                                    }

    //							计算出预收区间
                                    $(resummaryId).val(getNWeek(rsfundsmoney)[0] + "~" + getNWeek(rsfundsmoney)[1]);
                                }
                                // 月结
                                if (chpaytype == "月结") {
                                    if (rsname == "日租金") {
                                        money = chmoney * datedifference(getnmonth(rsfundsmoney)[0], getnmonth(rsfundsmoney)[1]);
                                    } else {
                                        money = chmoney * Number(rsfundsmoney);
                                    }
    //							alert(rsfundsmoney);
                                    $(resummaryId).val(getnmonth(rsfundsmoney)[0] + "~" + getnmonth(rsfundsmoney)[1]);
                                }
                                var oldsummoney =  $("#summoney").val();
                                alert("前"+oldsummoney);
                                summoney = oldsummoney-Number($(this).parents().prev().prev().prev().children().next().val());
                                alert("后"+summoney);
                                $("#summoney").val(summoney+Number(money));
                                // 给应收金额和实收金额改变值
                                $(this).parents().prev().prev().prev().children().next().val(money);
                                $(this).parents().prev().prev().children().next().val(money);
                                // 获取应收的金额
                                var rsablemoney = $(this).parents().prev().children().next().val().trim();
                                console.log("实收金额钱：" + rsfundsmoney + ",应收金额钱：" + rsablemoney);
                            });

    //					当实收金额那个框的值被改变时
                            $(rsfundsmoneyId).change(function () {
                                // 获取输入的实收金额钱
                                var rsfundsmoney = $(this).val().trim();
                                // 获取应收的金额
                                var rsablemoney = $(this).parents().prev().children().next().val().trim();
                                // 欠款的定位
                                var rsowemoneyId = $(this).parents().next().children().next();
                                console.log("实收金额钱：" + rsfundsmoney + ",应收金额钱：" + rsablemoney);
                                $(rsowemoneyId).val(rsablemoney - rsfundsmoney);
                            });
                        }
                    } else {
                        layer.alert('此车有未结款项！', 3);
                    }
                } else {
                    layer.alert("此车为问题车辆！！", 3);
                }
            }
        });

        // 点击提交按钮触发，获取表单元素的值
        $("#addAll").click(function () {
    //		1.判断车是否问题
            // 判断这个车是不是问题车
            var data = $("#cid").val();
            var iserrorurl = rootPath + '/receipt/iserror.shtml?id=' + data;
            var iserror = CommnUtil.ajax(iserrorurl, null, "json");
    //		alert(iserror);
            // 车没有问题
            if (iserror == "success") {
    //			2.判断车是否有未结
                var isReceiptUrl = rootPath + '/receiptbefore/isreceipt.shtml?cid=' + data;
                var isreceipt = CommnUtil.ajax(isReceiptUrl, null, "json");
    //			alert(isreceipt);
                if (isreceipt == "success") {
                    // 定义数组保存表单所有数据
                    var receiptList = new Array();

                    var receipt = new Object();
                    receipt.reredocumentid = $("#reredocumentid").val();
                    receipt.cid = $("#cid").val();
                    receipt.cplateid = $("#cplateid").val();
                    receipt.coname = $("#coname").val();
                    receipt.rechamberlain = $("#rechamberlain").val();
                    receipt.retimeofreceipt = $("#retimeofreceipt").val();
                    receipt.restarttime = $("#restarttime").val();
                    receipt.reendtime = $("#reendtime").val();

                    receiptList[0] = receipt;
                    console.log("固定表单元素值" + receiptList);
                    // 遍历获取动态的表单元素值
                    var otbl = document.getElementById('Mytable'); // 获取table对象，名字为otbl
                    var anjianName = "";
                    var anjianNum = "";
                    var len = otbl.rows.length // 获取otbl的行数
    //				 alert("行数"+len);
                    for (var i = 0; i < len; i++) {
                        var receiptSon = new Object();
                        //收款日期
                        receiptSon.retimeofreceipt = receipt.retimeofreceipt;
                        //费用名
                        receiptSon.rsname = otbl.rows[i].cells[0].children[1].value;
                        //支付方式
                        receiptSon.chpaytype = otbl.rows[i].cells[1].children[1].value;
                        //应收金额
                        receiptSon.rsablemoney = otbl.rows[i].cells[2].children[1].value;
                        //实收金额
                        receiptSon.rsfundsmoney = otbl.rows[i].cells[3].children[1].value;
                        //欠款
                        receiptSon.rsowemoney = otbl.rows[i].cells[4].children[1].value;
                        //预收周/月数
                        receiptSon.recount = otbl.rows[i].cells[5].children[1].value;
                        //预收区间
                        receiptSon.resummary = otbl.rows[i].cells[6].children[1].value;
                        //给它设置为预收
                        receiptSon.reisreceive = "预收";
                        //预收金钱总额和预收金钱剩余钱
                        receiptSon.remoney = receiptSon.rsfundsmoney;
                        receiptSon.restmoney = receiptSon.rsfundsmoney;
                        //给预收区间拆分为起始时间和截止时间
                        receiptSon.restarttime = receiptSon.resummary.split("~")[0];
                        receiptSon.reendtime = receiptSon.resummary.split("~")[1];

                        // alert(receiptSon.recount);
                        console.log(receiptSon.rsname + "+"
                            + receiptSon.rsablemoney + "+"
                            + receiptSon.rsfundsmoney + "+"
                            + receiptSon.rsowemoney + "+" + receiptSon.recount + "+" + receiptSon.resummary);
                        receiptList.push(receiptSon);
                    }

                    receiptList[0].recount = iDays;

                    // 创建后台的接收路由
                    var jsonInfo = JSON.stringify(receiptList);
                    console.log("传递JSON总" + jsonInfo);
                    var url = rootPath + '/receiptbefore/saveAllEntity.shtml';
                    var data = CommnUtil.ajax(url, {
                        info: jsonInfo
                    }, "json");
                    console.log(data);
                    if (data == "success") {
                        layer.confirm('保存成功!是否关闭窗口?', function (index) {
                            parent.grid.loadData();
                            parent.layer.close(parent.pageii);
                            return true;
                        });
                    } else {
                        layer.alert('添加失败！', 3);
                    }
                } else {
                    layer.alert('此车有未结款项！', 3);
                }
            }
            else {
                layer.alert("此车为问题车辆！！", 3);
            }
        });

    });
