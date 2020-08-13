package com.cdut.kdchinese.converter;

import com.cdut.kdchinese.pojo.Classes;
import com.cdut.kdchinese.vo.ClassesVO;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 用于类型转换
 */
public class Classes2ClassVO {
    public static ClassesVO convert(Classes aclass){
        ClassesVO classesVO = new ClassesVO();
        BeanUtils.copyProperties(aclass, classesVO);
        return classesVO;
    }
    public static List<ClassesVO> convert(List<Classes> classes){
        return classes.stream().map(e -> convert(e))
                .collect(Collectors.toList());
    }
}
