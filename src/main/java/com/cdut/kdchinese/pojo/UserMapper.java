package com.cdut.kdchinese.pojo;

import java.util.List;

public interface UserMapper {
    
    int deleteByPrimaryKey(Integer uid);

    
    int insert(User record);

    /**
     * 有选择性地插入
     * @param record user对象，其中的属性可以为null
     * @return 受影响的条数
     */
    int insertSelective(User record);

    /**
     * 根据用户主键-uid查询
     * @param uid 主键
     * @return user对象
     */
    User selectByPrimaryKey(Integer uid);

    
    int updateByPrimaryKeySelective(User record);

    
    int updateByPrimaryKey(User record);

    /**
     * 根据用户名查询
     * @param username 用户名
     * @return user对象
     */
    User selectUserByUN(String username);

    /**
     * 根据用户名查询
     * @param username 用户名
     * @return 用户密码
     */
    String selectPwdByUN(String username);

    /**
     * 根据电话号码查询
     * @param telephone 电话号码
     * @return user对象
     */
    User selectUserByTel(String telephone);

    /**
     * 查询所有user对象
     * @return
     */
    List<User> selectAll();
}