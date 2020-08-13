package com.cdut.kdchinese.service;

import com.cdut.kdchinese.pojo.Classes;
import com.cdut.kdchinese.pojo.StudentClass;
import com.cdut.kdchinese.pojo.StudentClassMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
@Service
public class StudentClassService {

    @Resource
    private StudentClassMapper studentClassMapper;

    public List<StudentClass> selByCI(int pageStart, int pageLimit,int classId){
        return studentClassMapper.selectByClassId(pageStart,pageLimit,classId);
    }
    public int delByCI(int classId){return studentClassMapper.deleteByClassId(classId);}

    public int deleteByUserId(int userId){
        return studentClassMapper.deleteByUserId(userId);
    }

    public int addStudentClass(StudentClass studentClass){
        return studentClassMapper.addStudentClass(studentClass);
    }

    public int batchDeleteStudent(List<Integer> idList){
        return studentClassMapper.batchDeleteStudent(idList);
    }
    public List<StudentClass> selectNumberByClassId(int classId){
        return studentClassMapper.selectNumberByClassId(classId);
    }
}
