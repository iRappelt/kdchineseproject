package com.cdut.kdchinese.pojo;

import java.util.List;

public interface ExaminationQuestionMapper {
    /**
     * 插入题目信息
     * @param examinationQuestion
     * @return 1：插入成功 0：插入失败
     */
    int addQuestioninfo(ExaminationQuestion examinationQuestion);

    /**
     * 修改题目信息
     * @param  examinationQuestion
     * @return 1：修改成功 0：修改失败
     */
    int modifyQuestioninfo(ExaminationQuestion examinationQuestion);

    /**
     * 通过id删除对应记录
     * @param qid
     * @return 1：删除成功 0：删除失败
     */
    int delQuestioninfoById(Integer qid);

    /**
     * 通过id集合查找对应所有记录
     * @param examinationQuestion
     * @return MistakePinyin集合
     */
    List<ExaminationQuestion> findQuestioninfoByIds(ExaminationQuestion examinationQuestion);

    /**
     * 通过id集合查删除对应所有记录
     * @param examinationQuestion
     * @return 1：删除成功 0：删除失败
     */
    int delQuestioninfoBatch(ExaminationQuestion examinationQuestion);

    /**
     * 统计总记录条数
     * @return 记录条数
     */
    int selAllCount();

    /**
     * 分页查找题目记录
     * @param pageStart
     * @param pageLimit
     * @return ExaminationQuestion集合
     */
    List<ExaminationQuestion> findByPage(int pageStart, int pageLimit);




}
