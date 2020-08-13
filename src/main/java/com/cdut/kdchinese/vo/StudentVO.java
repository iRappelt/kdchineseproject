package com.cdut.kdchinese.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * 学生表VO:用于转换为返回类型
 */
@Data
public class StudentVO {
    @JsonProperty("id")
    private Integer uid;
    @JsonProperty("name")
    private String username;
    @JsonProperty("gender")
    private String sex;
}
