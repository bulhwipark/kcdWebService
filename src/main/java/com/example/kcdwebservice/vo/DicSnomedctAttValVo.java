package com.example.kcdwebservice.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class DicSnomedctAttValVo {

    private String attSctId;
    private String valSctId;
    private String sctTpCd;
    private String avDispOdr;
    private String avUdtDt;
    private String cmSctTerm;
    private String cmDispOdr;
    private String cmUdtDt;

    //쿼리용
    private String sctId;

}
