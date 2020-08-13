package com.cdut.kdchinese.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

public class ExaminationQuestion implements Serializable {
    @JsonProperty("qid")
    Integer qid;
    @JsonProperty("questionContent")
    String question_content;
    @JsonProperty("qA")
    String qA;
    @JsonProperty("qB")
    String qB;
    @JsonProperty("qC")
    String qC;
    @JsonProperty("qD")
    String qD;
    @JsonProperty("questionScore")
    Integer question_score;
    @JsonProperty("questionAnswer")
    String question_answer;
    @JsonProperty("questionType")
    Integer question_type;
    Integer paper_id;
    List<Integer> ids;//定义id集合
    private Papers papers;

    public Papers getPapers() {
        return papers;
    }

    public void setPapers(Papers papers) {
        this.papers = papers;
    }

    public List<Integer> getIds() {
        return ids;
    }

    public void setIds(List<Integer> ids) {
        this.ids = ids;
    }

    public Integer getQid() {
        return qid;
    }

    public void setQid(Integer qid) {
        this.qid = qid;
    }

    public String getQuestion_content() {
        return question_content;
    }

    public void setQuestion_content(String question_content) {
        this.question_content = question_content;
    }

    public String getqA() {
        return qA;
    }

    public void setqA(String qA) {
        this.qA = qA;
    }

    public String getqB() {
        return qB;
    }

    public void setqB(String qB) {
        this.qB = qB;
    }

    public String getqC() {
        return qC;
    }

    public void setqC(String qC) {
        this.qC = qC;
    }

    public String getqD() {
        return qD;
    }

    public void setqD(String qD) {
        this.qD = qD;
    }

    public Integer getQuestion_score() {
        return question_score;
    }

    public void setQuestion_score(Integer question_score) {
        this.question_score = question_score;
    }

    public String getQuestion_answer() {
        return question_answer;
    }

    public void setQuestion_answer(String question_answer) {
        this.question_answer = question_answer;
    }

    public Integer getQuestion_type() {
        return question_type;
    }

    public void setQuestion_type(Integer question_type) {
        this.question_type = question_type;
    }

    public Integer getPaper_id() {
        return paper_id;
    }

    public void setPaper_id(Integer paper_id) {
        this.paper_id = paper_id;
    }

    @Override
    public String toString() {
        return "ExaminationQuestion{" +
                "qid=" + qid +
                ", question_content='" + question_content + '\'' +
                ", qA='" + qA + '\'' +
                ", qB='" + qB + '\'' +
                ", qC='" + qC + '\'' +
                ", qD='" + qD + '\'' +
                ", question_score=" + question_score +
                ", question_answer='" + question_answer + '\'' +
                ", question_type=" + question_type +
                ", paper_id=" + paper_id +
                ", ids=" + ids +
                ", papers=" + papers +
                '}';
    }
}
