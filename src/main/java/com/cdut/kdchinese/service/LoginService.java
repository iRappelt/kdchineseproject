package com.cdut.kdchinese.service;

import com.cdut.kdchinese.pojo.User;
import com.cdut.kdchinese.pojo.UserMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created with IntelliJ IDEA.
 * User: iRappelt
 * Date: 2020/03/20 17:02
 * Description:
 * Version: V1.0
 */
@Service
public class LoginService {
    @Resource
    private UserMapper userMapper;

    /**
     * 根据主键（id）查询得到user对象
     * @param id 主键id
     * @return user对象
     **/
    public User selUserById(Integer id){
        return userMapper.selectByPrimaryKey(id);
    }

    /**
     * 根据用户名得到用户对象
     * @param username 用户名
     * @return 用户对象
     */
    public User selUserByUN(String username){
        return userMapper.selectUserByUN(username);
    }

    /**
     * 根据用户名得到密码
     * @param username 用户名
     * @return 用户密码
     */
    public String selPwdByUN(String username){
        return  userMapper.selectPwdByUN(username);
    }


}
