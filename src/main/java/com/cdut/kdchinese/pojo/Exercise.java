package com.cdut.kdchinese.pojo;

public class Exercise {

    private Integer wordId;

    private String wordName;

    private String wordPinyin;

    private String wordShengmu;

    private String wordYunmu;
    private String a;

    public Integer getWordId() {
        return wordId;
    }

    public void setWordId(Integer wordId) {
        this.wordId = wordId;
    }

    public String getWordName() {
        return wordName;
    }

    public void setWordName(String wordName) {
        this.wordName = wordName == null ? null : wordName.trim();
    }

    public String getWordPinyin() {
        return wordPinyin;
    }

    public void setWordPinyin(String wordPinyin) {
        this.wordPinyin = wordPinyin == null ? null : wordPinyin.trim();
    }

    public String getWordShengmu() {
        return wordShengmu;
    }

    public void setWordShengmu(String wordShengmu) {
        this.wordShengmu = wordShengmu == null ? null : wordShengmu.trim();
    }

    public String getWordYunmu() {
        return wordYunmu;
    }

    public void setWordYunmu(String wordYunmu) {
        this.wordYunmu = wordYunmu == null ? null : wordYunmu.trim();
    }

    public String getA() {
        return a;
    }

    public void setA(String a) {
        this.a = a == null ? null : a.trim();
    }

    @Override
    public String toString() {
        return "Exercise{" +
                "wordId=" + wordId +
                ", wordName='" + wordName + '\'' +
                ", wordPinyin='" + wordPinyin + '\'' +
                ", wordShengmu='" + wordShengmu + '\'' +
                ", wordYunmu='" + wordYunmu + '\'' +
                ", a='" + a + '\'' +
                '}';
    }
}