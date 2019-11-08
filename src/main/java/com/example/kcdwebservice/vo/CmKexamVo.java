package com.example.kcdwebservice.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CmKexamVo {
    private String kexCd;
    private String kexTpCd;
    private String kexGrpCd;
    private String kexKor;
    private String kexEng;
    private String kexPrice;

    private String limit;
    private String offset;
}
