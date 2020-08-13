package com.cdut.kdchinese.pojo;

import java.util.List;
/**
 * Created with IntelliJ IDEA.
 * Date: 2020/06/24 17:46
 * Description:
 * Version: V1.0
 * @author lxj
 */
public interface MistakeWordMapper {
    /**
     * 插入汉字错误信息
     * @param misrecord
     * @return 1：插入成功 0：插入失败
     */
    int addMistakeinfo(MistakeWord misrecord);

    /**
     * 修改汉字错误信息
     * @param misrecord
     * @return 1：修改成功 0：修改失败
     */
    int modifyMistakeinfo(MistakeWord misrecord);


    /**
     * 通过id删除对应记录
     * @param id
     * @return 1：删除成功 0：删除失败
     */
    int delMistakeinfoById(Integer id);


    /**
     * 通过id集合查找对应所有记录
     * @param misrecord
     * @return MistakePinyin集合
     */
    List<MistakeWord> findMistakeinfoByIds(MistakeWord misrecord);

    /**
     * 通过id集合查删除对应所有记录
     * @param misrecord
     * @return 1：删除成功 0：删除失败
     */
    int delMistakeinfoBatch(MistakeWord misrecord);

    /**
     * 统计总记录条数
     * @return 记录条数
     */
    int selAllCount();

    /**
     * 分页查找拼音错误记录
     * @param pageStart
     * @param pageLimit
     * @return MiatakeWord集合
     */
    List<MistakeWord> findByPage(int pageStart, int pageLimit);

}
