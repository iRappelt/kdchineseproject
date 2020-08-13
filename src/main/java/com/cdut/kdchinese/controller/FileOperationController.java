package com.cdut.kdchinese.controller;

import com.cdut.kdchinese.pojo.User;
import com.cdut.kdchinese.service.PicOperationService;
import com.cdut.kdchinese.service.UserService;
import com.cdut.kdchinese.util.Result;
import com.cdut.kdchinese.util.ResultCode;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Date: 2020/04/02 21:10
 * Description:
 * Version: V1.0
 * @Author  iRappelt
 */
@Controller
public class FileOperationController {

    @Resource
    private PicOperationService picOperationService;

    @Resource
    private UserService userService;

    /**
     * 文件上传请求
     * @param img 图片对象
     * @return 上传成功与否的相关信息
     */
    @RequestMapping(value = "/avatar_upload", method = RequestMethod.POST)
    @ResponseBody
    public Result uploadFile(MultipartFile img, HttpServletRequest request){
        HttpSession session = request.getSession();
        User user = (User)session.getAttribute("user");
        // 定义文件上传中间目录
        String directory = "/users/avatar";
        // 先删除以前的图片
        String oldAvatar = user.getHeadimg();
        if(oldAvatar != null){
            Result result1 = picOperationService.fileDelete(oldAvatar, directory);
            if(result1.getCode()!=0){
                // 删除失败
                // 直接返回错误信息（service类已封装）
                return result1;
            }
        }
        // 图片上传
        Result result2 = picOperationService.fileUpload(img, directory);
        if(result2.getCode()!=0){
            // 如果上传失败
            // 直接返回错误信息（service类已封装）
            return result2;
        }
        // 如果上传成功
        // 更新用户头像url信息
        user.setHeadimg((String)result2.getData());
        int updateResult = userService.updUserByPKSelective(user);
        if(updateResult!=1){
            return Result.failure(ResultCode.USER_UPDATE_ERROR);
        }
        return Result.success();
    }


    /**
     * 文件删除请求
     * @param filename 文件名
     * @return 文件删除成功与否的信息
     */
    @RequestMapping(value = "/delete_file", method = RequestMethod.POST)
    @ResponseBody
    public Result deleteFile(String filename){
        //定义文件所在的中间目录
        String directory = "/users/avatar";
        //图片删除
        Result result = picOperationService.fileDelete(filename, directory);
        //如果删除失败
        if(result.getCode()!=0){
            //直接返回错误信息（service类已封装）
            return result;
        }
        //如果删除成功
        //其他业务

        return result;
    }

}
