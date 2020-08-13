package com.cdut.kdchinese.controller;

import com.cdut.kdchinese.converter.Classes2ClassVO;
import com.cdut.kdchinese.pojo.Classes;
import com.cdut.kdchinese.service.ClassesService;
import com.cdut.kdchinese.service.PapersService;
import com.cdut.kdchinese.service.StudentClassService;
import com.cdut.kdchinese.util.ClassCodeUtil;
import com.cdut.kdchinese.util.DateUtil;
import com.cdut.kdchinese.util.Result;
import com.cdut.kdchinese.util.ResultCode;
import com.cdut.kdchinese.vo.ClassesVO;
import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class ClassesController {
    @Resource
    private ClassesService classesService;

    @Resource
    private StudentClassService studentClassService;

    @Resource
    private PapersService papersService;
    /**
     * 分页查询指定教师的班级信息
     * @param page
     * @param limit
     * @param teacherId
     * @return
     */
    @GetMapping("/show_class")
    @ResponseBody
    public Result showClass(int page, int limit, int teacherId){
        //每页查询条数
        int pageLimit = limit;
        //从第startIndex条开始查询
        int startIndex = pageLimit * (page - 1);
        List<Classes> classList = classesService.selAll(startIndex,pageLimit,teacherId);
        //查询到的班级总数
        int count = classList.size();
        //封装需要返回的数据
        List<ClassesVO> classVOList = Classes2ClassVO.convert(classList);
        //班级人数
        int number = 0;
        for(ClassesVO classesVO:classVOList){
            //班级人数
            number = studentClassService.selectNumberByClassId(classesVO.getClassId()).size();
            classesVO.setNumber(number);
        }
        Map<String,Object> map = new HashMap<>();
        map.put("classes",classVOList);
        map.put("count",count);
        return Result.success(map);
    }

    /**
     * 删除班级并删除与班级关联的学生班级表以及试题表
     * @param id
     * @return
     */
    @GetMapping("/delete_class")
    @ResponseBody//多次数据库操作，防止其中一次失败，其他成功导致的不一致性
    public Result deleteClass(int id){
        //删除班级
        int result1 = classesService.delByPK(id);
        //删除学生班级表相应的记录
        int result2 = studentClassService.delByCI(id);
        //删除与班级关联的试题表
        int result3 = papersService.deleteExamByClassId(id);
        if(result1 == 1) {
            return Result.success();
        } else {
            return Result.failure(ResultCode.CLASS_DELETE_ERROR);
        }
    }

    /**
     * 添加班级
     * @param map
     * @return
     */
    @PostMapping("/add_class")
    @ResponseBody
    public Result addClass(@RequestBody Map<String,Object> map){
        String name = (String) map.get("name");
        int teacherId = Integer.parseInt(map.get("teacherId").toString()) ;
        //int teacherId = (Integer) map.get("teacherId") ;
        System.out.println(name);
        System.out.println(teacherId);
        Classes bclass = classesService.selByCN(name);
        if(bclass != null)
            return Result.failure(ResultCode.CLASS_HAS_EXIST);
        Classes aclass = new Classes();
        aclass.setClassCode(ClassCodeUtil.generateShortUuid());
        aclass.setClassName(name);
        aclass.setClassDate(DateUtil.getDate());
        aclass.setTeacherId(teacherId);
        int result = classesService.insSelective(aclass);
        if(result==1)
            return Result.success();
        else
            return Result.failure(ResultCode.CLASS_INSERT_ERROR);
    }

    /**
     * 批量删除班级
     * @param map
     * @return
     */
    @PostMapping("/batch_delete_class")
    @ResponseBody
    public Result batchDeleteClass(@RequestBody Map<String,Object> map){
        JSONArray jsonArray = JSONArray.fromObject(map.get("delList"));
        List<Integer> list = JSONArray.toList(jsonArray,"",new JsonConfig());
        int result = classesService.delByPKList(list);
        //删除学生班级表相关内容
        int result2 = 0;
        int result3 = 0;
        for(Integer id:list){
            result2 = studentClassService.delByCI(id);
            result3 = papersService.deleteExamByClassId(id);
        }
        if(result == list.size())
            return Result.success();
        else
            return Result.failure(ResultCode.CLASS_BATCHDELETE_ERROR);
    }

    /**
     * 更新班级信息
     * @param map
     * @return
     */
    @PostMapping("/update_class")
    @ResponseBody
    public Result updateClass(@RequestBody Map<String,Object> map){
        Integer classId = Integer.parseInt(map.get("id").toString());
        String name = (String) map.get("name");
        Classes classes = new Classes();
        classes.setClassId(classId);
        classes.setClassName(name);
        int result = classesService.updByPKSelective(classes);
        if(result == 1)
            return Result.success();
        else
            return Result.failure(ResultCode.CLASS_UPDATE_ERROR);
    }

    /**
     * 查询班级信息
     * @param teacherId
     * @return
     */
    @GetMapping("/get_class_info")
    @ResponseBody
    public Result getClassInfo(Integer teacherId){
        List<Classes> classesList = classesService.selByTI(teacherId);
        if(classesList == null){
            return Result.failure(ResultCode.CLASS_GET_ERROR);
        }
        List<Integer> classIds = classesList.stream().map(e->e.getClassId())
                                    .collect(Collectors.toList());
        List<String> nameList = classesList.stream().map(e->e.getClassName())
                                    .collect(Collectors.toList());
        Map<String,List> map = new HashMap<>();
        map.put("classIds",classIds);
        map.put("classNames",nameList);
        return Result.success(map);
    }


}