$(document).ready(function () {

    layui.use(['element', 'layer', 'table', 'form'], function(){
        var element = layui.element;
        var layer = layui.layer;
        var table = layui.table;
        var form = layui.form;

        element.render();//2.1.6新增，原来为element.init()

        // 刚开始加载第一项
        $('#word-error-content').load("word_error.html");

        // 事件监听
        element.on('tab(error-bank-filter)', function(data){
            // console.log(this); //当前Tab标题所在的原始DOM元素
            // console.log(data.index); //得到当前Tab的所在下标
            // console.log(data.elem); //得到当前的Tab大容器

            var idx = data.index;
            if (idx == 0) {
                $('#word-error-content').load("word_error.html");
            } else if (idx == 1) {
                $('#pinyin-error-content').load("pinyin_error.html");
            } else if (idx == 2) {
                //...
            }
        });

    });//layui-use-end

});//ready-end