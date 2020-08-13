package com.cdut.kdchinese.controller;

import com.cdut.kdchinese.pojo.User;
import com.cdut.kdchinese.service.UserService;
import com.cdut.kdchinese.util.Result;
import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;
import org.springframework.stereotype.Controller;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * Date: 2020/03/26 17:46
 * Description:
 * Version: V1.0
 * @author iRappelt
 */
@Controller
public class UserController {
    @Resource
    private UserService userService;

    /**
     * 分页展示用户
     * @param page 页码
     * @param limit 每页数量
     * @return map数据
     */
    @RequestMapping(value = "/show_users", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> showUser(String page, String limit) {
        //声明要返回的存储map对象
        Map<String, Object> map = new HashMap<>();
        //每页展示数量
        int pageLimit = Integer.valueOf(limit);
        //页码
        int pageStart = pageLimit*(Integer.valueOf(page)-1);
        //总数
        int count = userService.selAllCount();
        List<User> users = userService.selAll(pageStart, pageLimit);
        //layui要求返回的数据格式
        map.put("code", 0);
        map.put("msg", "");
        map.put("count", count);
        map.put("data", users);

        return map;
    }

    /**
     * 根据user的主键uid删除user
     * @param uid uid
     * @return map
     */
    @RequestMapping(value = "/delete_user", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> deleteUser(String uid){
        //声明要返回的存储map对象
        Map<String, Object> map = new HashMap<>();
        //判断是否为空
        if(uid==null||uid.equals("")){
            map.put("code", 1);
            map.put("msg", "uid为空");
            return map;
        }
        //转换得到uid
        Integer id = Integer.valueOf(uid);
        int result = userService.delUserByPK(id);
        if(result!=1){
            map.put("code", 2);
            map.put("msg", "删除user失败");
            return map;
        }
        map.put("code", 0);
        map.put("msg", "删除user成功");
        return map;
    }

    /**
     * 动态更新用户
     * @param map 包含要更新的数据
     * @return map信息
     */
    @RequestMapping(value = "/update_user", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> updateUser(@RequestBody Map<String, Object> map){
        //声明要返回的存储map对象
        Map<String, Object> newMap = new HashMap<>();
        User user = new User();
        user.setUid((int)map.get("uid"));
        user.setUsername((String)map.get("username"));
        String md5Password = DigestUtils.md5DigestAsHex(((String)map.get("password")).getBytes());
        user.setPassword(md5Password);
        user.setTelephone((String)map.get("telephone"));
        user.setSex((String)map.get("sex"));
        user.setGrade((String)map.get("grade"));

        int result = userService.updUserByPKSelective(user);
        if(result!=1){
            newMap.put("code", 1);
            newMap.put("msg", "更新失败");
            return newMap;
        }
        newMap.put("md5Password", md5Password);
        newMap.put("code", 0);
        newMap.put("msg", "更新成功");
        return newMap;
    }


    @RequestMapping(value = "/batch_delete_users", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> batchDeleteUsers(@RequestBody Map<String, Object> map){
        //声明要返回的存储map对象
        Map<String, Object> newMap = new HashMap<>();
        //得到要删除的user对象的uid集合
        JSONArray jsonArray = JSONArray.fromObject(map.get("uidList"));
        List list = JSONArray.toList(jsonArray, "", new JsonConfig());
        int length = list.size();
        int result = userService.delUsersByPK(list);
        if(result!=length){
            newMap.put("code", 1);
            newMap.put("msg", "删除失败，影响条数不相等");
            return newMap;
        }
        newMap.put("code", 0);
        newMap.put("msg", "删除成功");
        return newMap;
    }

//    @RequestMapping(value = "/add_exam", method = RequestMethod.POST)
//    @ResponseBody
//    public Map<String, Object> addExam(@RequestBody Map<String, Object> map){
//        System.out.println(map.get("data"));
//        System.out.println(map.get("chooseCount"));
//        System.out.println(map.get("fillCount"));
//        return map;
//    }


    @RequestMapping(value = "/add_user", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> addUser(@RequestBody Map<String, Object> map){
        //声明要返回的存储map对象
        Map<String, Object> newMap = new HashMap<>();
        //判断用户是否已经存在
        String username = (String)map.get("username");
        User u1 = userService.selUserByUN(username);
        if(u1!=null){
            newMap.put("code",1);
            newMap.put("msg","用户名已存在");
            return newMap;
        }
        //判断电话号码是否已经被注册
        String telephone = (String)map.get("telephone");
        User u2 = userService.selUserByTel(telephone);
        if(u2!=null){
            newMap.put("code",2);
            newMap.put("msg","电话号码已被注册");
            return newMap;
        }
        //接下来进行添加用户操作
        //声明user容器
        User user = new User();
        //获得前端参数
        if(telephone!=null && !telephone.equals(""))
        {
            user.setTelephone((String)map.get("telephone"));
        }
        String password = (String)map.get("password");
        if(password!=null && !password.equals(""))
        {
            String md5password = DigestUtils.md5DigestAsHex(password.getBytes());
            user.setPassword(md5password);
        }
        String identity = (String)map.get("identity");
        if(identity!=null && !identity.equals(""))
        {
            user.setIdentity(identity);
        }
        String sex = (String)map.get("sex");
        if(sex!=null && !sex.equals(""))
        {
            user.setSex(sex);
        }
        String grade = (String)map.get("grade");
        if(grade!=null && !grade.equals(""))
        {
            user.setGrade(grade);
        }
        user.setUsername(username);
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String nowTime = df.format(new Date());
        user.setRegistdate(nowTime);
        //注册
        int result = userService.insUserSelective(user);
        if(result==1){
            newMap.put("code", 0);
            newMap.put("msg", "添加成功");
        }else{
            newMap.put("code", 3);
            newMap.put("msg", "添加失败,未知错误");
        }
        return newMap;
    }
}
