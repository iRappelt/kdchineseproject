package com.cdut.kdchinese.util;

/**
 * Created with IntelliJ IDEA.
 * Date: 2020/06/04 21:44
 * Description:
 * Version: V1.0
 * @author iRappelt
 */
public enum ResultCode {

    /**
     * 0：请求成功
     */
    SUCCESS(0, "成功"),

    /* **************************参数错误时，状态码范围在：1001~1999************************* */

    /**
     * 1001：参数无效
     */
    PARAM_IS_INVALID(1001,"参数无效"),
    /**
     * 1002：参数为空
     */
    PARAM_IS_BLANK(1002, "参数为空"),
    /**
     * 1003：参数类型错误
     */
    PARAM_TYPE_ERROR(1003, "参数类型错误"),
    /**
     * 1004：参数缺失
     */
    PARAM_NOT_COMPLETE(1004, "参数缺失"),

    /* *****************************用户错误时，状态码范围在：2001~2999*********************** */

    /**
     * 2001：用户不存在
     */
    USER_NOT_EXIST(2001, "用户不存在"),

    /**
     * 2002：用户登录时用户名或密码错误
     */
    USER_LOGIN_ERROR(2002, "用户名或密码错误"),

    /**
     * 2003：用户登录验证码有误
     */
    USER_VERIFICATION_CODE_ERROR(2003, "用户登录验证码有误"),
    /**
     * 2004：用户登录手机号有误
     */
    USER_TELEPHONE_ERROR(2004, "用户登录手机号有误"),
    /**
     * 2005：用户登录验证码已过期
     */
    USER_VERIFICATION_CODE_EXPIRED(2005, "用户登录验证码已过期"),
    /**
     * 2006：用户信息插入数据库失败
     */
    USER_INSERT_ERROR(2006, "用户插入数据库失败"),
    /**
     * 2007：用户信息从数据库删除失败
     */
    USER_DELETE_ERROR(2007, "用户从数据库删除失败"),
    /**
     * 2008：用户信息更新到数据库失败
     */
    USER_UPDATE_ERROR(2008, "用户更新到数据库失败"),
    /**
     * 2009：发送短信验证码失败
     */
    USER_SEND_VERIFICATIO_CODE_ERROR(2009, "发送短信验证码失败"),
    /**
     * 2010：获取session中的用户数据失败
     */
    USER_SESSION_ERROR(2010, "获取session中的用户数据失败"),
    /**
     * 2011：注册时用户已经存在
     */
    USER_HAS_EXIST(2011, "用户已存在"),
    /**
     * 2022：用户电话号码已经被注册
     */
    USER_PHONE_REGISTED(2022, "电话号码已被注册"),
    /**
     * 2023：用户注册失败
     */
    USER_REGISTER_FAILED(2023, "用户注册失败"),

    /* ******************习题表错误时，状态码范围在：3001~3999********************* */

    /**
     * 3001:习题添加失败
     */
    EXERCISE_PINYIN_ADD_ERROR(3001, "习题添加失败"),
    /**
     * 3002:习题更新失败
     */
    EXERCISE_PINYIN_UPDATE_ERROR(3002, "习题更新失败"),

    /**
     * 3003:习题删除失败
     */
    EXERCISE_PINYIN_DELETE_ERROR(3003, "习题删除失败"),

    /**
     * 3004:习题批量删除失败
     */
    EXERCISE_PINYIN_BATCHDELETE_ERROR(3004, "习题批量删除失败"),

    /**
     * 3005:习题查询出错
     */
    EXERCISE_PINYIN_SELECT_ERROR(3005, "习题查询失败"),

    /* ******************错题表错误时，状态码范围在：4001~4999********************* */

    /**
     * 4000：添加拼音错题失败
     */
    PinYinError_ADD_ERROR(4000, "添加拼音错题失败"),
    /**
     * 4001：拼音错题记录修改错误
     */
    PinYinError_UPDATE_ERROR(4001,"拼音错题记录修改错误"),
    /**
     * 4002：拼音错题删除错误
     */
    PinYinError_DELETE_ERROR(4002,"拼音错题删除错误"),
    /**
     * 4003：批量删除拼音错题错误
     */
    Batch_PinYinError_DELETE_ERROR(4003,"批量删除拼音错题错误"),
    /**
     * 4004：添加汉字错题失败
     */
    WordError_ADD_ERROR(4004,"添加汉字错题失败"),
    /**
     * 4005：汉字错题记录修改错误
     */
    WordError_UPDATE_ERROR(4005,"汉字错题记录修改错误"),
    /**
     * 4006：汉字错题删除错误
     */
    WordError_DELETE_ERROR(4006,"汉字错题删除错误"),
    /**
     * 4007：批量删除汉字错题错误
     */
    Batch_WordError_DELETE_ERROR(4007,"批量删除汉字错题错误"),

