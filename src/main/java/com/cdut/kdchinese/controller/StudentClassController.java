package com.cdut.kdchinese.controller;

import com.cdut.kdchinese.converter.User2UserVO;
import com.cdut.kdchinese.pojo.StudentClass;
import com.cdut.kdchinese.pojo.User;
import com.cdut.kdchinese.service.StudentClassService;
import com.cdut.kdchinese.service.UserService;
import com.cdut.kdchinese.util.Result;
import com.cdut.kdchinese.util.ResultCode;
import com.cdut.kdchinese.vo.StudentVO;
import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class StudentClassController {
    @Resource
    private StudentClassService studentClassService;
    @Resource
    private UserService userService;

    /**
     * 通过班级id分页查询学生信息
     * @param page
     * @param limit
     * @param classId
     * @return
     */
    @GetMapping("/show_student")
    @ResponseBody
    public Result showStudent(int page,int limit,int classId){
        int startIndex = limit * (page - 1);
        List<StudentClass> studentClassList = studentClassService.selByCI(startIndex,limit,classId);
        List<User> userList = new ArrayList<>();
        User user = null;
        List<Integer> ids = new ArrayList<>();
        for(StudentClass studentClass:studentClassList){
            ids.add(studentClass.getStudentId());
            System.out.println(studentClass.getStudentId());
        }
        //依次查询学生信息
        for(Integer uid:ids){
            user = userService.selUserById(uid);
            userList.add(user);
        }
        //转换VO
        List<StudentVO> studentVOList = User2UserVO.User2UserVO(userList);
        int count = studentVOList.size();
        Map<String,Object> map = new HashMap<>();
        map.put("students",studentVOList);
        map.put("count",count);
        return Result.success(map);
    }

    /**
     * 删除学生
     * @param id
     * @return
     */
    @GetMapping("/delete_student")
    @ResponseBody
    public Result deleteStudent(Integer id){
        int result = studentClassService.deleteByUserId(id);
        if(result == 1)
            return Result.success();
        else
            return Result.failure(ResultCode.STUDENT_DELETE_ERROR);
    }

    /**
     * 批量删除学生
     * @param map
     * @return
     */
    @PostMapping("/batch_delete_student")
    @ResponseBody
    public Result batchDeleteStudent(@RequestBody Map<String,Object> map){
        JSONArray jsonArray = JSONArray.fromObject(map.get("delList"));
        List list = JSONArray.toList(jsonArray,"",new JsonConfig());
        int len = list.size();
        int result = studentClassService.batchDeleteStudent(list);
        if(result == len)
            return Result.success();
        else
            return Result.failure(ResultCode.STUDENT_BATCHDELETE_ERROR);
    }

    /**
     * 添加学生
     * @param map
     * @return
     */
    @PostMapping("/add_student")
    @ResponseBody
    public Result addStudent(@RequestBody Map<String,Object> map){
        Integer classId = (Integer) map.get("classId") ;
        String telephone = (String) map.get("telephone");
        System.out.println(classId);
        System.out.println(telephone);
        User user = userService.selUserByTel(telephone);
        if(user == null)
            return Result.failure(ResultCode.TEL_NOT_EXIST);
        StudentClass studentClass = new StudentClass();
        studentClass.setClassId(classId);
        studentClass.setStudentId(user.getUid());
        int result = studentClassService.addStudentClass(studentClass);
        if(result == 1)
            return Result.success();
        else
            return Result.failure(ResultCode.STUDENT_INSERT_ERROR);
    }


}
