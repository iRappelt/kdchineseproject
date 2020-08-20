$(document).ready(function () {



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
    if (class_ids.length < 1) {
        return;
    }

    // 加载tab选项卡基础html标签
    $('#exam_content').html("<div class=\"layui-tab layui-tab-card\" lay-filter=\"exam-filter\">\n" +
        "<ul class=\"layui-tab-title\"></ul>\n" +
        "<div class=\"layui-tab-content\"></div>\n" +
        "</div>");

    // layui从这里开始
    layui.use(['element', 'layer', 'table', 'form'], function () {
        var element = layui.element;
        var layer = layui.layer;
        var table = layui.table;
        var form = layui.form;

        // 动态加载时需要重新渲染element元素，否则可能会失效
        element.render();//2.1.6新增，原来为element.init()

        // 当前班级id
        var class_id = class_ids[0];
        // 为每个试题建立选项卡
        for (var i = 0; i < class_names.length; i++) {
            element.tabAdd('exam-filter', {
                title: class_names[i],
                content: '<table id="exam-table-' + (i + 1) + '" lay-filter="exam-table-filter" class="layui-table" lay-size="sm" ></table>', //支持传入html
                id: 'exam_tab_' + (i + 1)// lay-id属性
            });
        }

        // 刚开始加载第一项
        element.tabChange('exam-filter', 'exam_tab_1');
        // $('#exam_tab_content_1').html('<table id="exam-table" lay-filter="exam-table-filter" ></table>');

        var idx = 0;
        // 事件监听,tab选项卡切换
        element.on('tab(exam-filter)', function (data) {
            // console.log(this); //当前Tab标题所在的原始DOM元素
            // console.log(data.index); //得到当前Tab的所在下标
            // console.log(data.elem); //得到当前的Tab大容器

            idx = data.index;
            // 下面这里需要调试
            // $('#exam_tab_content_'+idx+1).html("<table id='exam-table' lay-filter='exam-table-filter' ></table>");
            class_id = class_ids[idx];
            var i = "#exam-table-" + (idx + 1);
            tableIns.reload({
                elem: i,
                where: {classId: class_id}
            });
        });

        var urls = ["show_exam",
            "delete_exam",
            "batch_delete_exam",
            "show_question",
            "add_exam",
            "publish_exam"];// 请求地址数组


        //layui表格渲染函数
        var tableIns = table.render({
            id: 'examTable',
            elem: "#exam-table-1",
            url: urls[0],// 后端接口地址
            parseData: function (res) { //res 即为原始返回的数据
                return {
                    "code": res.code, //解析接口状态
                    "msg": res.message, //解析提示文本
                    "count": res.data[1], //解析数据长度
                    "data": res.data[0] //解析数据列表
                }
            },
            where: {classId: class_id},
            title: '试题信息',
            toolbar: '#headBarDemo',
            page: {
                limit: 10 //一页显示多少条
                , limits: [10, 20, 30]//每页条数的选择项
                , groups: 2 //只显示 2 个连续页码
            },
            cols: [[
                {type: 'checkbox', fixed: 'left'},
                {type: 'numbers', title: '序号'},
                {field: 'id', title: '试题ID', hide: true},
                {field: 'name', title: '试题名'},
                {field: 'des', title: '试题说明'},
                {field: 'date', title: '创建日期'},
                {field: 'publish', title: '发布状态'},
                {title: '操作', fixed: 'right', align: 'center', toolbar: '#lineBarDemo'}
            ]]
        });//table-end


        //layui监听每行工具菜单的操作
        table.on('tool(exam-table-filter)', function (obj) { //注：tool 是工具条事件名，userTableFilter 是 table 原始容器的属性 lay-filter="对应的值"
            var data = obj.data; //获得当前行数据
            var layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
            var tr = obj.tr; //获得当前行 tr 的 DOM 对象（如果有的话）

            if (layEvent === 'del') { //删除
                layer.confirm('你真的要删除这套试题吗，删除后学生的作答信息也将删除?', {icon: 0, title: '提示'}, function (index) {
                    // 先关闭这个确认框
                    layer.close(index);
                    //加载效果
                    var loadIndex = layer.load(2, {time: 10 * 1000}); //2为样式，后面为设定最长等待10秒
                    //向服务端发送删除指令
                    //Ajax
                    $.get(urls[1] + "?id=" + data.id, function (res) {
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
                    title: "查看试题",
                    //定义弹出层宽度，高度自适应
                    area: ['800px', '600px'],
                    content: $('#exam-edit-form'), // 引用编辑界面表单$("#exam-edit-form")
                    cancel: function () {
                        $('#exam_edit_clear').html('<div class="layui-form-item" >\n' +
                            '                <label class="layui-form-label">题目</label>\n' +
                            '                <div class="layui-input-block">\n' +
                            '                    <div class="layui-input-block">\n' +
                            '                        <input type="text" autocomplete="off" class="layui-input" id="exam_title_area">\n' +
                            '                    </div>\n' +
                            '                </div>\n' +
                            '            </div>\n' +
                            '\n' +
                            '            <div class="layui-form-item" >\n' +
                            '                <label class="layui-form-label">题目描述</label>\n' +
                            '                <div class="layui-input-block">\n' +
                            '                    <div class="layui-input-block">\n' +
                            '                        <input type="text" autocomplete="off" class="layui-input" id="exam_des_area">\n' +
                            '                    </div>\n' +
                            '                </div>\n' +
                            '            </div>\n' +
                            '            <hr class="layui-bg-red"/>\n' +
                            '\n' +
                            '            <div class="layui-form-item" id="choose_edit_area">\n' +
                            '                <div class="layui-input-block">\n' +
                            '                    <hr class="layui-bg-red"/>\n' +
                            '                </div>\n' +
                            '            </div>\n' +
                            '\n' +
                            '            <div class="layui-form-item" id="fill_edit_area">\n' +
                            '                <div class="layui-input-block">\n' +
                            '                    <hr class="layui-bg-red"/>\n' +
                            '                </div>\n' +
                            '            </div>')
                    }
                });
                //调用渲染函数，查看试题数据
                editForm(obj, data);
            } else if (layEvent === 'publish') { //发布
                // 判断该试题是否已经发布了
                if (data.publish == 1) {
                    layer.msg('该试题已经发布了，请勿重复发布', {
                        icon: 2,
                        time: 1000 //1秒关闭（如果不配置，默认是3秒）
                    });
                } else {
                    layer.confirm('你真的要发布吗？发布后，该班级的学生将收到该试题！', {icon: 0, title: '提示'}, function (index) {
                        layer.close(index);
                        //加载效果
                        var loadIndex = layer.load(2, {time: 10 * 1000}); //2为样式，后面为设定最长等待10秒
                        //向服务端发送发布指令，要传递试题id参数
                        //Ajax
                        $.get(urls[5] + "?id=" + data.id, function (res) {
                            if (res.code == 0) {
                                //发布成功
                                layer.close(loadIndex);
                                layer.msg('发布成功', {
                                    icon: 1,
                                    time: 1000 //1秒关闭（如果不配置，默认是3秒）
                                });
                            } else {
                                //发布失败
                                layer.close(loadIndex);
                                layer.msg('发布失败', {
                                    icon: 2,
                                    time: 1000 //1秒关闭（如果不配置，默认是3秒）
                                });
                            }
                        });
                    });
                }

            }
        });//table-on--tool-end

        //监听头部操作菜单栏
        table.on('toolbar(exam-table-filter)', function (obj) {
            var checkStatus = table.checkStatus('examTable');
            switch (obj.event) {
                //添加
                case 'add':
                    layer.open({
                        //layer提供了5种层类型。可传入的值有：0（信息框，默认）1（页面层）2（iframe层）3（加载层）4（tips层）
                        type: 1,
                        title: "创建试题",
                        //定义弹出层宽度，高度自适应
                        area: ['800px', '600px'],
                        content: $("#exam-add-form"),//引用添加界面表单
                        cancel: function () {
                            $('#exam_add_clear').html(' <div class="layui-form-item" >\n' +
                                '                <label class="layui-form-label">题目</label>\n' +
                                '                <div class="layui-input-block">\n' +
                                '                    <div class="layui-input-block">\n' +
                                '                     <input type="text" autocomplete="off" class="layui-input" id="exam_title_area">\n' +
                                '                       </div>\n' +
                                '                </div>\n' +
                                '            </div>\n' +
                                '\n' +
                                '            <div class="layui-form-item" >\n' +
                                '                <label class="layui-form-label">题目描述</label>\n' +
                                '                <div class="layui-input-block">\n' +
                                '                    <div class="layui-input-block">\n' +
                                '                        <input type="text" autocomplete="off" class="layui-input" id="exam_des_area">\n' +
                                '                    </div>\n' +
                                '                </div>\n' +
                                '            </div>\n' +
                                '            <hr class="layui-bg-red"/>\n' +
                                '\n' +
                                '            <div class="layui-form-item" id="choose_add_area">\n' +
                                '                <div class="layui-input-block">\n' +
                                '                    <button type="button" class="layui-btn layui-btn-normal" id="add_choose">添加选择题</button>\n' +
                                '                    <button type="button" class="layui-btn layui-btn-normal" id="delete_choose">删除上个选择题</button>\n' +
                                '                </div>\n' +
                                '            </div>\n' +
                                '\n' +
                                '            <div class="layui-form-item" id="fill_add_area">\n' +
                                '                <div class="layui-input-block">\n' +
                                '                    <hr class="layui-bg-red"/>\n' +
                                '                    <button type="button" class="layui-btn layui-btn-normal" id="add_fill">添加填空题</button>\n' +
                                '                    <button type="button" class="layui-btn layui-btn-normal" id="delete_fill">删除上个填空题</button>\n' +
                                '                </div>\n' +
                                '            </div>\n' +
                                '\n' +
                                '            <div class="layui-form-item" id="exam_add_area">\n' +
                                '                <div class="layui-input-block">\n' +
                                '                    <hr class="layui-bg-red"/>\n' +
                                '                    <button class="layui-btn" lay-submit lay-filter="exam-add*">创建完成</button>\n' +
                                '                    <button type="reset" class="layui-btn layui-btn-primary">重置</button>\n' +
                                '                </div>\n' +
                                '            </div>')
                        }
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
                    layer.confirm('你真的要删除这些试题吗，删除后学生的作答信息也将全部删除?', {icon: 0, title: '提示'}, function (index) {
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

        // 试题查看函数
        function editForm(obj, data) {

            //加载效果
            var loadIndex = layer.load(2, {time: 10 * 1000}); //2为样式，后面为设定最长等待10秒
            var jsonData = {
                id: data.id
            };
            // 根据试卷id获得该试卷中的数据
            $.ajax({
                url: urls[3],
                type: 'post',
                contentType: "application/json;charset=utf-8",
                dataType: 'json',
                data: JSON.stringify(jsonData),
                success: function (res) {
                    //关闭加载弹出层
                    layer.close(loadIndex);
                    if (res.code == 0) {
                        //获取成功
                        //试卷信息渲染
                        $('#exam_title_area').val(res.data.examTitle); // 试卷标题
                        $('#exam_des_area').val(res.data.examDes);// 试卷描述
                        //遍历试题数据渲染到页面中
                        var choose_count = 0;
                        var fill_count = 0;
                        res.data.question.forEach(function (value, index) {
                            choose_count += 1;
                            if (value.questionType == 1) {
                                // 选择题
                                $('#choose_edit_area').before('<fieldset class="layui-elem-field layui-field-title">\n' +
                                    '                <legend>选择题' + choose_count + '</legend>\n' +
                                    '                <div class="layui-field-box">\n' +
                                    '                    <div class="layui-form-item" style="margin-top: 15px;">\n' +
                                    '                        <label class="layui-form-label">题目</label>\n' +
                                    '                        <div class="layui-input-block">\n' +
                                    '                            <input type="text" autocomplete="off" class="layui-input" value="' + value.questionContent + '">\n' +
                                    '                        </div>\n' +
                                    '                    </div>\n' +
                                    '                    <div class="layui-form-item">\n' +
                                    '                        <label class="layui-form-label">选项一</label>\n' +
                                    '                        <div class="layui-input-block" style="width: 50%;">\n' +
                                    '                            <input type="text" autocomplete="off" class="layui-input" value="' + value.qA + '">\n' +
                                    '                        </div>\n' +
                                    '                        <label class="layui-form-label">选项二</label>\n' +
                                    '                        <div class="layui-input-block" style="width: 50%;;">\n' +
                                    '                            <input type="text"  autocomplete="off" class="layui-input" value="' + value.qB + '">\n' +
                                    '                        </div>\n' +
                                    '                        <label class="layui-form-label">选项三</label>\n' +
                                    '                        <div class="layui-input-block" style="width: 50%;;">\n' +
                                    '                            <input type="text"  autocomplete="off" class="layui-input" value="' + value.qC + '">\n' +
                                    '                        </div>\n' +
                                    '                        <label class="layui-form-label">选项四</label>\n' +
                                    '                        <div class="layui-input-block" style="width: 50%;;">\n' +
                                    '                            <input type="text"  autocomplete="off" class="layui-input" value="' + value.qD + '">\n' +
                                    '                        </div>\n' +
                                    '                    </div>\n' +
                                    '                    <div class="layui-form-item" style="margin-top: 15px;">\n' +
                                    '                        <label class="layui-form-label">正确答案</label>\n' +
                                    '                        <div class="layui-input-block">\n' +
                                    '                            <input type="text"  autocomplete="off" class="layui-input" value="' + value.questionAnswer + '">\n' +
                                    '                        </div>\n' +
                                    '                    </div>\n' +
                                    '                    <div class="layui-form-item" style="margin-top: 15px;">\n' +
                                    '                        <label class="layui-form-label">分值</label>\n' +
                                    '                        <div class="layui-input-block">\n' +
                                    '                            <input type="text"  autocomplete="off" class="layui-input" value="' + value.questionScore + '">\n' +
                                    '                        </div>\n' +
                                    '                    </div>\n' +
                                    '                </div>\n' +
                                    '            </fieldset>');
                            } else if (value.questionType == 2) {
                                fill_count += 1;
                                // 填空题
                                $('#fill_edit_area').before('<fieldset class="layui-elem-field layui-field-title">\n' +
                                    '                <legend>填空题' + fill_count + '</legend>\n' +
                                    '                <div class="layui-field-box">\n' +
                                    '                    <div class="layui-form-item" style="margin-top: 15px;">\n' +
                                    '                        <label class="layui-form-label">题目</label>\n' +
                                    '                        <div class="layui-input-block">\n' +
                                    '                            <input type="text" autocomplete="off" class="layui-input" value="' + value.questionContent + '">\n' +
                                    '                        </div>\n' +
                                    '                    </div>\n' +
                                    '                    <div class="layui-form-item" style="margin-top: 15px;">\n' +
                                    '                        <label class="layui-form-label">正确答案</label>\n' +
                                    '                        <div class="layui-input-block">\n' +
                                    '                            <input type="text"  autocomplete="off" class="layui-input" value="' + value.questionAnswer + '">\n' +
                                    '                        </div>\n' +
                                    '                    </div>\n' +
                                    '                    <div class="layui-form-item" style="margin-top: 15px;">\n' +
                                    '                        <label class="layui-form-label">分值</label>\n' +
                                    '                        <div class="layui-input-block">\n' +
                                    '                            <input type="text"  autocomplete="off" class="layui-input" value="' + value.questionScore + '">\n' +
                                    '                        </div>\n' +
                                    '                    </div>\n' +
                                    '                </div>\n' +
                                    '            </fieldset>');
                            }
                        });

                    } else {
                        //删除失败
                        layer.msg('获取试题数据失败', {
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

        }// editForm-function-end

        // 试题添加函数
        function addForm() {
            var choose_count = 0;
            var fill_count = 0;
            // 添加选择题
            $('#add_choose').on("click", function () {
                choose_count += 1;
                $('#choose_add_area').before('<fieldset class="layui-elem-field layui-field-title" id="choose' + choose_count + '">\n' +
                    '                <legend>选择题' + choose_count + '</legend>\n' +
                    '                <div class="layui-field-box">\n' +
                    '                    <div class="layui-form-item" style="margin-top: 15px;">\n' +
                    '                        <label class="layui-form-label">题目</label>\n' +
                    '                        <div class="layui-input-block">\n' +
                    '                            <input type="text" name="choose_question' + choose_count + '" required lay-verify="required" autocomplete="off" class="layui-input">\n' +
                    '                        </div>\n' +
                    '                    </div>\n' +
                    '                    <div class="layui-form-item">\n' +
                    '                        <label class="layui-form-label">选项一</label>\n' +
                    '                        <div class="layui-input-block" style="width: 50%;">\n' +
                    '                            <input type="text" name="option_' + choose_count + '_1" required lay-verify="required" autocomplete="off" class="layui-input">\n' +
                    '                        </div>\n' +
                    '                        <label class="layui-form-label">选项二</label>\n' +
                    '                        <div class="layui-input-block" style="width: 50%;;">\n' +
                    '                            <input type="text" name="option_' + choose_count + '_2" required lay-verify="required" autocomplete="off" class="layui-input">\n' +
                    '                        </div>\n' +
                    '                        <label class="layui-form-label">选项三</label>\n' +
                    '                        <div class="layui-input-block" style="width: 50%;;">\n' +
                    '                            <input type="text" name="option_' + choose_count + '_3" required lay-verify="required" autocomplete="off" class="layui-input">\n' +
                    '                        </div>\n' +
                    '                        <label class="layui-form-label">选项四</label>\n' +
                    '                        <div class="layui-input-block" style="width: 50%;;">\n' +
                    '                            <input type="text" name="option_' + choose_count + '_4" required lay-verify="required" autocomplete="off" class="layui-input">\n' +
                    '                        </div>\n' +
                    '                    </div>\n' +
                    '                    <div class="layui-form-item" style="margin-top: 15px;">\n' +
                    '                        <label class="layui-form-label">正确答案</label>\n' +
                    '                        <div class="layui-input-block">\n' +
                    '                            <input type="text" name="choose_correct' + choose_count + '" required lay-verify="required" autocomplete="off" class="layui-input">\n' +
                    '                        </div>\n' +
                    '                    </div>\n' +
                    '                    <div class="layui-form-item" style="margin-top: 15px;">\n' +
                    '                        <label class="layui-form-label">分值</label>\n' +
                    '                        <div class="layui-input-block">\n' +
                    '                            <input type="text" name="choose_score' + choose_count + '" required lay-verify="required" autocomplete="off" class="layui-input">\n' +
                    '                        </div>\n' +
                    '                    </div>\n' +
                    '                </div>\n' +
                    '            </fieldset>');
            });

            // 添加填空题
            $('#add_fill').on("click", function () {
                fill_count += 1;
                $('#fill_add_area').before('<fieldset class="layui-elem-field layui-field-title" id="fill' + fill_count + '">\n' +
                    '                <legend>填空题' + fill_count + '</legend>\n' +
                    '                <div class="layui-field-box">\n' +
                    '                    <div class="layui-form-item" style="margin-top: 15px;">\n' +
                    '                        <label class="layui-form-label">题目</label>\n' +
                    '                        <div class="layui-input-block">\n' +
                    '                            <input type="text" name="fill_question' + fill_count + '" required lay-verify="required" autocomplete="off" class="layui-input">\n' +
                    '                        </div>\n' +
                    '                    </div>\n' +
                    '                    <div class="layui-form-item" style="margin-top: 15px;">\n' +
                    '                        <label class="layui-form-label">正确答案</label>\n' +
                    '                        <div class="layui-input-block">\n' +
                    '                            <input type="text" name="fill_correct' + fill_count + '" required lay-verify="required" autocomplete="off" class="layui-input">\n' +
                    '                        </div>\n' +
                    '                    </div>\n' +
                    '                    <div class="layui-form-item" style="margin-top: 15px;">\n' +
                    '                        <label class="layui-form-label">分值</label>\n' +
                    '                        <div class="layui-input-block">\n' +
                    '                            <input type="text" name="fill_score' + fill_count + '" required lay-verify="required" autocomplete="off" class="layui-input">\n' +
                    '                        </div>\n' +
                    '                    </div>\n' +
                    '                </div>\n' +
                    '            </fieldset>');
            });

            // 删除上个选择题
            $('#delete_choose').on("click", function () {
                var tag = "#choose" + choose_count;
                $(tag).remove();
                choose_count -= 1;
            });

            // 删除上个填空题
            $('#delete_fill').on("click", function () {
                var tag = "#fill" + choose_count;
                $(tag).remove();
                fill_count -= 1;
            });


            //表单提交监听
            form.on('submit(exam-add*)', function (formdata) {
                // var data = [];
                // for (var i = 1; i<=count; i++) {
                //     var temp = [];
                //     temp.push($('input[name="question"'+i+']').val());
                //     temp.push($('input[name="option"'+i+'_1]').val());
                //     temp.push($('input[name="option"'+i+'_2]').val());
                //     temp.push($('input[name="option"'+i+'_3]').val());
                //     temp.push($('input[name="option"'+i+'_4]').val());
                //     temp.push($('input[name="correct"'+i+']').val());
                //     data.push(temp);
                //     console.log("===>"+temp);
                // }
                // console.log("--->"+data);
                // return false;
                console.log(formdata.field);

                //加载效果
                var loadIndex = layer.load(2, {time: 10 * 1000}); //2为样式，后面为设定最长等待10秒
                var jsonData = {
                    classId: class_id,  // 班级id
                    data: formdata.field,  // 表单中的信息
                    chooseCount: choose_count, // 选择题数量
                    fillCount: fill_count  // 填空题数量
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
                            layer.msg('创建成功', {
                                icon: 1,
                                time: 1000 //1秒关闭（如果不配置，默认是3秒）
                            });
                            //重载表格
                            tableIns.reload();
                        } else {
                            //添加失败
                            layer.close(loadIndex);
                            layer.msg("创建失败", {
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