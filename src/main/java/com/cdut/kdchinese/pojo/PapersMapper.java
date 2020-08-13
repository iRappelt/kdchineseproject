package com.cdut.kdchinese.pojo;

import java.util.List;

public interface PapersMapper {
    /**
     * 插入试卷信息
     * @param papers
     * @return 1：插入成功 0：插入失败
     */
    int addPapersinfo(Papers papers);

    /**
     * 修改试卷信息
     * @param papers
     * @return 1：修改成功 0：修改失败
     */
    int modifyPapersinfo(Papers papers);


    /**
     * 通过id删除试卷对应记录
     * @param id
     * @return 1：删除成功 0：删除失败
     */
    int delPapersinfoById(Integer id);


    /**
     * 通过id集合查找对应所有记录
     * @param papers
     * @return Papers集合
     */
    List<Papers> findPapersinfoByIds(Papers papers);

    /**
     * 通过id集合查删除对应所有试卷记录
     * @param papers
     * @return 1：删除成功 0：删除失败
     */
    int delPapersinfoBatch(Papers papers);

    /**
     * 统计总记录条数
     * @return 记录条数
     */
    int selAllCount();

    /**
     * 分页查找试卷记录
     * @param pageStart
     * @param pageLimit
     * @return Papers集合
     */
    List<Papers> findByPage(int pageStart, int pageLimit);

    /**
     * 通过paperId查找对应试卷题目
     * @param id
     * @return
     */

    Papers findQuestionByPaperId(Integer id);

    /**
     * 修改发布状态
     * @param id
     * @return 成功或者失败标志
     */

    int modifyPublish(Integer id);

    /**
     * 根据试卷的名称和班级ID查找该试卷对应ID
     * @param paperName
     * @param classId
     * @return
     */
    int findIdByPNCID(String paperName, Integer classId);

    int deleteExamByClassId(int classId);
}
