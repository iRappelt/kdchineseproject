package com.cdut.kdchinese.converter;

import com.cdut.kdchinese.pojo.User;
import com.cdut.kdchinese.vo.StudentVO;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 用于类型转换：User2UserVO
 */
public class User2UserVO {
    public static StudentVO User2UserVO(User user){
        StudentVO studentVO = new StudentVO();
        BeanUtils.copyProperties(user,studentVO);
        return studentVO;
    }
    public static List<StudentVO> User2UserVO(List<User> userList){
        List<StudentVO> studentVOList = new ArrayList<>();
        return userList.stream().map(e->User2UserVO(e))
                    .collect(Collectors.toList());
    }
}
