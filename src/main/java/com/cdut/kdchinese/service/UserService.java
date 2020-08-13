package com.cdut.kdchinese.service;

import com.cdut.kdchinese.pojo.User;
import com.cdut.kdchinese.pojo.UserMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: iRappelt
 * Date: 2020/04/19 15:09
 * Description:
 * Version: V1.0
 */
@Service
public class UserService {
    @Resource
    private UserMapper userMapper;

    /**
     * 查询user总数目
     * @return 总条数
     */
    public int selAllCount(){
        return userMapper.selectAllCount();
    }

    /**
     * 分页查询user
     * @param pageStart 页码
     * @param pageLimit 每页数量
     * @return user集合
     */
    public List<User> selAll(int pageStart, int pageLimit){
        return userMapper.selectAll(pageStart, pageLimit);
    }

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

    /**
     * 根据主键删除user
     * @param uid uid
     * @return 结果
     */
    public int delUserByPK(int uid){
        return userMapper.deleteByPrimaryKey(uid);
    }

    /**
     * 根据主键动态更新
     * @param user user对象
     * @return 更新结果
     */
    public int updUserByPKSelective(User user){
        return userMapper.updateByPrimaryKeySelective(user);
    }

    /**
     * 根据主键批量删除
     * @param list uid集合
     * @return 删除条数
     */
    public int delUsersByPK(List list){
        return userMapper.batchDeleteByPK(list);
    }
}
