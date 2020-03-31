package com.cdut.kdchinese.pojo;

import java.util.Date;

public class User {
      
    private Integer uid;
    private String telephone;
    private String password;
    private String identity;
    private String username;
    private String headimg;
    private String registdate;
    private String sex;
    private String grade;


    public Integer getUid() {
        return uid;
    }
    public void setUid(Integer uid) {
        this.uid = uid;
    }
    public String getTelephone() {
        return telephone;
    }
    public void setTelephone(String telephone) {
        this.telephone = telephone == null ? null : telephone.trim();
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }
    public String getIdentity() {
        return identity;
    }
    public void setIdentity(String identity) {
        this.identity = identity == null ? null : identity.trim();
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }
    public String getHeadimg() {
        return headimg;
    }
    public void setHeadimg(String headimg) {
        this.headimg = headimg == null ? null : headimg.trim();
    }
    public String getRegistdate() {
        return registdate;
    }
    public void setRegistdate(String registdate) {
        this.registdate = registdate;
    }
    public String getSex() {
        return sex;
    }
    public void setSex(String sex) {
        this.sex = sex == null ? null : sex.trim();
    }
    public String getGrade() {
        return grade;
    }
    public void setGrade(String grade) {
        this.grade = grade == null ? null : grade.trim();
    }

    @Override
    public String toString() {
        return "User{" +
                "uid=" + uid +
                ", telephone='" + telephone + '\'' +
                ", password='" + password + '\'' +
                ", identity='" + identity + '\'' +
                ", username='" + username + '\'' +
                ", headimg='" + headimg + '\'' +
                ", registdate=" + registdate +
                ", sex='" + sex + '\'' +
                ", grade='" + grade + '\'' +
                '}';
    }
}