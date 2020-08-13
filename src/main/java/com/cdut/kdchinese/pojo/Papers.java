package com.cdut.kdchinese.pojo;



import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Papers {
    @JsonProperty("id")
    Integer pid;
    @JsonProperty("name")
    String paper_name;
    @JsonProperty("des")
    String paper_des;
    @JsonProperty("date")
    String paper_date;
    @JsonProperty("publish")
    Integer publish;
    Integer class_id;
    List<Integer> ids;//定义id集合
    //一对多关系映射
    private List<ExaminationQuestion> examinationQuestions;

    public List<ExaminationQuestion> getExaminationQuestions() {
        return examinationQuestions;
    }

    public void setExaminationQuestions(List<ExaminationQuestion> examinationQuestions) {
        this.examinationQuestions = examinationQuestions;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getPaper_name() {
        return paper_name;
    }

    public void setPaper_name(String paper_name) {
        this.paper_name = paper_name;
    }

    public String getPaper_des() {
        return paper_des;
    }

    public void setPaper_des(String paper_des) {
        this.paper_des = paper_des;
    }

    public String getPaper_date() {
        return paper_date;
    }

    public void setPaper_date(String paper_date) {
        this.paper_date = paper_date;
    }

    public Integer getPublish() {
        return publish;
    }

    public void setPublish(Integer publish) {
        this.publish = publish;
    }

    public Integer getClass_id() {
        return class_id;
    }

    public void setClass_id(Integer class_id) {
        this.class_id = class_id;
    }

    public List<Integer> getIds() {
        return ids;
    }

    public void setIds(List<Integer> ids) {
        this.ids = ids;
    }

    @Override
    public String toString() {
        return "Papers{" +
                "pid=" + pid +
                ", paper_name='" + paper_name + '\'' +
                ", paper_des='" + paper_des + '\'' +
                ", paper_date='" + paper_date + '\'' +
                ", publish='" + publish + '\'' +
                ", class_id=" + class_id +
                ", ids=" + ids +
                '}';
    }
}
