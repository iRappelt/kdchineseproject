
$(document).ready(function() {
    //输入框获得焦点改变icon亮度
    $('input').focus(function () {
       $(this).prev().attr('style', 'opacity: 1.0;');
    });
    //失去焦点
    $('input').blur(function () {
        $(this).prev().attr('style', 'opacity: 0.5;');
    });
    $(document).keypress(function (e) {
        // 回车键事件
        if (e.which == 13) {
            $('input[type="button"]').click();
        }
    });
    //粒子背景特效
    $('body').particleground({
        dotColor: '#E8DFE8',
        lineColor: '#133b88'
    });
    //非空判断
    $('input[type="button"]').click(function () {
        var username = $('input[name="username"]').val();
        var password = $('input[name="password"]').val();
        // var code = $('input[name="code"]').val();
        if (username == '') {
            showMessage('请输入您的账号');
        } else if (password == '') {
            showMessage('请输入密码');
        }
        else {
            //登陆
            var JsonData = {username: username, password: password};
            $.ajax({
                url: "/kdchinese/chklogin",   // 请求路径
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
                    if (data.status == '200') {
                        //登录成功
                        // alert(data.message);
                        window.location.href = "/kdchinese/index.html"
                    } else {
                        showMessage(data.message);
                    }
                },
                error: function (response) {
                    alert("登录出错-->" + JSON.stringify(response));
                }
            });
        }
    })
});
//展示信息在界面上
function showMessage(message) {
    var blockp = $('p#message');
    blockp.css({"padding-top":"5px","padding-bottom":"5px"});
    blockp.text(message);

}