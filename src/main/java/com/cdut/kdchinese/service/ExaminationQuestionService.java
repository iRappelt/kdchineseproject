package com.cdut.kdchinese.service;

import com.cdut.kdchinese.pojo.ExaminationQuestion;
import com.cdut.kdchinese.pojo.ExaminationQuestionMapper;
import com.cdut.kdchinese.pojo.Papers;
import org.springframework.stereotype.Service;


import javax.annotation.Resource;
import java.util.List;
@Service
public class ExaminationQuestionService {

    @Resource
    private ExaminationQuestionMapper EQ;


    /**
     * 统计试卷所有条目数
     * @return 条目数
     */

    public int selAllCount(){
        return EQ.selAllCount();
    }

    /**
     * 添加试卷信息记录
     * @param examinationQuestion
     * @return 0：添加失败 1：添加成功
     */

    public int addQuestioninfo(ExaminationQuestion examinationQuestion){
        return EQ.addQuestioninfo(examinationQuestion);
    }

    /**
     * 修改错误记录
     * @param examinationQuestion
     * @return 0：修改失败 1：修改成功
     */

    public int modifyQuestioninfo(ExaminationQuestion examinationQuestion){
        return EQ.modifyQuestioninfo(examinationQuestion);

    }

    /**
     * 根据id号删除对应试卷信息
     * @param qid
     * @return 0：删除失败 1：删除成功
     */

    public int delQuestioninfoById(Integer qid){
        return  EQ.delQuestioninfoById(qid);
    }

    /**
     * 根据id集合查找对应题目信息
     * @param examinationQuestion
     * @return 0：查找失败 1：查找成功
     */

    public List<ExaminationQuestion> findQuestioninfoByIds(ExaminationQuestion examinationQuestion){
        return EQ.findQuestioninfoByIds(examinationQuestion);
    }

    /**
     * 根据id集合批量删除试卷信息
     * @param examinationQuestion
     * @return 0：删除失败 1：删除成功
     */
    public int delQuestioninfoBatch(ExaminationQuestion examinationQuestion){

        return EQ.delQuestioninfoBatch(examinationQuestion);
    }

    /**
     * 分页查找拼音错题信息
     * @param pageStart
     * @param pageLimit
     * @return  MistakePinyin集合
     */

    public  List<ExaminationQuestion> findByPage(int pageStart, int pageLimit){
        return EQ.findByPage(pageStart,pageLimit);

    }
}
