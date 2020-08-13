package com.cdut.kdchinese.pojo;

import java.util.List;
/**
 * Created with IntelliJ IDEA.
 * Date: 2020/06/24 17:46
 * Description:
 * Version: V1.0
 * @author lxj
 */
public interface MistakePinyinMapper {
     /**
      * 插入拼音错误信息
      * @param misrecord
      * @return 1：插入成功 0：插入失败
      */
     int addMistakeinfo(MistakePinyin misrecord);

     /**
      * 修改拼音错误信息
      * @param misrecord
      * @return 1：修改成功 0：修改失败
      */
     int modifyMistakeinfo(MistakePinyin misrecord);

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
     List<MistakePinyin> findMistakeinfoByIds(MistakePinyin misrecord);

     /**
      * 通过id集合查删除对应所有记录
      * @param misrecord
      * @return 1：删除成功 0：删除失败
      */
     int delMistakeinfoBatch(MistakePinyin misrecord);

     /**
      * 统计总记录条数
      * @return 记录条数
      */
     int selAllCount();

     /**
      * 分页查找拼音错误记录
      * @param pageStart
      * @param pageLimit
      * @return MiatakePinyin集合
      */
     List<MistakePinyin> findByPage(int pageStart, int pageLimit);
}
