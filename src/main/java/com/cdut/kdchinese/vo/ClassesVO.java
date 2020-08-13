package com.cdut.kdchinese.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * 班级表VO：用于转换
 */
@Data
public class ClassesVO {
    @JsonProperty("id")
    private Integer classId;
    @JsonProperty("name")
    private String className;
    @JsonProperty("code")
    private String classCode;
    @JsonProperty("number")
    private Integer number;
    @JsonProperty("date")
    private String classDate;

}
