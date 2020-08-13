package com.cdut.kdchinese.service;

import com.cdut.kdchinese.pojo.DetailedWords;
import com.cdut.kdchinese.pojo.DetailedWordsMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Copyright (C), 2020-2020, 快对语文
 * FileName: DetailedWordsService
 * Date:     2020/6/20 10:58
 * Description: 汉字表service层
 * @Author  lfc
 */
@Service
public class DetailedWordsService {
    @Resource
    private DetailedWordsMapper detailedWordsMapper;

    /**
     * 分页查询DetailedWords对象
     * @param pageStart 页码
     * @param pageLimit 每页数量
     * @return DetailedWords集合
     */
    public List<DetailedWords> selAll(int pageStart, int pageLimit){
        return detailedWordsMapper.selectAll(pageStart, pageLimit);
    }

    /**
     * 根据主键查询得到DetailedWords对象
     * @param id
     * @return DetailedWords对象
     **/
    public DetailedWords selDWById(Integer id){
        return detailedWordsMapper.selectByPrimaryKey(id);
    }

    /**
     * 根据字名查询DetailedWords对象
     * @param wordName
     * @return DetailedWords对象
     */
    public DetailedWords selDWByWN(String wordName){
        return detailedWordsMapper.selectByWordName(wordName);
    }

    /**
     * 根据字名查询拼音
     * @param wordName
     * @return 拼音
     */
    public String selPYByWN(String wordName){
        return  detailedWordsMapper.selectPinYinByWordName(wordName);
    }

    /**
     * 根据拼音查询字名
     * @param wordPinYin
     * @return 字名
     */
    public String selWNByPY(String wordPinYin){
        return  detailedWordsMapper.selectWordNameByPinYin(wordPinYin);
    }

    /**
     * 选择性的将汉字信息添加到汉字表中
     * @param detailedWords detailedWords对象，其中的属性值可以为null
     * @return 添加成功返回（1）不成功返回(0)
     */
    public int insDWSelective(DetailedWords detailedWords){
        return detailedWordsMapper.insertSelective(detailedWords);
    }

    /**
     * 根据主键删除detailedWords
     * @param wordId
     * @return 结果
     */
    public int delDWByPK(int wordId){
        return detailedWordsMapper.deleteByPrimaryKey(wordId);
    }

    /**
     * 根据主键动态更新
     * @param detailedWords
     * @return 更新结果
     */
    public int updDWByPKSelective(DetailedWords detailedWords){
        return detailedWordsMapper.updateByPrimaryKeySelective(detailedWords);
    }

    /**
     * 根据主键批量删除
     * @param list
     * @return 删除条数
     */
    public int delDWByPKList(List list){
        return detailedWordsMapper.batchDeleteByPrimaryKey(list);
    }
    public int getCount(){return detailedWordsMapper.selectAllCount();}

}
