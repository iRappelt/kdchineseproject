$(document).ready(function () {

    //为layui数据表格先建立基础表格
    //$('#content').append("<table id='showUser' lay-filter='userTableFilter' ></table>");
    layui.use(['table', 'layer', 'form'], function () {
        var table = layui.table;
        var layer = layui.layer;
        var form = layui.form;

        var urls = ["show_word",
            "delete_word",
            "batch_delete_word",
            "update_word",
            "add_word"];// 请求地址数组

        //layui表格渲染
        var tableIns = table.render({
            id: 'wordTable',
            elem: '#showWord',
            url: urls[0],// 修改
            parseData: function(res){ //res 即为原始返回的数据
                return {
                "code": res.code, //解析接口状态
                "msg": res.message, //解析提示文本
                "count": res.data[1], //解析数据长度
                "data": res.data[0] //解析数据列表
                }
            },
            title: '汉字信息',
            toolbar: '#headBarDemo',
            page: {
                limit: 10 //一页显示多少条
                , limits: [10, 15, 20]//每页条数的选择项
                , groups: 2 //只显示 2 个连续页码
            },
            cols: [[
                {type: 'checkbox', fixed: 'left'},
                {field: 'id', title: 'ID'},
                {field: 'name', title: '汉字'},
                {field: 'pinyin', title: '拼音'},
                {field: 'pianpang', title: '偏旁'},
                {field: 'bihuanum', title: '笔画数'},
                {field: 'bishun', title: '笔顺'},
                {field: 'bishuncode', title: '笔顺码'},
                {field: 'phrase', title: '词组'},
                {field: 'gif', title: 'GIF'},
                {field: 'grade', title: '所属年级'},
                {title: '操作', fixed: 'right', align: 'center', width: '10%', toolbar: '#lineBarDemo'}
            ]]
        });//table-end

        //layui监听每行工具菜单的操作
        table.on('tool(wordTableFilter)', function (obj) { //注：tool 是工具条事件名，userTableFilter 是 table 的属性 lay-filter="对应的值"
            var data = obj.data; //获得当前行数据
            var layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
            var tr = obj.tr; //获得当前行 tr 的 DOM 对象（如果有的话）


            if (layEvent === 'del') { //删除
                layer.confirm('你真的要删除吗?', {icon: 0, title: '提示'}, function (index) {
                    layer.close(index);
                    //加载效果
                    var loadIndex = layer.load(2, {time: 10 * 1000}); //2为样式，后面为设定最长等待10秒
                    //向服务端发送删除指令
                    //Ajax，需改下方的url
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
                    content: $("#word-edit-form")//引用修改界面表单
                });
                //调用函数，表单赋值，和数据传递后端
                editForm(obj, data);
            }

        });//table-on--tool-end

        //监听头部操作菜单栏
        table.on('toolbar(wordTableFilter)', function (obj) {
            var checkStatus = table.checkStatus('wordTable');
            switch (obj.event) {
                //添加
                case 'add':
                    layer.open({
                        //layer提供了5种层类型。可传入的值有：0（信息框，默认）1（页面层）2（iframe层）3（加载层）4（tips层）
                        type: 1,
                        title: "添加数据",
                        //定义弹出层宽度，高度自适应
                        area: '500px',
                        content: $("#word-add-form")//引用修改界面表单
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
                            url: urls[2],// 修改
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
            form.val("word-edit-filter", { //editFilter 即 class="layui-form" 所在元素属性 lay-filter="" 对应的值
                "name": data.name // "name": "value"
                , "pinyin": data.pinyin
                , "pianpang": data.pianpang
                , "bihuanum": data.bihuanum
                , "bishun": data.bishun
                , "bishuncode": data.bishuncode
                , "phrase": data.phrase
                , "explain": data.explain
                , "gif": data.gif
                , "grade": data.grade
            });
            //表单提交监听
            form.on('submit(word-edit*)', function (formdata) {
                //加载效果
                var loadIndex = layer.load(2, {time: 10 * 1000}); //2为样式，后面为设定最长等待10秒
                var jsonData = {
                    id: data.id
                    , name: formdata.field.name
                    , pinyin: formdata.field.pinyin
                    , pianpang: formdata.field.pianpang
                    , bihuanum: formdata.field.bihuanum
                    , bishuncode: formdata.field.bishuncode
                    , phrase: formdata.field.phrase
                    , explain: formdata.field.explain
                    , bishun: formdata.field.bishun
                    , gif: formdata.field.gif
                    , grade: formdata.field.grade
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
                                name: formdata.field.name
                                , pinyin: formdata.field.pinyin
                                , pianpang: formdata.field.pianpang
                                , bihuanum: formdata.field.bihuanum
                                , bishuncode: formdata.field.bishuncode
                                , phrase: formdata.field.phrase
                                , explain: formdata.field.explain
                                , bishun: formdata.field.bishun
                                , gif: formdata.field.gif
                                , grade: formdata.field.grade
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
            form.val("word-add-filter", { //editFilter 即 class="layui-form" 所在元素属性 lay-filter="" 对应的值
                name: ""
                , pinyin: ""
                , pianpang: ""
                , bihuanum: ""
                , bishuncode: ""
                , phrase: ""
                , explain: ""
                , bishun: ""
                , gif: ""
                , grade: ""
            });
            //表单提交监听
            form.on('submit(word-add*)', function (formdata) {
                //加载效果
                var loadIndex = layer.load(2, {time: 10 * 1000}); //2为样式，后面为设定最长等待10秒
                var jsonData = {
                    name: formdata.field.name
                    , pinyin: formdata.field.pinyin
                    , pianpang: formdata.field.pianpang
                    , bihuanum: formdata.field.bihuanum
                    , bishuncode: formdata.field.bishuncode
                    , phrase: formdata.field.phrase
                    , explain: formdata.field.explain
                    , bishun: formdata.field.bishun
                    , gif: formdata.field.gif
                    , grade: formdata.field.grade
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

