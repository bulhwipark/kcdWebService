package com.example.kcdwebservice.dao;

import java.util.List;

import com.example.kcdwebservice.vo.CmKcdVo;
import com.example.kcdwebservice.vo.MapKcdSctVo;

import org.apache.ibatis.annotations.Mapper;
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

    String totalAllCnt(MapKcdSctVo mapKcdSctVo);

    String totalMappingCnt(MapKcdSctVo mapKcdSctVo);

    String totalNotMappingCnt(MapKcdSctVo mapKcdSctVo);
    
    String totalIcdNotMappingCnt(MapKcdSctVo mapKcdSctVo);
    

}
