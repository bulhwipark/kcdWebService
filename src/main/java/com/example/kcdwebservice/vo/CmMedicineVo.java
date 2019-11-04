package com.example.kcdwebservice.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CmMedicineVo {
    private String prinipalCompCd;
    private String kdCd;
    private String drugNmKor;
    private String drugManuf;
    private String hiraAtcCd;
    private String hiraAtcNm;
    private String stdCd;
    private String drugNmEng;
    private String substanceNm;
    private double amount1;
    private String unit1;
    private String eftSubstNm;
    private double amount2;
    private String unit2;
    private double amount3;
    private String unit3;
    private String medDoseFrm;
    private String rtOfAdmin;

    private String udtDt;
    private String sctId;
    private String sctTerm;
    private String mapStatCd;
    private String cdDscrt;
    private String mapVer;
    private String medListOption;
    private String mapStatNm;
    private String limit;
    private String offset;
    
}
