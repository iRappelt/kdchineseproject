package com.cdut.kdchinese.controller;

import com.cdut.kdchinese.pojo.DetailedWords;
import com.cdut.kdchinese.service.DetailedWordsService;
import com.cdut.kdchinese.util.Result;
import com.cdut.kdchinese.util.ResultCode;
import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 *@ClassName DetailedWordsController
 *@Description 汉字表controller
 *@Author Chao
 *@Date 2020/6/20 17:39
 *@Version 1.0
 */
@Controller
public class DetailedWordsController {
    @Resource
    private DetailedWordsService detailedWordService;

    @GetMapping("/delete_word")
    @ResponseBody
    public Result deleteWords(String id){
        if(id==null||id.equals("")) {
            return Result.failure(ResultCode.PARAM_IS_BLANK);
        }
        Integer wordId = Integer.parseInt(id);
        int result = detailedWordService.delDWByPK(wordId);
        if(result!=1) {
            return Result.failure(ResultCode.WORD_DELETE_ERROR);
        }
        else {
            return Result.success();
        }
    }

    /**
     * 分页查询所有汉字
     * @param page 当前页码
     * @param limit 每页查询条数
     * @return 汉字集合
     */
    @GetMapping("/show_word")
    @ResponseBody
    public Result showWords(String page,String limit){
        //每页查询条数
        int pageLimit = Integer.valueOf(limit);
        //从第firstIndex条数据开始
        int firstIndex = pageLimit * (Integer.valueOf(page)-1);
        List<DetailedWords> detailedWordsList = detailedWordService.selAll(firstIndex,pageLimit);
        int count = detailedWordService.getCount();
        // 先封装到一个Object数组中
        Object[] obj = {detailedWordsList, count};
        // 数据传递
        return Result.success(obj);
    }

    @PostMapping("/add_word")
    @ResponseBody
    public Result addWords(@RequestBody DetailedWords detailedWords){
        //请求为post，使用@RequestBody，将json对象转为JavaBean对象
        //当客户端的HTTP请求参数contentType设置为：application/json
        String wordName = detailedWords.getWordName();
        DetailedWords detailedWords1 = detailedWordService.selDWByWN(wordName);
        System.out.println(detailedWords1);
        if(detailedWords1 != null) {
            return Result.failure(ResultCode.WORD_HAS_EXIST);
        }
        int result = detailedWordService.insDWSelective(detailedWords);
        if(result==1) {
            return Result.success();
        } else {
            return Result.failure(ResultCode.WORD_INSERT_ERROR);
        }
    }

    @PostMapping("/batch_delete_word")
    @ResponseBody
    public Result batchDeleteWords(@RequestBody Map<String,Object> map){
        JSONArray jsonArray = JSONArray.fromObject(map.get("delList"));
        List list = JSONArray.toList(jsonArray,"",new JsonConfig());
        int len = list.size();
        int result = detailedWordService.delDWByPKList(list);
        if(result == len) {
            return Result.success();
        } else {
            return Result.failure(ResultCode.WORD_DELETE_ERROR);
        }
    }

    @PostMapping("/update_word")
    @ResponseBody
    public Result updateWords(@RequestBody DetailedWords detailedWords){
        int wordId = detailedWords.getWordId();
        DetailedWords detailedWords1 = detailedWordService.selDWById(wordId);
        if(detailedWords1 == null)
            return Result.failure(ResultCode.WORD_NOT_EXIST);
        int result = detailedWordService.updDWByPKSelective(detailedWords);
        if(result==1) {
            return Result.success();
        } else {
            return Result.failure(ResultCode.WORD_UPDATE_ERROR);
        }
    }

}
