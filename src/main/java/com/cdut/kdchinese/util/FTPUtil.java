package com.cdut.kdchinese.util;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created with IntelliJ IDEA.
 * User: iRappelt
 * Date: 2020/04/01 20:27
 * Description:  FTP上传文件工具类
 * Version: V1.0
 */
public class FTPUtil {

    /**
     * 建立ftp连接对象
     * @param host 服务器主机名或ip
     * @param port 服务器端口
     * @param username 服务器用户名
     * @param password 服务器用户密码
     * @return ftp连接对象
     */
    private static FTPClient getFtpClient(String host, int port, String username, String password) {
        //1.声明FTPClient对象 需导入commons-net的jar包
        FTPClient ftp = null;
        try {
            //2.创建FTPClient对象
            ftp = new FTPClient();
            //3.定义响应状态码
            int reply;
            //5.连接FTP服务器
            ftp.connect(host, port);// 如果采用默认端口，可以使用ftp.connect(host)的方式直接连接FTP服务器
            //6.登录ftp服务器
            ftp.login(username, password);
            //7.获得响应码
            reply = ftp.getReplyCode();
            //System.out.println(reply);
            if (!FTPReply.isPositiveCompletion(reply)) {
                // 8.如果返回状态码不在 200 ~ 300 则认为连接失败
                ftp.disconnect();
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ftp;
    }


    /**
     * 关闭ftp的连接
     * @param ftp ftp连接对象
     */
    private static void closeFtpClient(FTPClient ftp) {
        if (ftp != null) {
            try {
                ftp.logout();
                if (ftp.isConnected()) {
                    ftp.disconnect();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


        /**
         * Description: 向FTP服务器上传文件
         *
         * @param host     FTP服务器ip
         * @param port     FTP服务器端口
         * @param username FTP登录账号
         * @param password FTP登录密码
         * @param basePath FTP服务器基础目录,/home/ftpuser/EmbraceRegiment/images
         * @param filePath FTP服务器文件存放路径。例如分为用户头像文件夹：/usericon/ 总路径为：basePath+filePath
         * @param filename 上传到FTP服务器上的文件名
         * @param input    输入流
         * @return 成功返回true，否则返回false
         */
        public static boolean uploadFile (String host,int port, String username, String password, String basePath,
                String filePath, String filename, InputStream input){
            //1.确定返回标志
            boolean result = false;
            //2.创建FTPClient对象
            FTPClient ftp = FTPUtil.getFtpClient(host, port, username, password);
            if (ftp == null) {
                throw new RuntimeException("ftp为null");
            }
            try {
                //3.切换到上传目录
                if (!ftp.changeWorkingDirectory(basePath + filePath)) {
                    //4.如果目录不存在创建目录
                    String[] dirs = filePath.split("/");
                    String tempPath = basePath;
                    for (String dir : dirs) {
                        if (null == dir || "".equals(dir)) {
                            continue;
                        }
                        tempPath += "/" + dir;
//                        System.out.println(tempPath);
                        if (!ftp.changeWorkingDirectory(tempPath)) {
                            if (!ftp.makeDirectory(tempPath)) {
                                throw new RuntimeException("ftp创建文件夹失败");
                            } else {
                                ftp.changeWorkingDirectory(tempPath);
                            }
                        }
                    }
                }
                //4.设置为被动模式 （上传到服务器时，取消这个注释）
//                ftp.enterLocalPassiveMode();
                // 取消远程验证（上传到服务器时，取消这个注释）
//                ftp.setRemoteVerificationEnabled(false);
                //5.设置上传文件的类型为二进制类型
                ftp.setFileType(FTP.BINARY_FILE_TYPE);
                //6.上传文件
                if (!ftp.storeFile(filename, input)) {
                    throw new RuntimeException("存储文件失败！-----------");
                }
                //7.关闭流
                input.close();
                result = true;
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException(e.getMessage());
            } finally {
                //8.登出ftp
                FTPUtil.closeFtpClient(ftp);
            }
            return result;
        }


    /**
     * 删除ftp服务器上的文件
     * @param host 服务器主机
     * @param port 服务器端口
     * @param username ftp用户名
     * @param password ftp用户密码
     * @param basePath 服务器基础文件目录
     * @param filePath 中间文件目录
     * @param filename 文件名
     * @return 删除成功返回true，否则返回false
     */
        public static boolean deleteFile (String host,int port, String username, String password, String basePath,
                                          String filePath, String filename){
            FTPClient ftp = FTPUtil.getFtpClient(host, port, username, password);
            if(ftp==null){
                return false;
            }
            try {
                //切换FTP目录
                if(!ftp.changeWorkingDirectory(basePath+filePath)){
                    return false;
                }
                //删除文件
                ftp.dele(filename);
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                //关闭ftp连接
                FTPUtil.closeFtpClient(ftp);
            }
            return true;
        }

    }
