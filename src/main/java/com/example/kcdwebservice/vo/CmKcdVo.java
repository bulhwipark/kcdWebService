package com.example.kcdwebservice.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CmKcdVo {
    private String kcdCd;
    private String kcdEng;
    private String kcdKor;
    private String kcdMapStatCd;
    private String kcdRvStatCd;
    private String dispOdr;
    private String udtDt;
    private String sctId;

    private String limit;
    private String offset;
}
