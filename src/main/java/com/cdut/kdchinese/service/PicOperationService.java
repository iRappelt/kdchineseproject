package com.cdut.kdchinese.service;

import com.cdut.kdchinese.util.FTPUtil;
import com.cdut.kdchinese.util.Result;
import com.cdut.kdchinese.util.ResultCode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 * User: iRappelt
 * Date: 2020/04/02 19:54
 * Description:
 * Version: V1.0
 */
@Service
public class PicOperationService {
    /**
     * ftp服务器主机
     */
    @Value("${FTP.ADDRESS}")
    private String host;

    /**
     * ftp服务器端口
     */
    @Value("${FTP.PORT}")
    private int port;

    /**
     * 登录ftp账号
     */
    @Value("${FTP.USERNAME}")
    private String username;

    /**
     * 登录ftp密码
     */
    @Value("${FTP.PASSWORD}")
    private String password;

    /**
     * 图片存储到服务器上的主目录
     */
    @Value("${FTP.BASEPATH}")
    private String basePath;

    /**
     * 访问图片的基础URL
     */
    @Value("${IMAGE.BASEURL}")
    private String baseUrl;


    /**
     * 上传文件到ftp服务器
     * @param uploadFile 前端上传的文件对象
     * @param directory 保存到服务器上的中间目录
     * @return map信息
     */
    public Result fileUpload(MultipartFile uploadFile, String directory){
            //声明存储对对象
            Map<String,Object> map = new HashMap<>(16);
        try {
            //获得上传的图片原始名
            String oldName = uploadFile.getOriginalFilename();
            //使用Java的uuid生成新的唯一图片名
            String imguuid = UUID.randomUUID().toString().trim().replaceAll("-", "");
            String newName = imguuid+oldName.substring(oldName.lastIndexOf("."));
            //设置访问图片的完整url
            //String imgUrl = baseUrl+directory+"/"+newName;
            //将图片上传到服务器
            //获取图片文件流
            InputStream is = uploadFile.getInputStream();
            //调用FTPUtils工具类进行文件上传
            boolean result = FTPUtil.uploadFile(host,port,username,password,basePath, directory,newName,is);
            if(result){
                //上传成功
                return Result.success(newName);
            }else{
                //上传失败
                return Result.failure(ResultCode.AVATAR_UPLOAD_ERROR);
            }
        }catch (IOException e){
            return Result.failure(ResultCode.AVATAR_UPLOAD_FILE_STREAM_ERROR);
        }
    }

    /**
     * 删除ftp服务器上的文件
     * @param filename 文件名
     * @param directory 文件的中间文件夹
     * @return map信息
     */
    public Result fileDelete(String filename, String directory){
        //调用FTPUtils工具类进行文件删除
        boolean result = FTPUtil.deleteFile(host,port,username,password,basePath,directory,filename);
        if(result){
            //删除成功
            return Result.success();
        }else{
            //删除失败
            return Result.failure(ResultCode.AVATAR_DELETE_ERROR);
        }
    }
}
