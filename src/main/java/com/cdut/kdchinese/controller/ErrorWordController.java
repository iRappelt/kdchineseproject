package com.cdut.kdchinese.controller;


import com.cdut.kdchinese.pojo.MistakeWord;
import com.cdut.kdchinese.service.WordErrorService;
import com.cdut.kdchinese.util.Result;
import com.cdut.kdchinese.util.ResultCode;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Date: 2020/06/24 17:46
 * Description:
 * Version: V1.0
 * @author lxj
 */
@Controller
public class ErrorWordController {
    @Resource
    private WordErrorService wes;

    @RequestMapping(value = "/add_word_error", method = RequestMethod.POST)
    @ResponseBody
    public Result addWordError(@RequestBody Map<String, Object> map) {
        String word = (String)map.get("answer");
        String pinyin = (String)map.get("pinyin");
        String title = (String)map.get("title");
        String grade = (String)map.get("grade");
        Integer frequency = Integer.valueOf((String)map.get("frequency"));
        Integer userid=Integer.valueOf((String)map.get("userid"));
        MistakeWord mw = new MistakeWord();
        mw.setPinyin(pinyin);
        mw.setWord(word);
        mw.setTitle(title);
        mw.setWord_grade(grade);
        mw.setFrequency(frequency);
        mw.setUser_id(userid);
        int result = wes.addMistakeinfo(mw);
        if(result==1){
            return  Result.success();
        }else{
            return Result.failure(ResultCode.WordError_ADD_ERROR);
        }

    }



    @RequestMapping(value = "/update_word_error", method = RequestMethod.POST)
    @ResponseBody
    public Result updateWordError(@RequestBody Map<String, Object> map) {
        Integer id=(Integer) map.get("id");
        String word = (String)map.get("answer");
        String pinyin = (String)map.get("pinyin");
        String title = (String)map.get("title");
        String grade = (String)map.get("grade");
        Integer frequency = Integer.valueOf((String)map.get("frequency"));
        Integer userid=Integer.valueOf((String)map.get("userid"));
        MistakeWord mw = new MistakeWord();
        mw.setId(id);
        mw.setPinyin(pinyin);
        mw.setWord(word);
        mw.setTitle(title);
        mw.setWord_grade(grade);
        mw.setFrequency(frequency);
        mw.setUser_id(userid);
        int result = wes.updMistakeinfo(mw);

        if(result==1){
            return  Result.success();
        }else{
            return Result.failure(ResultCode.WordError_UPDATE_ERROR);
        }

    }


    @RequestMapping(value = "/delete_word_error", method = RequestMethod.GET)
    @ResponseBody
    public Result deleteWordError(Integer id) {
        int result = wes.delMistakeinfoById(id);
        if(result==1){
            return  Result.success();
        }else{
            return Result.failure(ResultCode.WordError_DELETE_ERROR);
        }

    }

    @RequestMapping(value = "/batch_delete_word_error", method = RequestMethod.POST)
    @ResponseBody
    public Result batchDeleteWordError(@RequestBody Map<String, Object> map) {
        List<Integer> delList=(List<Integer>) map.get("delList");
        int length = delList.size();
        MistakeWord mw = new MistakeWord();
        mw.setIds(delList);
        int result = wes.delMistakeinfoBatch(mw);
        if(result==length){
            return  Result.success();
        }else{
            return Result.failure(ResultCode.Batch_WordError_DELETE_ERROR);
        }

    }


    @RequestMapping(value = "/show_word_error", method = RequestMethod.GET)
    @ResponseBody
    public Result   showWordError(String page, String limit) {
        //每页展示数量
        int pageLimit = Integer.valueOf(limit);
        //页码
        int pageStart = pageLimit*(Integer.valueOf(page)-1);
        //总数
        int count = wes.selAllCount();
        List<MistakeWord> mps = wes.selByPage(pageStart,pageLimit);
        Object[] obj={mps,count};
        return Result.success(obj);

    }
}
