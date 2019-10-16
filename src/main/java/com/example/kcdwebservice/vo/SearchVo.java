package com.example.kcdwebservice.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SearchVo {
    private String ecl;
    private String disorder;
    private String clinicalFinding;
    private String term;
}
