package com.cdut.kdchinese.service;

import com.cdut.kdchinese.pojo.User;
import com.cdut.kdchinese.pojo.UserMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: iRappelt
 * Date: 2020/03/26 17:44
 * Description:
 * Version: V1.0
 */
@Service
public class MainService {
    @Resource
    private UserMapper userMapper;

    public List<User> getAll(){
        return userMapper.selectAll();
    }


}
