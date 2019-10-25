package com.example.kcdwebservice.dao;

import java.util.List;

import com.example.kcdwebservice.vo.CmKcdVo;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.ResultHandler;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface CmKcdDao {
    List<CmKcdVo> selectAll(CmKcdVo cmKcdVo);
    List<CmKcdVo> select_mapping(CmKcdVo cmKcdVo);
    List<CmKcdVo> select_not_mapping(CmKcdVo cmKcdVo);
    List<CmKcdVo> select_icdNotMapping(CmKcdVo cmKcdVo);

    CmKcdVo selectKcdCdInfo(String kcdCd);

    String kcdTotalAllCnt();

    String totalKcdAllCnt(CmKcdVo mapKcdSctVo);
    String totalKcdMappingCnt(CmKcdVo mapKcdSctVo);
    String totalKcdNotMappingCnt(CmKcdVo mapKcdSctVo);
    String totalKcdIcdNotMappingCnt(CmKcdVo mapKcdSctVo);
    
    String totalAllCnt(CmKcdVo mapKcdSctVo);
    String totalMappingCnt(CmKcdVo mapKcdSctVo);
    String totalNotMappingCnt(CmKcdVo mapKcdSctVo);
    String totalIcdNotMappingCnt(CmKcdVo mapKcdSctVo);

}
