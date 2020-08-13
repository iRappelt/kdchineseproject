package com.cdut.kdchinese.service;

import com.cdut.kdchinese.pojo.MistakePinyin;
import com.cdut.kdchinese.pojo.MistakePinyinMapper;
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
public class PinyinErrorService {
    @Resource
    private MistakePinyinMapper mpm;


    /**
     * 统计拼音所有条目数
     * @return 条目数
     */

    public int selAllCount(){
        return mpm.selAllCount();
    }

    /**
     * 添加拼音错误记录
     * @param misrecord
     * @return 0：添加失败 1：添加成功
     */

    public int addMistakeinfo(MistakePinyin misrecord){
        return mpm.addMistakeinfo(misrecord);
    }

    /**
     * 修改拼音错误记录
     * @param misrecord
     * @return 0：修改失败 1：修改成功
     */

    public int updMistakeinfo(MistakePinyin misrecord){
        return mpm.modifyMistakeinfo(misrecord);

    }

    /**
     * 根据id号删除对应错题信息
     * @param id
     * @return 0：删除失败 1：删除成功
     */

    public int delMistakeinfoById(Integer id){
        return  mpm.delMistakeinfoById(id);
    }

    /**
     * 根据id集合查找对应错题信息
     * @param misrecord
     * @return 0：查找失败 1：查找成功
     */

    public List<MistakePinyin> selMistakeinfoByIds(MistakePinyin misrecord){
        return mpm.findMistakeinfoByIds(misrecord);
    }

    /**
     * 根据id集合批量删除错题信息
     * @param misrecord
     * @return 0：删除失败 1：删除成功
     */
    public int delMistakeinfoBatch(MistakePinyin misrecord){
        return mpm.delMistakeinfoBatch(misrecord);
    }

    /**
     * 分页查找拼音错题信息
     * @param pageStart
     * @param pageLimit
     * @return  MistakePinyin集合
     */

    public  List<MistakePinyin> selByPage(int pageStart, int pageLimit){
        return mpm.findByPage(pageStart,pageLimit);

    }
}
