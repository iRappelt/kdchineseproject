
$(document).ready(function() {
    //输入框获得焦点改变icon亮度
    $('input').focus(function () {
       $(this).prev().attr('style', 'opacity: 1.0;');
        hideMessage();//隐藏提示信息
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


    //验证码生成

    var validateCode = '';
    draw();

    function randomRgbColor() { //随机生成RGB颜色
        var r = Math.floor(Math.random() * 256); //随机生成256以内r值
        var g = Math.floor(Math.random() * 256); //随机生成256以内g值
        var b = Math.floor(Math.random() * 256); //随机生成256以内b值
        return "rgb(" + r + "," + g + "," + b + ")"; //返回rgb(r,g,b)格式颜色
    }

    function draw(){
        var canvas = document.getElementById("validateCode");
        var context = canvas.getContext("2d");
        canvas.width = 110;
        canvas.height = 35;
        // context.strokeRect(0, 0, 110, 35);//边框
        var aCode = "A,B,C,E,F,G,H,J,K,L,M,N,P,Q,R,S,T,W,X,Y,1,2,3,4,5,6,7,8,9";
        // console.log(aCode.split(","));
        var aLength = aCode.split(",").length;
        for (var i = 0; i <= 3; i++) {
            var x = 20 + i * 20;
            var y = 20 ;
            var j = Math.floor(Math.random() * aLength);
            var deg = (Math.random()) * 45 * Math.PI / 180;//随机弧度
            var txt = aCode.split(",")[j];
            context.fillStyle = randomRgbColor();
            context.font = "bold 20px 微软雅黑";
            //修改坐标原点和旋转角度
            context.translate(x, y);
            context.rotate(deg);
            context.fillText(txt, 0, 0);
            //恢复坐标原点和旋转角度
            context.rotate(-deg);
            context.translate(-x, -y);
            //存储验证码code
            validateCode += txt;
        }
        // //干扰线
        // for (var i = 0; i < 8; i++) {
        //     context.strokeStyle = randomRgbColor();
        //     context.beginPath();
        //     context.moveTo(Math.random() * 120, Math.random() * 40);
        //     context.lineTo(Math.random() * 120, Math.random() * 40);
        //     context.stroke();
        // }
        // /**绘制干扰点**/
        // for (var i = 0; i < 20; i++) {
        //     context.fillStyle = randomRgbColor();
        //     context.beginPath();
        //     context.arc(Math.random() * 120, Math.random() * 40, 1, 0, 2 * Math.PI);
        //     context.fill();
        // }
    }
    //刷新验证码
    $('#validateCode').click(function () {
        draw();
    });


    //非空判断
    $('input[type="button"]').click(function () {
        var username = $('input[name="username"]').val();
        var password = $('input[name="password"]').val();
        var code = $('input[name="code"]').val().toUpperCase();
        // var code = $('input[name="code"]').val();
        if (username == '') {
            showMessage('请输入您的账号');
        } else if (password == '') {
            showMessage('请输入密码');
        } else if (code !== validateCode) {
            showMessage('验证码错误，请重试');
            validateCode = '';
            draw();
        }
        else {
            //登陆
            var JsonData = {username: username, password: password};
            $.ajax({
                url: "chklogin",   // 请求路径
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
                        //登录成功
                        // alert(data.message);
                        window.location.href = "main.html";
                    } else {
                        showMessage(data.message);
                        validateCode = '';
                        draw();//重新生成验证码
                    }
                },
                error: function (response) {
                    alert("登录出错-->" + JSON.stringify(response));
                    validateCode = '';
                    draw();//重新生成验证码
                }
            });
        }
    });

    // 注册点击事件
    $('#regist').on("click", function () {
        alert("暂不提供注册功能");
    })

});
//展示信息在界面上
function showMessage(message) {
    var blockp = $('p#message');
    blockp.css({"display": "block","padding-top":"5px","padding-bottom":"5px","color":"red"});
    blockp.text(message);

}
//隐藏信息
function hideMessage(message) {
    var blockp = $('p#message');
    blockp.css({"display": "none"});
}