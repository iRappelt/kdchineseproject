$(document).ready(function() {
    $(document).keypress(function (e) {
        // 回车键事件
        if (e.which == 13) {
            $('#register').click();
        }
    });
    $('#back').click(function () {
        history.back();
    });
    $('#register').click(function () {
        var username = $('input[name="username"]').val();
        var password = $('input[name="password"]').val();
        var telephone = $('input[name="telephone"]').val();
        //电话号码的正则
        var reg = /^1(3[0-9]|4[57]|5[012356789]|6[2567]|7[0178]|8[0-9]|9[189])\d{8}$/;
        // var code = $('input[name="code"]').val();
        if (username == '') {
            showMessage('请输入您的账号');
        } else if (password == '') {
            showMessage('请输入密码');
        } else if(telephone == '') {
            showMessage('请输入手机号');
        } else if (!reg.test(telephone)) {
            showMessage('请输入正确的手机号码')
        }else {
            //登陆
            var JsonData = {username: username, password: password, telephone: telephone};
            $.ajax({
                url: "check_register",   // 请求路径
                type: "post",            // 请求的方式，不区分大小写
                async: true,             // 是否异步，true是默认值，false为同步请求
                cache: false,            // 关闭缓存，目的是为了避免部分浏览器缓存加载出错(IE)
                contentType: "application/json;charset=utf-8",
                datatype: "json",        // 返回类型，text文本、html页面、json数据
                data: JSON.stringify(JsonData),
                success: function (data) {
                    //alert("数据: " + JSON.stringify(data));
                    //ajax返回
                    //认证完成
                    if (data.code == 0) {
                        //注册成功
                        // alert(data.message);
                        showMessage(data.message+',3秒后跳转到登录界面...');
                        setTimeout(function () {
                            window.location.href = "login.html"
                        },3000);
                    } else {
                        showMessage(data.message);
                    }
                },
                error: function (response) {
                    alert("注册出错-->" + JSON.stringify(response));
                }
            });
        }
    })
});

function showMessage(message) {
    var blockp = $('p#message');
    blockp.css({"padding-top":"5px","padding-bottom":"5px"});
    blockp.text(message);

}