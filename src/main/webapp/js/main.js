$(document).ready(function () {
    // 获取用户名，显示在导航栏上
    $.get("get_session_user", function (res) {
        if(res.data==null){
            $('#username').append("未登录");
            return;
        }
        // 界面呈现用户名
        $('#username').append(res.data.username);
        // 界面呈现头像
        if(res.data.headimg == '' || res.data.headimg == null){
            // 默认头像
            $('#avatar').attr('src', "http://imagic.work/images/users/avatar/ff1cacf71f084a5fbf301ec7dd332da0.jpg");
        }else{
            $('#avatar').attr('src', "http://imagic.work/images/users/avatar/"+res.data.headimg);
        }

        // console.log(res.data);
        if(res.data.identity == 'admin'){
            $('#identity_nav').html('<li class="layui-nav-item layui-nav-itemed">\n' +
                '                    <a class="" href="javascript:">数据管理</a>\n' +
                '                    <dl class="layui-nav-child">\n' +
                '                        <dd><a href="#user" id="get_user">用户管理</a></dd>\n' +
                '                        <dd><a href="#word" id="get_word">汉字管理</a></dd>\n' +
                '                        <dd><a href="#exercise01" id="get_exercise">题目管理</a></dd>\n' +
                '                        <dd><a href="#error_bank" id="get_error_bank">错题管理</a></dd>\n' +
                '                    </dl>\n' +
                '                </li>');
        }else if(res.data.identity == 'teacher'){
            $('#identity_nav').html('<li class="layui-nav-item layui-nav-itemed">\n' +
                '                    <a href="javascript:">教师管理</a>\n' +
                '                    <dl class="layui-nav-child">\n' +
                '                        <dd><a href="#class" id="class_manage">班级管理</a></dd>\n' +
                '                        <dd><a href="#stu" id="stu_manage">学生管理</a></dd>\n' +
                '                        <dd><a href="#exam" id="exercise_manage">试题管理</a></dd>\n' +
                '                        <dd><a href="#correct" id="correct_manage">批阅管理</a></dd>\n' +
                '                    </dl>\n' +
                '                </li>');

            var teacher_id = res.data.uid;
            // 将teacher的id存储到缓存中
            sessionStorage.setItem('teacher_id', teacher_id);

            // 获取教师id对应的班级信息
            $.get("get_class_info?teacherId="+teacher_id, function (res) {
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
        }else{
            // 不满足上面两个身份
        }


        // 点击标题回到主页
        $('#logo_title').on("click", function () {
            window.location.href = "main.html";
        });

        // 左侧导航栏点击切换事件
        $('#get_user').on("click", function () {
            $('#content').load("user.html");
        });
        $('#get_word').on("click", function () {
            $('#content').load("word.html");
        });
        $('#get_exercise').on("click", function () {
            $('#content').load("exercise.html");
        });
        $('#get_error_bank').on("click", function () {
            $('#content').load("error_bank.html");
        });
        $('#class_manage').on("click", function () {
            $('#content').load("class.html");
        });
        $('#stu_manage').on("click", function () {
            $('#content').load("student.html");
        });
        $('#exercise_manage').on("click", function () {
            $('#content').load("exam.html");
        });
        $('#correct_manage').on("click", function () {
            layer.msg('该功能还在设计中');
        });

        //退出
        $('#logout').on("click", function () {
            $.get("remove_session_user", function (res) {
                if(res.code == 0){
                    window.location.href = "login.html";
                } else{
                    alert("退出失败，请稍后重试！")
                }
            })
        })

    });

    //表单必填项前面加标识
    $('input[required]').parent().prev().append('<span style="color:red">*</span>');
    $('select[required]').parent().prev().append('<span style="color:red">*</span>');

    //layui部分
    layui.use(['element', 'layer', 'table', 'form', 'util', 'upload'], function(){
        var element = layui.element;
        var layer = layui.layer;
        var table = layui.table;
        var form = layui.form;
        var util = layui.util;
        var upload = layui.upload;

        //导航栏点击事件
        //nav代表导航栏的监听，filter过滤定位到固定的元素，下面为示例
        // element.on('nav(filter)', function(elem){
        //     console.log(elem); //得到当前点击的DOM对象
        // });
        
        // 修改个人资料
        $('#user_info').on("click", function () {
            layer.open({
                //layer提供了5种层类型。可传入的值有：0（信息框，默认）1（页面层）2（iframe层）3（加载层）4（tips层）
                type: 1,
                closeBtn: 0,
                shadeClose: true,
                title: "基本资料设置",
                //定义弹出层宽度，高度自适应
                area: '500px',
                content: $("#infoForm")//引用修改界面表单
            });
            //调用函数，表单赋值，和数据传递后端
            editForm(); 
        });

        // 上传图片
        // 普通图片上传
        var uploadInst = upload.render({
            elem: '#avatar_upload'
            ,url: 'avatar_upload'
            ,auto: false
            ,bindAction: '#avatar_upload_sure'
            ,field: "img"
            ,size: 2048
            ,choose: function(obj){
                //预读本地文件示例，不支持ie8
                obj.preview(function(index, file, result){
                    $('#avatar_preview').attr('src', result); //图片链接（base64）
                });
            }
            ,before: function(obj){ //obj参数包含的信息，跟 choose回调完全一致，可参见上文。
                // layer.load(); //上传loading
                // 添加动态条
                $('#user_info_form').before('<div class="layui-progress" lay-showpercent="false" lay-filter="avatar_upload_progress_filter" id="avatar_upload_progress">\n' +
                    '            <div class="layui-progress-bar layui-bg-red" lay-percent="20%"></div>\n' +
                    '        </div>');
            }
            ,progress: function(n, elem){
                var percent = n + '%'; //获取进度百分比
                element.progress('avatar_upload_progress_filter', percent); //可配合 layui 进度条元素使用

                //以下系 layui 2.5.6 新增
                // console.log("==========="+elem); //得到当前触发的元素 DOM 对象。可通过该元素定义的属性值匹配到对应的进度条。
            }
            ,done: function(res){
                //如果上传失败
                if(res.code > 0){
                    return layer.msg('上传失败，请稍后重试');
                }
                //上传成功
                layer.msg('上传成功', {
                    icon: 1,
                    time: 1000 //1秒关闭（如果不配置，默认是3秒）
                });
                // 移除动态条
                $('#avatar_upload_progress').remove();
            }
            ,error: function(){
                //演示失败状态，并实现重传
                // var demoText = $('#demoText');
                // demoText.html('<span style="color: #FF5722;">上传失败</span> <a class="layui-btn layui-btn-xs demo-reload">重试</a>');
                // demoText.find('.demo-reload').on('click', function(){
                //     uploadInst.upload();
                // });
            }
        });

        //固定块
        util.fixbar({
            bar1: true
            ,bar2: true
            ,bgcolor: '#9F9F9F'
            ,css: {right: 40, bottom: 60}
            ,click: function(type){
                if(type === 'bar1'){
                    layer.open({
                        type: 1,
                        title: false,
                        area: ['410px', '180px'],
                        closeBtn: 0,
                        shadeClose: true,
                        content: '<fieldset class="layui-elem-field" style="margin-top: 10px;">' +
                        '<legend style="color: #1E9FFF">在线客服功能正在建设中</legend>\n' +
                        '<div class="layui-field-box">如果您在使用中遇到了任何问题，或是你对本系统有任何意见或建议，请<br />' +
                        '邮箱联系：<b>2528520444@qq.com</b>' +
                        '<br />或QQ：<b>2528520444</b><br />我们将在第一时间联系你并提供支持' +
                        '<i class="layui-icon layui-icon-face-smile-fine" style="font-size: 20px; color: #ff5ec8;"></i></div>\n' +
                        '</fieldset>'
                    });
                } else if(type === 'bar2') {
                    layer.msg('有什么问题请联系管理员哦');
                }
            }
        });
        
        function editForm() {
            // 获取用户id，以得到头像资源
            $.get("get_session_user", function (res) {
                if(res.code != 0){
                    layer.msg("用户信息已过期，请重新登录！");
                    return;
                }
                // 获取头像uri
                var avatarUri = res.data.headimg;
                if(avatarUri == '' || avatarUri == null){
                    // 默认头像
                    $('#avatar_preview').attr('src', "http://101.200.59.162/images/users/avatar/ff1cacf71f084a5fbf301ec7dd332da0.jpg");
                }else{
                    $('#avatar_preview').attr('src', "http://101.200.59.162/images/users/avatar/"+res.data.headimg);
                }


                //设置表单呈现的默认值
                form.val("editFilter", { //editFilter 即 class="layui-form" 所在元素属性 lay-filter="" 对应的值
                    "username": res.data.username // "name": "value"
                    , "password": ''
                    , "telephone": res.data.telephone
                    , "sex": res.data.sex
                    , "identity": res.data.identity+"(不可更改)"
                    , "grade": res.data.grade
                });
                //表单提交监听
                form.on('submit(info_edit*)', function (formdata) {
                    //加载效果
                    var loadIndex = layer.load(2, {time: 10 * 1000}); //2为样式，后面为设定最长等待10秒
                    var jsonData = {
                        uid: res.data.uid
                        , username: formdata.field.username
                        , password: formdata.field.password
                        , telephone: formdata.field.telephone
                        , sex: formdata.field.sex
                        , grade: formdata.field.grade
                    };
                    $.ajax({
                        url: "update_user",   // 请求路径
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

            });
        }

    });


});
