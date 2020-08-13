$(document).ready(function () {
    //为layui数据表格先建立基础表格
    $('#word-error-content').html("<table id='word-error-table' lay-filter='word-error-table-filter' ></table>");

    layui.use(['element', 'layer', 'table', 'form'], function(){
        var element = layui.element;
        var layer = layui.layer;
        var table = layui.table;
        var form = layui.form;

        var urls = ["show_word_error",
            "delete_word_error",
            "batch_delete_word_error",
            "update_word_error",
            "add_word_error"];// 请求地址数组

        // 动态加载时需要重新渲染element元素，否则可能会失效
        element.render();//2.1.6新增，原来为element.init()

        //layui表格渲染函数
        var tableIns = table.render({
            id: 'wordErrorTable',
            elem: "#word-error-table",
            url: urls[0],// 后端接口地址
            parseData: function(res){ //res 即为原始返回的数据
                return {
                    "code": res.code, //解析接口状态
                    "msg": res.message, //解析提示文本
                    "count": res.data[1], //解析数据长度
                    "data": res.data[0] //解析数据列表
                }
            },
            title: '汉字错题表',
            toolbar: '#headBarDemo',
            page: {
                limit: 10 //一页显示多少条
                , limits: [10, 15, 20]//每页条数的选择项
                , groups: 2 //只显示 2 个连续页码
            },
            cols: [[
                {type: 'checkbox', fixed: 'left'},
                {field: 'id', title: 'ID'},
                {field: 'word', title: '答案'},
                {field: 'pinyin', title: '拼音'},
                {field: 'title', title: '题目'},
                {field: 'word_grade', title: '所属年级'},
                {field: 'frequency', title: '频次'},
                {field: 'user_id', title: '用户id'},
                {title: '操作', fixed: 'right', align: 'center', toolbar: '#lineBarDemo'}
            ]]
        });//table-end


        //layui监听每行工具菜单的操作
        table.on('tool(word-error-table-filter)', function (obj) { //注：tool 是工具条事件名，userTableFilter 是 table 原始容器的属性 lay-filter="对应的值"
            var data = obj.data; //获得当前行数据
            var layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
            var tr = obj.tr; //获得当前行 tr 的 DOM 对象（如果有的话）


            if (layEvent === 'del') { //删除
                layer.confirm('你真的要删除吗?', {icon: 0, title: '提示'}, function (index) {
                    layer.close(index);
                    //加载效果
                    var loadIndex = layer.load(2, {time: 10 * 1000}); //2为样式，后面为设定最长等待10秒
                    //向服务端发送删除指令
                    //Ajax
                    $.get(urls[1]+"?id=" + data.id, function (res) {
                        if (res.code == 0) {
                            //删除成功
                            obj.del(); //删除对应行（tr）的DOM结构，并更新缓存
                            layer.close(loadIndex);
                            layer.msg('删除成功', {
                                icon: 1,
                                time: 1000 //1秒关闭（如果不配置，默认是3秒）
                            });
                        } else {
                            //删除失败
                            layer.close(loadIndex);
                            layer.msg('删除失败', {
                                icon: 2,
                                time: 1000 //1秒关闭（如果不配置，默认是3秒）
                            });
                        }
                    });
                });
            } else if (layEvent === 'edit') { //编辑
                //do something
                layer.open({
                    //layer提供了5种层类型。可传入的值有：0（信息框，默认）1（页面层）2（iframe层）3（加载层）4（tips层）
                    type: 1,
                    title: "编辑信息",
                    //定义弹出层宽度，高度自适应
                    area: '500px',
                    content: $("#word-error-edit-form")//引用修改界面表单
                });
                //调用函数，表单赋值，和数据传递后端
                editForm(obj, data);
            }
        });//table-on--tool-end

        //监听头部操作菜单栏
        table.on('toolbar(word-error-table-filter)', function (obj) {
            var checkStatus = table.checkStatus('wordErrorTable');
            switch (obj.event) {
                //添加
                case 'add':
                    layer.open({
                        //layer提供了5种层类型。可传入的值有：0（信息框，默认）1（页面层）2（iframe层）3（加载层）4（tips层）
                        type: 1,
                        title: "添加数据",
                        //定义弹出层宽度，高度自适应
                        area: '500px',
                        content: $("#word-error-add-form")//引用修改界面表单
                    });
                    //调用函数，表单赋值，和数据传递后端
                    addForm();
                    break;
                //删除
                case 'delete':
                    if (checkStatus.data.length == 0) {
                        layer.msg('请先选择要删除的数据行！', {icon: 2});
                        return;
                    }
                    //获取选中行的数据
                    var checkedData = checkStatus.data;
                    //存储每行id的列表
                    var delList = [];
                    checkedData.forEach(function (n, i) {
                        delList.push(n.id);
                    });
                    var jsonData = {delList: delList};
                    layer.confirm('确定删除所选项吗？', {icon: 0, title: '提示'}, function (index) {
                        //关闭确认弹出层
                        layer.close(index);
                        //向服务端发送删除指令
                        //加载效果
                        var loadIndex = layer.load(2, {time: 10 * 1000}); //2为样式，后面为设定最长等待10秒
                        $.ajax({
                            url: urls[2],
                            type: 'post',
                            contentType: "application/json;charset=utf-8",
                            dataType: 'json',
                            data: JSON.stringify(jsonData),
                            success: function (res) {
                                //关闭加载弹出层
                                layer.close(loadIndex);
                                if (res.code == 0) {
                                    //删除成功
                                    layer.msg('删除成功', {
                                        icon: 1,
                                        time: 1000 //1秒关闭（如果不配置，默认是3秒）
                                    });
                                    //重载表格
                                    tableIns.reload();
                                } else {
                                    //删除失败
                                    layer.msg('删除失败', {
                                        icon: 2,
                                        time: 1000 //1秒关闭（如果不配置，默认是3秒）
                                    });
                                }
                            },
                            error: function (response) {
                                //关闭加载弹出层
                                layer.close(loadIndex);
                                layer.alert("请求出错");
                            }
                        })
                    });
                    break;
                //刷新
                case 'refresh':
                    tableIns.reload();
                    break;
            }
        });//table-on-toolbar

        //信息编辑的弹出层所需的表单设值函数
        function editForm(obj, data) {
            //设置表单呈现的默认值
            form.val("word-error-edit-filter", { //editFilter 即 class="layui-form" 所在元素属性 lay-filter="" 对应的值
                "answer": data.word // "name": "value"
                , "pinyin": data.pinyin
                , "title": data.title
                , "grade": data.word_grade
                , "frequency": data.frequency
                , "userid": data.user_id
            });
            //表单提交监听,edit*为提交按钮中设置的过滤器
            form.on('submit(word-error-edit*)', function (formdata) {
                //加载效果
                var loadIndex = layer.load(2, {time: 10 * 1000}); //2为样式，后面为设定最长等待10秒
                var jsonData = {
                    id: data.id
                    , answer: formdata.field.answer
                    , pinyin: formdata.field.pinyin
                    , title: formdata.field.title
                    , grade: formdata.field.grade
                    , frequency: formdata.field.frequency
                    , userid: formdata.field.userid
                };
                $.ajax({
                    url: urls[3],   // 请求路径
                    type: "post",            // 请求的方式，不区分大小写
                    async: true,             // 是否异步，true是默认值，false为同步请求
                    cache: false,            // 关闭缓存，目的是为了避免部分浏览器缓存加载出错(IE)
                    contentType: "application/json;charset=utf-8",
                    datatype: "json",        // 返回类型，text文本、html页面、json数据
                    data: JSON.stringify(jsonData),
                    success: function (res) {
                        if (res.code == 0) {
                            //更新成功
                            //关闭所有弹出层
                            layer.closeAll();
                            //关闭加载动画
                            layer.close(loadIndex);
                            layer.msg('更新成功', {
                                icon: 1,
                                time: 1000 //1秒关闭（如果不配置，默认是3秒）
                            });
                            //同步更新缓存对应的值（更新表格中的数据，不用再次请求刷新）
                            obj.update({
                                answer: formdata.field.answer
                                , pinyin: formdata.field.pinyin
                                , title: formdata.field.title
                                , grade: formdata.field.grade
                                , frequency: formdata.field.frequency
                                , userid: formdata.field.userid
                            });

                        } else {
                            //更新失败
                            layer.close(loadIndex);
                            layer.msg('更新失败', {
                                icon: 2,
                                time: 1000 //1秒关闭（如果不配置，默认是3秒）
                            });
                        }
                    },
                    error: function (response) {
                        layer.close(loadIndex);
                        layer.alert("请求出错");
                    }
                });
                //阻止表单跳转
                return false;
            });
        }//function-end

        //信息添加的弹出层所需的表单设值函数
        function addForm() {
            //设置表单呈现的默认值
            form.val("word-error-add-filter", { //editFilter 即 class="layui-form" 所在元素属性 lay-filter="" 对应的值
                "answer": '' // "name": "value"
                , "pinyin": ''
                , "title": ''
                , "grade": ''
                , "frequency": ''
                , "userid": ''
            });
            //表单提交监听
            form.on('submit(word-error-add*)', function (formdata) {
                //加载效果
                var loadIndex = layer.load(2, {time: 10 * 1000}); //2为样式，后面为设定最长等待10秒
                var jsonData = {
                    answer: formdata.field.answer
                    , pinyin: formdata.field.pinyin
                    , title: formdata.field.title
                    , grade: formdata.field.grade
                    , frequency: formdata.field.frequency
                    , userid: formdata.field.userid
                };
                $.ajax({
                    url: urls[4],   // 请求路径
                    type: "post",            // 请求的方式，不区分大小写
                    async: true,             // 是否异步，true是默认值，false为同步请求
                    cache: false,            // 关闭缓存，目的是为了避免部分浏览器缓存加载出错(IE)
                    contentType: "application/json;charset=utf-8",
                    datatype: "json",        // 返回类型，text文本、html页面、json数据
                    data: JSON.stringify(jsonData),
                    success: function (res) {
                        if (res.code == 0) {
                            //添加成功
                            //关闭所有弹出层
                            layer.closeAll();
                            //关闭加载动画
                            layer.close(loadIndex);
                            layer.msg('添加成功', {
                                icon: 1,
                                time: 1000 //1秒关闭（如果不配置，默认是3秒）
                            });
                            //重载表格
                            tableIns.reload();
                        } else {
                            //添加失败
                            layer.close(loadIndex);
                            layer.msg(res.message, {
                                icon: 2,
                                time: 1000 //1秒关闭（如果不配置，默认是3秒）
                            });
                        }
                    },
                    error: function (response) {
                        layer.close(loadIndex);
                        layer.alert("请求出错");
                    }
                });
                //阻止表单跳转
                return false;
            });
        }//function-end

    });//layui-use-end

});//ready-end