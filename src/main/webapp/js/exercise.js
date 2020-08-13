$(document).ready(function () {
    layui.use(['element', 'layer', 'table', 'form'], function(){
        var element = layui.element;
        var layer = layui.layer;
        var table = layui.table;
        var form = layui.form;

        // 动态加载时需要重新渲染，与之相同操作的还有form组件
        element.render();//2.1.6新增，原来为element.init()

        // 事件监听
        /*element.on('tab(exercise01-grade-filter)', function(data){
            console.log(this); //当前Tab标题所在的原始DOM元素
            console.log(data.index); //得到当前Tab的所在下标
            console.log(data.elem); //得到当前的Tab大容器
        });*/

    });

    // 加载首页
    $('#exercise_content').load("exercise01.html");

    // 面包屑点击时切换下方页面
    $("#exercise01").on("click", function () {
        // 改变面包屑选中状态字体
        $('#exercise01').html("<cite>汉字与拼音</cite>");
        $('#exercise02').html("笔顺与笔画");
        $('#exercise_content').load("exercise01.html");
    });
    $("#exercise02").on("click", function () {
        // 改变面包屑选中状态字体
        $('#exercise01').html("汉字与拼音");
        $('#exercise02').html("<cite>笔顺与笔画</cite>");
        $('#exercise_content').load("exercise02.html");
    });
});