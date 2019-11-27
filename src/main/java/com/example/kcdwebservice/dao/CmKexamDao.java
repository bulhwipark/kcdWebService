package com.example.kcdwebservice.dao;


import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.example.kcdwebservice.vo.CmKexamVo;
import com.example.kcdwebservice.vo.MapKcdSctVo;

import java.util.List;

@Repository
@Mapper
public interface CmKexamDao {

	List<CmKexamVo> selectAll();
    List<CmKexamVo> kexam_selectAll(CmKexamVo cmKexamVo);

    List<String> selectSynonym(String sctId);

    CmKexamVo selectKexCdInfo(String kexCd);
    //미구현
    List<CmKexamVo> kexam_selectMapping(CmKexamVo cmKexamVo);
    //미구현
    List<CmKexamVo> kexam_selectNoMapping(CmKexamVo cmKexamVo);
    
    ////////////////////////////////////////////////////////////
    String kexam_mappingTotalCnt(CmKexamVo cmKexamVo);
    String kexam_totalCnt(CmKexamVo cmKexamVo);
    String kexam_noMappingTotalCnt(CmKexamVo cmKexamVo);
    ////////////////////////////////////////////////////////////
    String kexam_kexCdMappingTotalCnt(CmKexamVo cmKexamVo);
    String kexam_kexCdTotalCnt(CmKexamVo cmKexamVo);
    String kexam_kexCdNoMappingTotalCnt(CmKexamVo cmKexamVo);
    ////////////////////////////////////////////////////////////
    
    Integer insertAutoMap3(MapKcdSctVo kcdSct);
}
