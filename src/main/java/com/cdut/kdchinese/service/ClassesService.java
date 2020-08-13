package com.cdut.kdchinese.service;

import com.cdut.kdchinese.pojo.Classes;
import com.cdut.kdchinese.pojo.ClassesMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ClassesService {
    @Resource
    private ClassesMapper classesMapper;

    public int insSelective(Classes classes){
        return classesMapper.insertSelective(classes);
    }

    public List<Classes> selAll(int pageStart, int pageLimit, int teacherId){
        return classesMapper.selectAll(pageStart,pageLimit,teacherId);
    }

    public List<Classes> selByTI(Integer teacherId){
        return classesMapper.selectByTeacherId(teacherId);
    }

    public Classes selByCN(String className){return classesMapper.selectByClassName(className);}

    public int updByPKSelective(Classes aclass){
        return classesMapper.updateByPrimaryKeySelective(aclass);
    }

    public int delByPK(int classId){
        return classesMapper.deleteByPrimaryKey(classId);
    }

    public int delByPKList(List list){
        return classesMapper.batchDeleteByPrimaryKey(list);
    }


}
