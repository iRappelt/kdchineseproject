package com.cdut.kdchinese.pojo;

import java.util.List;

public interface ClassesMapper {

    int insertSelective(Classes classes);

    int updateByPrimaryKeySelective(Classes aclass);

    int deleteByPrimaryKey(Integer id);

    int batchDeleteByPrimaryKey(List<Integer> idList);

    List<Classes> selectByTeacherId(Integer id);

    Classes selectByClassName(String name);

    List<Classes> selectAll(int pageStart, int pageLimit, int teacherId);


}