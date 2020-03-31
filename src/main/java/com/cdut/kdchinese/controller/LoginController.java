package com.cdut.kdchinese.controller;

import com.cdut.kdchinese.pojo.User;
import com.cdut.kdchinese.service.LoginService;
import org.springframework.stereotype.Controller;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: iRappelt
 * Date: 2020/03/20 17:13
 * Description:
 * Version: V1.0
 */
@Controller
public class LoginController {
    @Resource
    private LoginService loginService;

    @RequestMapping(value="/getuser", method = RequestMethod.GET)
    @ResponseBody
    public User getUserById(Integer id){
        return loginService.selUserById(id);
    }

    @RequestMapping(value = "/chklogin", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, String> chkLogin(@RequestBody Map<String, String> map, HttpServletRequest request){
        //根据用户名判断是否有这个用户
        String username = map.get("username");
        User user = loginService.selUserByUN(username);
        if(user!=null){//如果有这个用户
            //查询密码判断密码是否正确
            String dbPassword = loginService.selPwdByUN(username);
            //前端传过来的密码进行md5加密
            String password = map.get("password");
            String md5Password = DigestUtils.md5DigestAsHex(password.getBytes());
            if(md5Password.equals(dbPassword)){//密码正确
                //设置用户session
                HttpSession session = request.getSession();
                session.setAttribute("user", user);
                //返回成功信息
                map.put("status", "200");
                map.put("message", "登陆成功");

            }else {//密码错误
                //返回失败信息
                map.put("status", "10001");
                map.put("message", "登录失败，密码输入错误");
            }
        } else{//没这个用户
            //返回失败信息
            map.put("status", "10002");
            map.put("message", "登录失败，用户名不存在");
        }
        return map;
    }
}
