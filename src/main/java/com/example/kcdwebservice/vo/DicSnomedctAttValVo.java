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
    private String cmSctTerm2;
    private String cmDispOdr;
    private String cmUdtDt;

    private String destinationId;
    private String dt;
    private String sourceId;
    private String st;

    //쿼리용
    private String sctId;

}
