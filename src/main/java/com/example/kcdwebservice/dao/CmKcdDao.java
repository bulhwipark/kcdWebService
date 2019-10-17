package com.example.kcdwebservice.dao;

import com.example.kcdwebservice.vo.CmKcdVo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface CmKcdDao {
    List<CmKcdVo> selectAll(CmKcdVo cmKcdVo);
    List<CmKcdVo> select_mapping(CmKcdVo cmKcdVo);
    List<CmKcdVo> select_not_mapping(CmKcdVo cmKcdVo);

    CmKcdVo selectKcdCdInfo(String kcdCd);

    String kcdTotalAllCnt();

    String totalAllCnt(String mapVer);

    String totalMappingCnt(String mapVer);

    String totalNotMappingCnt(String mapVer);
}
