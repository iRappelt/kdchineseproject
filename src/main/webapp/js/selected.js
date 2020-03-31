$(document).ready(function () {
    $('.functionList li a').each(function () {
        //获得网址的uri
        var start = String(window.location).lastIndexOf('/');
        var length = String(window.location).length;
        var url = String(window.location).substring(start + 1, length);
        //与a标签的href属性相比较
        if ($(this).attr("href") == url) {
            //去掉以前所点击的a标签样式，添加目前被点击的a标签的样式
            $('.functionList li a').removeClass("selected");
            $(this).addClass("selected");

        }
    });
});