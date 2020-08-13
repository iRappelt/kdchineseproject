package com.cdut.kdchinese.pojo;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Date: 2020/03/26 17:46
 * Description:
 * Version: V1.0
 * @author lxj
 */
public class MistakePinyin {
    private Integer id;
    private String word;
    private String pinyin;
    private String title;
    private String word_grade;
    private Integer frequency;
    private Integer user_id;
    private List<Integer> ids;

    public List<Integer> getIds() {
        return ids;
    }

    public void setIds(List<Integer> ids) {
        this.ids = ids;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getWord_grade() {
        return word_grade;
    }

    public void setWord_grade(String word_grade) {
        this.word_grade = word_grade;
    }

    public Integer getFrequency() {
        return frequency;
    }

    public void setFrequency(Integer frequency) {
        this.frequency = frequency;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    @Override
    public String toString() {
        return "MistakePinyin{" +
                "id=" + id +
                ", word='" + word + '\'' +
                ", pinyin='" + pinyin + '\'' +
                ", title='" + title + '\'' +
                ", word_grade='" + word_grade + '\'' +
                ", frequency=" + frequency +
                ", user_id=" + user_id +
                ", ids=" + ids +
                '}';
    }
}
