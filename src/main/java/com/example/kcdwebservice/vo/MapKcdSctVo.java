package com.example.kcdwebservice.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MapKcdSctVo {

    private String oriCd;
    private String sctId;
    private String mapVer;
    private String mapStatCd;
    private String rvStatCd;
    private String dispOdr;
    private String udtDt;
    private String mapMemo;

    private String sctTerm;
    private String mapStatNm;

    private String afterMap;
    private String subAltKey="0";
}
