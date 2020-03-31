package com.cdut.kdchinese.controller;

import com.cdut.kdchinese.pojo.User;
import com.cdut.kdchinese.service.LoginService;
import com.cdut.kdchinese.service.RegisterService;
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
    private RegisterService registerService;
    @Resource
    private LoginService loginService;

    @RequestMapping(value = "/chkregister",method = RequestMethod.POST)
    @ResponseBody
    public Map<String, String> regist(@RequestBody Map<String, String> map){
        //判断用户是否已经存在
        User u1 = loginService.selUserByUN(map.get("username"));
        if(u1!=null){
            map.put("status","20003");
            map.put("message","用户名已存在");
            return map;
        }
        //判断电话号码是否已经被注册
        User u2 = registerService.selUserByTel(map.get("telephone"));
        if(u2!=null){
            map.put("status","20004");
            map.put("message","电话号码已被注册");
            return map;
        }
        //接下来进行注册操作
        //声明user容器
        User user = new User();
        //获得前端参数
        user.setTelephone(map.get("telephone"));
        String md5password = DigestUtils.md5DigestAsHex(map.get("password").getBytes());
        user.setPassword(md5password);
        user.setIdentity("teacher");
        user.setUsername(map.get("username"));
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String nowTime = df.format(new Date());
        user.setRegistdate(nowTime);
        //注册
        int result = registerService.insUserSelective(user);
        if(result==1){
            map.put("status", "200");
            map.put("message", "注册成功");
        }else{
            map.put("status", "20001");
            map.put("message", "注册失败，sql返回不是1");
        }

        return map;
    }
}
