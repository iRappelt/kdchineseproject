package com.cdut.kdchinese.pojo;

import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Copyright (C), 2020-2020, 快对语文
 * FileName: ExerciseService
 * Date:     2020/6/22 10:58
 * Description: 习题dao层
 * @Author  healer
 */
public interface ExerciseMapper {

    /**
     * 根据id和年级批量删除
     * @param ids id列表
     * @param grade 年级
     * @return 删除数
     */
    int deleteMoreByPrimaryKey(@Param("list") List<Integer> ids, @Param("grade") int grade);

    /**
     * 根据id和年级删除
     * @param wordId id
     * @param grade 年级
     * @return 删除数
     */
    int deleteByPrimaryKey(@Param("wordId") int wordId, @Param("grade") int grade);

    /**
     * 根据年级插入习题
     * @param grade 年级
     * @param exercise 习题数据
     * @return 返回成功数
     */
    int insert(@Param("grade") int grade, @Param("exercise") Exercise exercise);

    /**
     * 根据id查询
     * @param wordId id
     * @return 习题
     */
    Exercise selectByPrimaryKey(Integer wordId);

    /**
     * 根据年级和习题更新
     * @param grade 年级
     * @param exercise 修改后的习题
     * @return 返回修改成功数
     */
    int updateByPrimaryKey(@Param("grade") int grade, @Param("exercise") Exercise exercise);

    /**
     * 分页查询
     * @param pageStart 起始页码
     * @param pageLimit 每页数量
     * @param grade 年级
     * @return 习题列表
     */
    List<Exercise> selectByPage(@Param("pageStart") int pageStart, @Param("pageLimit") int pageLimit, @Param("grade") int grade);

    /**
     * 根据年级返回数据库总条数
     * @param grade 年级
     * @return 总条数
     */
    int selCount(@Param("grade") int grade);
}