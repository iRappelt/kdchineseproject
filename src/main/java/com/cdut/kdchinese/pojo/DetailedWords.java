package com.cdut.kdchinese.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * Copyright (C), 2020-2020, 快对语文
 * FileName: ExerciseService
 * Date:     2020/6/20 10:58
 * Description: 汉字表对应pojo
 * @Author  lfc
 */
@Data
public class DetailedWords {

    /** Id */
    @JsonProperty("id")
    private Integer wordId;
    @JsonProperty("name")
    private String wordName;
    @JsonProperty("pinyin")
    private String wordPinYin;
    @JsonProperty("pianpang")
    private String wordPianPang;
    @JsonProperty("bihuanum")
    private Integer wordBiHuaNum;
    @JsonProperty("bishun")
    private String wordBiShun;
    @JsonProperty("bishuncode")
    private String wordBiShunCode;
    /** 词组*/
    @JsonProperty("phrase")
    private String wordPhrase;
    @JsonProperty("explain")
    private String wordExplain;
    @JsonProperty("gif")
    private String wordGif;
    @JsonProperty("grade")
    private String wordGrade;

}
