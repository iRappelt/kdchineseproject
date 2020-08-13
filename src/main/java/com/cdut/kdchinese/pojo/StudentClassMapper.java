package com.cdut.kdchinese.pojo;

import java.util.List;

public interface StudentClassMapper {

    List<StudentClass> selectByClassId(int pageStart, int pageLimit, int classId);

    int deleteByClassId(int classId);

    int deleteByUserId(int userId);

    int addStudentClass(StudentClass studentClass);

    int batchDeleteStudent(List<Integer> idList);

    List<StudentClass> selectNumberByClassId(int classId);

}
