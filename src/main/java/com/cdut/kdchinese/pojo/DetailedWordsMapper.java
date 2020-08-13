package com.cdut.kdchinese.pojo;

import java.util.List;

/**
 * Copyright (C), 2020-2020, 快对语文
 * FileName: ExerciseService
 * Date:     2020/6/22 10:58
 * Description: 习题dao层
 * @Author  lfc
 */
public interface DetailedWordsMapper {
    /**
     * 根据ID更新记录
     * @param detailedWords
     * @return 更新条数
     */
    int updateByPrimaryKey(DetailedWords detailedWords);

    /**
     * 根据ID有选择的更新记录的某些字段
     * @param detailedWords
     * @return 更新条数
     */
    int updateByPrimaryKeySelective(DetailedWords detailedWords);

    /**
     * 根据ID删除记录
     * @param id
     * @return 删除条数
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * 批量删除记录
     * @param idList
     * @return 删除条数
     */
    int batchDeleteByPrimaryKey(List<Integer> idList);

    /**
     * 添加记录
     * @param detailedWords
     * @return 受影响条数
     */
    int insert(DetailedWords detailedWords);

    /**
     * 有选择性的添加记录的某些字段
     * @param detailedWords
     * @return 添加的条数
     */
    int insertSelective(DetailedWords detailedWords);

    /**
     * 根据ID查询记录
     * @param id
     * @return 记录
     */
    DetailedWords selectByPrimaryKey(Integer id);

    /**
     * 根据字名查询记录
     * @param wordName
     * @return 记录
     */
    DetailedWords selectByWordName(String wordName);

    /**
     * 根据拼音查询字
     * @param pinYin
     * @return 字名
     */
    String selectWordNameByPinYin(String pinYin);

    /**
     * 根据字查询拼音
     * @param wordName
     * @return 拼音
     */
    String selectPinYinByWordName(String wordName);

    /**
     * 分页查询所有对象
     * @param pageStart
     * @param pageLimit
     * @return 对象集合
     */
    List<DetailedWords> selectAll(int pageStart, int pageLimit);
    int selectAllCount();


}
