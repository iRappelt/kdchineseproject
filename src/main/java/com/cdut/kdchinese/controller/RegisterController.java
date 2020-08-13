package com.cdut.kdchinese.controller;

import com.cdut.kdchinese.pojo.User;
import com.cdut.kdchinese.service.UserService;
import com.cdut.kdchinese.util.Result;
import com.cdut.kdchinese.util.ResultCode;
import org.springframework.stereotype.Controller;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: iRappelt
 * Date: 2020/03/22 15:47
 * Description:
 * Version: V1.0
 */
@Controller
public class RegisterController {
    @Resource
    private UserService userService;

    @RequestMapping(value = "/check_register",method = RequestMethod.POST)
    @ResponseBody
    public Result regist(@RequestBody Map<String, Object> map){
        //判断用户是否已经存在
        User u1 = userService.selUserByUN((String)map.get("username"));
        if(u1!=null){
            return Result.failure(ResultCode.USER_HAS_EXIST);
        }
        //判断电话号码是否已经被注册
        User u2 = userService.selUserByTel((String)map.get("telephone"));
        if(u2!=null){
            // 已被注册
            return Result.failure(ResultCode.USER_PHONE_REGISTED);
        }
        //接下来进行注册操作
        //声明user容器
        User user = new User();
        //获得前端参数
        user.setTelephone((String)map.get("telephone"));
        String md5password = DigestUtils.md5DigestAsHex(((String)map.get("password")).getBytes());
        user.setPassword(md5password);
        user.setIdentity("teacher");
        user.setUsername((String)map.get("username"));
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String nowTime = df.format(new Date());
        user.setRegistdate(nowTime);
        //注册
        int result = userService.insUserSelective(user);
        if(result==1){
            // 成功
            return Result.success();
        }else{
            // 注册失败
            return Result.failure(ResultCode.USER_REGISTER_FAILED);
        }
    }
}
