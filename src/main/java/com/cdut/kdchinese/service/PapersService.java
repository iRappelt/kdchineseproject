package com.cdut.kdchinese.service;

import com.cdut.kdchinese.pojo.MistakePinyin;
import com.cdut.kdchinese.pojo.MistakePinyinMapper;
import com.cdut.kdchinese.pojo.Papers;
import com.cdut.kdchinese.pojo.PapersMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
@Service
public class PapersService {
    @Resource
    private PapersMapper pm;


    /**
     * 统计试卷所有条目数
     * @return 条目数
     */

    public int selAllCount(){
        return pm.selAllCount();
    }

    /**
     * 添加试卷信息记录
     * @param papers
     * @return 0：添加失败 1：添加成功
     */

    public int addPapersinfo(Papers papers){
        return pm.addPapersinfo(papers);
    }

    /**
     * 修改拼音错误记录
     * @param papers
     * @return 0：修改失败 1：修改成功
     */

    public int modifyPapersinfo(Papers papers){
        return pm.modifyPapersinfo(papers);

    }

    /**
     * 根据id号删除对应试卷信息
     * @param id
     * @return 0：删除失败 1：删除成功
     */

    public int delPapersinfoById(Integer id){
        return  pm.delPapersinfoById(id);
    }

    /**
     * 根据id集合查找对应试卷信息
     * @param papers
     * @return 0：查找失败 1：查找成功
     */

    public List<Papers> findPapersinfoByIds(Papers papers){
        return pm.findPapersinfoByIds(papers);
    }

    /**
     * 根据id集合批量删除试卷信息
     * @param papers
     * @return 0：删除失败 1：删除成功
     */
    public int delPapersinfoBatch(Papers papers){

        return pm.delPapersinfoBatch(papers);
    }

    /**
     * 分页查找拼音错题信息
     * @param pageStart
     * @param pageLimit
     * @return  MistakePinyin集合
     */

    public  List<Papers> findByPage(int pageStart, int pageLimit){
        return pm.findByPage(pageStart,pageLimit);

    }

    /**
     * 通过papers的id查找题目
     * @param id
     * @return
     */

    public Papers findQuestionByPaperId(Integer id){
        return pm.findQuestionByPaperId(id);
    }

    /**
     * 修改试卷publish
     * @param id
     * @return失败或者成功标志
     */
    public int modifyPapersPublish(Integer id){
        return pm.modifyPublish(id);
    }

    public int findIdByPNCID(String paperName,Integer classId){
        return pm.findIdByPNCID(paperName,classId);
    }

    public int deleteExamByClassId(int classId){return pm.deleteExamByClassId(classId);}

}
