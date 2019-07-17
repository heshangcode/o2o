$("#submit").click(function () {
//    获取前台数据
//    创建person对象
    var person = {};
//    获取表单里的数据
    person.account = $('#person-account').val();
    person.password = $('#person-password').val();
    // alert(person.account);
    //传送数据到后台验证
    $.ajax({
        url: '/o2o/person/login',
        type: 'POST',
        data: person,
        success: function (data) {
            if (data.success) {
                $.toast('登录成功！');
                window.location.href = 'shopadmin/shoplist';
            }else {
                $.toast('登录失败,账号或密码错误！');
            }
        }
    })
})