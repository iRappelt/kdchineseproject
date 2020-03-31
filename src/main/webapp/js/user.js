$(document).ready(function () {
    $.ajax({
        url: "/kdchinese/showuser",   // 请求路径
        type: "get",            // 请求的方式，不区分大小写
        async: true,             // 是否异步，true是默认值，false为同步请求
        cache: false,            // 关闭缓存，目的是为了避免部分浏览器缓存加载出错(IE)
        contentType: "application/json;charset=utf-8",
        datatype: "json",        // 返回类型，text文本、html页面、json数据
        // data: JSON.stringify(JsonData),
        success: function (data) {
            $('.content').html("<table class='add'>" +
                "<tr><th>id</th><th>用户名</th><th>电话</th><th>密码</th><th>身份</th><th>注册日期</th><th>性别</th><th>年级</th></tr>" +
                "</table>");
            $.each(data, function (index, values) {
                $('.add').append("<tr><td>"+values.uid+"</td>"+"<td>"+values.username+"</td>"+"<td>"+values.telephone+"</td>"+"<td>"
                    +values.password+"</td>"+"<td>"+values.identity+"</td>"+"<td>"+values.registdate+"</td>"+"<td>"+values.sex+"</td>"+"<td>"+values.grade+"</td></tr>");
                // alert(values.uid);
            });
        },
        error: function (response) {
            alert("请求出错-->" + JSON.stringify(response));
        }
    });
});