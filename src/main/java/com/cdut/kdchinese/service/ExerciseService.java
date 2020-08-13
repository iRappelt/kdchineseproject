package com.cdut.kdchinese.service;

import com.cdut.kdchinese.pojo.Exercise;
import com.cdut.kdchinese.pojo.ExerciseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Copyright (C), 2020-2020, 快对语文
 * FileName: ExerciseService
 * Date:     2020/6/22 10:58
 * Description: 习题业务层
 * @Author  healer
 */
@Service
public class ExerciseService {
    @Autowired
    ExerciseMapper exerciseMapper;

    /**
     * 添加习题
     * @param grade 年级
     * @param exercise 习题
     * @return 成功插入数
     */
    public int insExercise(int grade, Exercise exercise) {
        return exerciseMapper.insert(grade,exercise);
    }

    /**
     * 更新习题
     * @param grade 年级
     * @param exercise 习题
     * @return 成功更新数
     */
    public int updExerciseById(int grade, Exercise exercise) {
        return exerciseMapper.updateByPrimaryKey(grade,exercise);
    }

    /**
     * 根据id删除习题
     * @param id id
     * @param grade 年级
     * @return 成功删除数
     */
    public int delExerciseById(int id, int grade) {
        return exerciseMapper.deleteByPrimaryKey(id,grade);
    }

    /**
     * 根据id列表批量删除
     * @param ids id列表
     * @param grade 年级
     * @return 删除条数
     */
    public int delExerciseBatched(List<Integer> ids, int grade){
        return exerciseMapper.deleteMoreByPrimaryKey(ids,grade);
    }

    /**
     * 分页查询
     * @param pageStart 起始页
     * @param pageLimit 每页数
     * @param grade 年级
     * @return 习题列表
     */
    public List<Exercise> selByPage(int pageStart, int pageLimit, int grade) {
        return exerciseMapper.selectByPage((pageStart-1)*pageLimit,pageLimit,grade);
    }

    public int getCount(int grade){
        return exerciseMapper.selCount(grade);

    }
}
