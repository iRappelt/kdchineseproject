package com.cdut.kdchinese.controller;

import com.cdut.kdchinese.pojo.Exercise;
import com.cdut.kdchinese.service.ExerciseService;
import com.cdut.kdchinese.util.Result;
import com.cdut.kdchinese.util.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * Copyright (C), 2020-2020, 快对语文
 * FileName: ExerciseService
 * Date:     2020/6/22 10:58
 * Description: 习题控制层
 * @Author  healer
 */
@Controller
public class ExerciseController {

    @Autowired
    private ExerciseService exerciseService;

    /**
     * 添加习题
     *
     * @param map 数据map
     * @return result
     */
    @RequestMapping(value = "/add_exercise_pinyin", method = RequestMethod.POST)
    @ResponseBody
    public Result addExercise(@RequestBody Map<String, Object> map) {
        //获取数据
        String answer = (String) map.get("answer");
        String pinyin = (String) map.get("pinyin");
        String shengmu = (String) map.get("shengmu");
        String yunmu = (String) map.get("yunmu");
        String question = (String) map.get("question");
        int grade = (int) map.get("grade");
        //封装习题
        Exercise exercise = new Exercise();
        exercise.setWordName(answer);
        exercise.setWordPinyin(pinyin);
        exercise.setWordShengmu(shengmu);
        exercise.setWordYunmu(yunmu);
        exercise.setA(question);
        int status = exerciseService.insExercise(grade, exercise);
        if (status <= 0) {
            return Result.failure(ResultCode.EXERCISE_PINYIN_ADD_ERROR);
        } else {
            return Result.success();
        }
    }

    /**
     * 编辑更新习题
     *
     * @param map 习题数据
     * @return 状态码
     */
    @RequestMapping(value = "/update_exercise_pinyin", method = RequestMethod.POST)
    @ResponseBody
    public Result updateExercise(@RequestBody Map<String, Object> map) {

        //获取数据
        int wordid = (int) map.get("id");
        String answer = (String) map.get("answer");
        String pinyin = (String) map.get("pinyin");
        String shengmu = (String) map.get("shengmu");
        String yunmu = (String) map.get("yunmu");
        String question = (String) map.get("question");
        int grade = (int) map.get("grade");
        //封装习题
        Exercise exercise = new Exercise();
        exercise.setWordId(wordid);
        exercise.setWordName(answer);
        exercise.setWordPinyin(pinyin);
        exercise.setWordShengmu(shengmu);
        exercise.setWordYunmu(yunmu);
        exercise.setA(question);

        int status = exerciseService.updExerciseById(grade, exercise);
        if (status <= 0) {
            return Result.failure(ResultCode.EXERCISE_PINYIN_UPDATE_ERROR);
        } else {
            return Result.success();
        }
    }

    /**
     * 根据id删除习题
     *
     * @param id    习题的id
     * @param grade 习题的年级
     * @return 状态码
     */
    @RequestMapping(value = "/delete_exercise_pinyin", method = RequestMethod.GET)
    @ResponseBody
    public Result deleteExercise(int id, int grade) {
        int status = exerciseService.delExerciseById(id, grade);
        if (status <= 0) {
            return Result.failure(ResultCode.EXERCISE_PINYIN_DELETE_ERROR);
        } else {
            return Result.success();
        }
    }

    /**
     * 批量删除习题
     *
     * @param map dlist：id列表，grade：年级
     * @return 状态码
     */
    @RequestMapping(value = "/batch_delete_exercise_pinyin", method = RequestMethod.POST)
    @ResponseBody
    public Result batchDeleteExercise(@RequestBody Map<String, Object> map) {
        List<Integer> ids = (List<Integer>) map.get("delList");
        int grade = (int) map.get("grade");

        System.out.println(ids);
        System.out.println(grade);

        int status = exerciseService.delExerciseBatched(ids, grade);
        if (status == ids.size()) {
            return Result.success();
        } else {
            return Result.failure(ResultCode.EXERCISE_PINYIN_BATCHDELETE_ERROR);
        }
    }

    /**
     * 分页查询
     *
     * @param page  页码
     * @param limit 每页数量
     * @param grade 年级
     * @return 状态码及查询结果
     */
    @RequestMapping(value = "/show_exercise_pinyin", method = RequestMethod.GET)
    @ResponseBody
    public Result showByPage(int page, int limit, int grade) {
        List<Exercise> exercises = exerciseService.selByPage(page, limit, grade);
        int count = exerciseService.getCount(grade);
        Object[] object = {exercises, count};
        return Result.success(object);
    }
}
