$(document).ready(function () {

    // // 获取session中的教师信息
    // $.get("get_session_user", function (res) {
    //     if(res.data==null){
    //         alert("获取不到用户信息，请重新登录再试！");
    //         return;
    //     }
    //     // teacher_id = res.data.uid;
    //
    //     sessionStorage.setItem('teacher_id', res.data.uid);
    //
    //
    // });//ajax-1-end



    // 获取教师id对应的班级信息
    $.get("get_class_info?teacherId="+sessionStorage.getItem('teacher_id'), function (res) {
        if(res.data==null || res.data==''){
            return;
        }
        // 班级名数组
        var class_names = [];
        // 班级id数组
        var class_ids = [];

        res.data['classNames'].forEach(function (value, index) {
            class_names.push(value);
        });
        res.data['classIds'].forEach(function (value, index) {
            class_ids.push(value);
        });
        // 将班级名和班级id存储到缓存中，每次加载这个页面都会更新缓存中信息
        sessionStorage.setItem('class_names', JSON.stringify(class_names));
        sessionStorage.setItem('class_ids', JSON.stringify(class_ids));
    });

    // 获取班级信息
    var class_names = JSON.parse(sessionStorage.getItem('class_names'));
    var class_ids = JSON.parse(sessionStorage.getItem('class_ids'));

    // 调试
    // console.log("class_names="+class_names);
    // console.log("class_ids="+class_ids);

    // 如果没有创建班级就直接返回
    if(class_ids.length<1){
        return;
    }

    // 加载tab选项卡基础html标签
    $('#stu_content').html("<div class=\"layui-tab layui-tab-card\" lay-filter=\"student-filter\">\n" +
        "<ul class=\"layui-tab-title\"></ul>\n" +
        "<div class=\"layui-tab-content\"></div>\n" +
        "</div>");

    // layui从这里开始
    layui.use(['element', 'layer', 'table', 'form'], function(){
        var element = layui.element;
        var layer = layui.layer;
        var table = layui.table;
        var form = layui.form;

        // 动态加载时需要重新渲染element元素，否则可能会失效
        element.render();//2.1.6新增，原来为element.init()

        // 当前班级id
        var class_id = class_ids[0];
        // 为每个班级建立选项卡
        for (var i = 0; i < class_names.length; i++){
            element.tabAdd('student-filter', {
                title: class_names[i]
                ,content: '<table id="stu-table-'+(i+1)+'" lay-filter="stu-table-filter" ></table>' //为每个选项卡创建表格
                ,id: 'stu_tab_'+(i+1)// lay-id属性
            });
        }

        // 刚开始加载第一项
        element.tabChange('student-filter', 'stu_tab_1');

        var idx = 0;
        // 事件监听,tab选项卡切换
        element.on('tab(student-filter)', function(data){
            // console.log(this); //当前Tab标题所在的原始DOM元素
            // console.log(data.index); //得到当前Tab的所在下标
            // console.log(data.elem); //得到当前的Tab大容器

            idx = data.index;
            // 下面这里需要调试
            // $('#stu_tab_content_'+idx+1).html("<table id='stu-table' lay-filter='stu-table-filter' ></table>");
            class_id = class_ids[idx];
            var i = "#stu-table-"+(idx+1);
            tableIns.reload({
                elem: i,
                where: {classId: class_id}
            });
        });

        var urls = ["show_student",
            "delete_student",
            "batch_delete_student",
            "update_student",
            "add_student"];// 请求地址数组



        //layui表格渲染函数
        var tableIns = table.render({
            id: 'stuTable',
            elem: "#stu-table-1",
            url: urls[0],// 后端接口地址
            parseData: function(res){ //res 即为原始返回的数据
                return {
                    "code": res.code, //解析接口状态
                    "msg": res.message, //解析提示文本
                    "count": res.data['count'], //解析数据长度
                    "data": res.data['students'] //解析数据列表
                }
            },
            where: {classId: class_id},
            title: '学生信息',
            toolbar: '#headBarDemo',
            page: {
                limit: 20 //一页显示多少条
                , limits: [10, 20, 30]//每页条数的选择项
                , groups: 2 //只显示 2 个连续页码
            },
            cols: [[
                {type: 'checkbox', fixed: 'left'},
                {type: 'numbers', title: '序号'},
                {field: 'id', title: '学生ID'},
                {field: 'name', title: '姓名'},
                {field: 'gender', title: '性别'},
                {title: '操作', fixed: 'right', align: 'center', toolbar: '#lineBarDemo'}
            ]]
        });//table-end


        //layui监听每行工具菜单的操作
        table.on('tool(stu-table-filter)', function (obj) { //注：tool 是工具条事件名，userTableFilter 是 table 原始容器的属性 lay-filter="对应的值"
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
            }
        });//table-on--tool-end

        //监听头部操作菜单栏
        table.on('toolbar(stu-table-filter)', function (obj) {
            var checkStatus = table.checkStatus('stuTable');
            switch (obj.event) {
                //添加
                case 'add':
                    layer.open({
                        //layer提供了5种层类型。可传入的值有：0（信息框，默认）1（页面层）2（iframe层）3（加载层）4（tips层）
                        type: 1,
                        title: "添加学生",
                        //定义弹出层宽度，高度自适应
                        area: '500px',
                        content: $("#stu-add-form")//引用添加界面表单
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

        //信息添加的弹出层所需的表单设值函数
        function addForm() {
            //表单提交监听
            form.on('submit(stu-add*)', function (formdata) {
                //加载效果
                var loadIndex = layer.load(2, {time: 10 * 1000}); //2为样式，后面为设定最长等待10秒
                var jsonData = {
                    classId: class_id,
                    telephone: formdata.field.telephone
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