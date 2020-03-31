package com.cdut.kdchinese.controller;

import com.cdut.kdchinese.pojo.User;
import com.cdut.kdchinese.service.MainService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: iRappelt
 * Date: 2020/03/26 17:46
 * Description:
 * Version: V1.0
 */
@Controller
public class MainController {
    @Resource
    private MainService mainService;

    @RequestMapping(value = "/showuser", method = RequestMethod.GET)
    @ResponseBody
    public List<User> showUser(){
        return mainService.getAll();
    }
}
