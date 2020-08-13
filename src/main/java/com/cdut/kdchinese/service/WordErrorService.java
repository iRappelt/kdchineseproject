package com.cdut.kdchinese.service;

import com.cdut.kdchinese.pojo.MistakePinyin;
import com.cdut.kdchinese.pojo.MistakePinyinMapper;
import com.cdut.kdchinese.pojo.MistakeWord;
import com.cdut.kdchinese.pojo.MistakeWordMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
/**
 * Created with IntelliJ IDEA.
 * Date: 2020/06/24 17:46
 * Description:
 * Version: V1.0
 * @author lxj
 */
@Service
public class WordErrorService {
    @Resource
    private MistakeWordMapper mm;
    /**
     * 统计汉字错误所有条目数
     * @return 条目数
     */
    public int selAllCount(){

        return mm.selAllCount();
    }

    /**
     * 添加汉字错误记录
     * @param misrecord
     * @return 0：添加失败 1：添加成功
     */

    public int addMistakeinfo(MistakeWord misrecord){

        return mm.addMistakeinfo(misrecord);
    }

    /**
     * 修改汉字错误记录
     * @param misrecord
     * @return 0：修改失败 1：修改成功
     */

    public int updMistakeinfo(MistakeWord misrecord){
        return mm.modifyMistakeinfo(misrecord);

    }
    /**
     * 根据id号删除对应错题信息
     * @param id
     * @return 0：删除失败 1：删除成功
     */

    public int delMistakeinfoById(Integer id){

        return  mm.delMistakeinfoById(id);
    }

    /**
     * 根据id集合查找对应错题信息
     * @param misrecord
     * @return 0：查找失败 1：查找成功
     */

    public List<MistakeWord> selMistakeinfoByIds(MistakeWord misrecord){

        return mm.findMistakeinfoByIds(misrecord);
    }

    /**
     * 根据id集合批量删除错题信息
     * @param misrecord
     * @return 0：删除失败 1：删除成功
     */
    public int delMistakeinfoBatch(MistakeWord misrecord){
        return mm.delMistakeinfoBatch(misrecord);
    }
    /**
     * 分页查找汉字错题信息
     * @param pageStart
     * @param pageLimit
     * @return  MistakeWord集合
     */

    public  List<MistakeWord> selByPage(int pageStart, int pageLimit){
        return mm.findByPage(pageStart,pageLimit);

    }
}
