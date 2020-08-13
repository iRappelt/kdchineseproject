package com.cdut.kdchinese.controller;

import com.cdut.kdchinese.pojo.User;
import com.cdut.kdchinese.service.UserService;
import com.cdut.kdchinese.util.Result;
import com.cdut.kdchinese.util.ResultCode;
import org.springframework.stereotype.Controller;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
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
    private UserService userService;

    @RequestMapping(value="/getuser", method = RequestMethod.GET)
    @ResponseBody
    public User getUserById(Integer id){
        return userService.selUserById(id);
    }

    @RequestMapping(value = "/chklogin", method = RequestMethod.POST)
    @ResponseBody
    public Result chkLogin(@RequestBody Map<String, String> map, HttpServletRequest request){
        //根据用户名判断是否有这个用户
        String username = map.get("username");
        User user = userService.selUserByUN(username);
        //如果有这个用户断密码是否正确
        if(user!=null){
            String dbPassword = userService.selPwdByUN(username);
            //前端传过来的密码进行md5加密
            String password = map.get("password");
            String md5Password = DigestUtils.md5DigestAsHex(password.getBytes());
            //密码正确
            if(md5Password.equals(dbPassword)){
                //设置用户session
                HttpSession session = request.getSession();
                session.setAttribute("user", user);
                //返回成功信息
                return Result.success();
            }else {//密码错误
                //返回失败信息

                return Result.failure(ResultCode.USER_LOGIN_ERROR);
            }
        } else{//没这个用户
            //返回失败信息
            return Result.failure(ResultCode.USER_LOGIN_ERROR);
        }
    }



    @RequestMapping(value = "/get_session_user", method = RequestMethod.GET)
    @ResponseBody
    public Result getSessionUser(HttpServletRequest req){
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        if(user!=null){
            // 重新获取用户信息
            User newUser = userService.selUserByUN(user.getUsername());
            // 更新session信息，以便一直保持用户最新状态
            session.setAttribute("user", newUser);
            return Result.success(newUser);
        }else{
            return Result.failure(ResultCode.USER_SESSION_ERROR);
        }
    }

    /**
     * 用户退出清除session
     * @param req HttpServletRequest对象
     * @return Result
     */
    @GetMapping("/remove_session_user")
    @ResponseBody
    public Result rmvSessionUser(HttpServletRequest req){
        HttpSession session = req.getSession();
        session.removeAttribute("user");
        return Result.success();
    }
}