    /* ******************汉字表错误时，状态码范围在：5001~5999********************* */

    /**
     * 5001：汉字信息从数据库删除失败
     */
    WORD_DELETE_ERROR(5001,"汉字信息从数据库删除失败"),
    /**
     * 5002：汉字已存在
     */
    WORD_HAS_EXIST(5002,"汉字已存在"),
    /**
     * 5003：汉字插入数据库失败
     */
    WORD_INSERT_ERROR(5003,"汉字插入数据库失败"),
    /**
     * 5004：汉字不存在
     */
    WORD_NOT_EXIST(5004, "汉字不存在"),
    /**
     * 5005：汉字更新到数据库失败
     */
    WORD_UPDATE_ERROR(5005, "汉字更新到数据库失败"),

    /* ******************头像上传错误时，状态码范围在：6001~6999********************* */

    /**
     * 6001:头像上传到服务器上失败
     */
    AVATAR_UPLOAD_ERROR(6001,"头像上传到服务器上失败"),

    /**
     * 6002：上传头像时获取文件流异常
     */
    AVATAR_UPLOAD_FILE_STREAM_ERROR(6002, "上传头像时获取文件流异常"),

    /**
     * 6003：文件从服务器上删除失败
     */
    AVATAR_DELETE_ERROR(6003, "文件从服务器上删除失败"),


    /* ******************班级表错误时，状态码范围在：7001~7999********************* */

    /**
     * 7001：班级已存在
     */
    CLASS_HAS_EXIST(7001,"班级已存在"),

    /**
     * 7002：班级插入数据库失败
     */
    CLASS_INSERT_ERROR(7002,"班级插入数据库失败"),

    /**
     * 7002：班级信息从数据库删除失败
     */
    CLASS_DELETE_ERROR(7002,"班级信息从数据库删除失败"),

    /**
     * 7003：班级不存在
     */
    CLASS_NOT_EXIST(7003, "班级不存在"),

    /**
     * 7004：班级更新到数据库失败
     */
    CLASS_UPDATE_ERROR(7004, "班级更新到数据库失败"),

    /**
     * 7005：班级信息获取失败
     */
    CLASS_GET_ERROR(7005,"班级信息获取失败"),

    /**
     * 7006：班级批量删除失败
     */
    CLASS_BATCHDELETE_ERROR(7006,"班级批量删除失败"),

    /* ******************学生表错误时，状态码范围在：8001~8999********************* */

    /**
     * 7006：学生信息查找失败
     */
    SHOW_STUDENT_ERROR(2047,"学生信息查找失败"),

    /**
     * 7006：批量删除学生失败
     */
    STUDENT_BATCHDELETE_ERROR(2048,"批量删除学生失败"),

    /**
     * 7006：学生删除失败
     */
    STUDENT_DELETE_ERROR(2049,"学生删除失败"),

    /**
     * 7006：学生添加失败
     */
    STUDENT_INSERT_ERROR(2050,"学生添加失败"),

    /* ******************试题和题目表错误时，状态码范围在：9001~9999********************* */


    TEL_NOT_EXIST(9001,"手机号不存在"),
    /**
     * 4008:分页查找试卷信息失败
     */
    Papers_FIND_ERROR(9002,"无相关试卷信息"),

    /**
     * 4009:通过id删除试卷错误
     */
    Paper_DELETE_ERROR(9003,"试卷删除错误"),

    /**
     * 4010:通过id集合批量删除试卷错误
     */
    Batch_Papers_DELETE_ERROR(9004,"批量删除试卷失败"),

    /**
     * 4011：试卷无试题
     */
    No_Question(9005,"试卷无试题"),

    /**
     * 4012：试卷发布失败
     */

    Publish_Exam(9006,"试卷发布失败"),

    /**
     * 4013:添加试卷失败
     */

    Papers_ADD_Error(9007,"添加试卷失败"),

    /**
     * 4014:试题插入失败
     */
    Question_ADD_Error(9008,"添加试卷失败");




    /**
     * 状态码
     */
    private Integer code;
    /**
     * 状态信息
     */
    private String message;

    /**
     * ResultCode构造器
     * @param code 状态码
     * @param message 状态信息
     */
    ResultCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }

}
