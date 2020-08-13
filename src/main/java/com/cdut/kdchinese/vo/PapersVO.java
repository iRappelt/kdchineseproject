package com.cdut.kdchinese.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class PapersVO {
    @JsonProperty("id")
    private Integer pid;
    @JsonProperty("name")
    private String paper_name;
    @JsonProperty("des")
    private String paper_des;
    @JsonProperty("date")
    private String paper_date;
    private Integer publish;
}
