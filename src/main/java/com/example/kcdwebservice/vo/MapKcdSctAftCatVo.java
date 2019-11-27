package com.example.kcdwebservice.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MapKcdSctAftCatVo {
    private String oriTpCd;
    private String mapVer;
    private String oriCd;
    private String sctId;
    private String attSctId;
    private String valgrpSctId;
    private String valSctId;
    private String mapStatCd;
    private String rvStatCd;
    private String dispOdr;
    private String udtDt;

    //JSON String 변수.
    private String valSctIdInfo;
}
