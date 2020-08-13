package com.cdut.kdchinese.controller;

import com.cdut.kdchinese.pojo.MistakePinyin;
import com.cdut.kdchinese.service.PinyinErrorService;
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
public class ErrorPinyinController {
    @Resource
    private PinyinErrorService pes;

    /**
     * 添加拼音错误信息记录
     * @param map
     * @return 成功或失败提醒
     */

    @RequestMapping(value = "/add_pinyin_error", method = RequestMethod.POST)
    @ResponseBody
    public Result addPinyinError(@RequestBody Map<String, Object> map) {
        String pinyin = (String)map.get("answer");
        String word = (String)map.get("word");
        String title = (String)map.get("title");
        String grade = (String)map.get("grade");
        Integer frequency = Integer.valueOf((String)map.get("frequency"));
        Integer userid=Integer.valueOf((String)map.get("userid"));
        MistakePinyin mp = new MistakePinyin();
        mp.setPinyin(pinyin);
        mp.setWord(word);
        mp.setTitle(title);
        mp.setWord_grade(grade);
        mp.setFrequency(frequency);
        mp.setUser_id(userid);
        int result = pes.addMistakeinfo(mp);
        if(result==1){
            return  Result.success();
        }else{
            return Result.failure(ResultCode.PinYinError_ADD_ERROR);
        }

    }

    /**
     * 更新拼音错误信息
     * @param map
     * @return 成功或失败提醒
     */

    @RequestMapping(value = "/update_pinyin_error", method = RequestMethod.POST)
    @ResponseBody
    public Result updatePinyinError(@RequestBody Map<String, Object> map) {
        Integer id=(Integer) map.get("id");
        String pinyin = (String)map.get("answer");
        String word = (String)map.get("word");
        String title = (String)map.get("title");
        String grade = (String)map.get("grade");
        Integer frequency = Integer.valueOf((String)map.get("frequency"));
        Integer userid=Integer.valueOf((String)map.get("userid"));
        MistakePinyin mp = new MistakePinyin();
        mp.setId(id);
        mp.setPinyin(pinyin);
        mp.setWord(word);
        mp.setTitle(title);
        mp.setWord_grade(grade);
        mp.setFrequency(frequency);
        mp.setUser_id(userid);
        int result = pes.updMistakeinfo(mp);

        if(result==1){
            return  Result.success();
        }else{
            return Result.failure(ResultCode.PinYinError_UPDATE_ERROR);
        }

    }

    /**
     * 根据id删除拼音信息记录
     * @param id
     * @return
     */


    @RequestMapping(value = "/delete_pinyin_error", method = RequestMethod.GET)
    @ResponseBody
    public Result deletePinyinError(Integer id) {
        int result = pes.delMistakeinfoById(id);
        if(result==1){
            return  Result.success();
        }else{
            return Result.failure(ResultCode.PinYinError_DELETE_ERROR);
        }

    }

    @RequestMapping(value = "/batch_delete_pinyin_error", method = RequestMethod.POST)
    @ResponseBody
    public Result batchDeletePinyinError(@RequestBody Map<String, Object> map) {
        List<Integer> delList=(List<Integer>) map.get("delList");
        int length = delList.size();
        MistakePinyin mp = new MistakePinyin();
        mp.setIds(delList);
        int result = pes.delMistakeinfoBatch(mp);
        if(result==length){
            return  Result.success();
        }else{
            return Result.failure(ResultCode.Batch_PinYinError_DELETE_ERROR);
        }

    }


    @RequestMapping(value = "/show_pinyin_error", method = RequestMethod.GET)
    @ResponseBody
    public Result   showPinyinError(String page, String limit) {
        //每页展示数量
        int pageLimit = Integer.valueOf(limit);
        //页码
        int pageStart = pageLimit*(Integer.valueOf(page)-1);
        //总数
        int count = pes.selAllCount();
        List<MistakePinyin> mps = pes.selByPage(pageStart,pageLimit);
        Object[] obj={mps,count};
        return Result.success(obj);

    }


}
