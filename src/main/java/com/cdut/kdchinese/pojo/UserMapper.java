package com.cdut.kdchinese.pojo;

import java.util.List;

public interface UserMapper {

    /**
     * 根据主键删除user
     * @param uid uid
     * @return 删除是否成功
     */
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


    /**
     * 根据主键有选择性的更新
     * @param record user对象
     * @return 更新结果
     */
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
     * 分页查询所有user对象
     * @param pageStart 页码
     * @param pageLimit 每页显示数量
     * @return user集合
     */
    List<User> selectAll(int pageStart, int pageLimit);

    /**
     * 查询用户总条数
     * @return 条数
     */
    int selectAllCount();

    /**
     * 根据主键uid批量删除
     * @param list uid集合
     * @return 删除条数
     */
    int batchDeleteByPK(List list);
}