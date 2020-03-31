package com.cdut.kdchinese.service;

import com.cdut.kdchinese.pojo.User;
import com.cdut.kdchinese.pojo.UserMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created with IntelliJ IDEA.
 * User: iRappelt
 * Date: 2020/03/22 15:49
 * Description:
 * Version: V1.0
 */
@Service
public class RegisterService {
    @Resource
    private UserMapper userMapper;

    /**
     * 选择性的将用户信息添加到用户表中
     * @param user user对象，其中的属性值可以为null
     * @return 添加成功返回（1）不成功返回(0)
     */
    public int insUserSelective(User user){
        return userMapper.insertSelective(user);
    }

    /**
     * 根据电话号码判断是否有这个user对象
     * @param telephone 电话
     * @return user对象
     */
    public User selUserByTel(String telephone){
        return userMapper.selectUserByTel(telephone);
    }
}
