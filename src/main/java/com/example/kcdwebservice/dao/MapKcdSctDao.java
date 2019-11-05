package com.example.kcdwebservice.dao;

import java.util.List;

import com.example.kcdwebservice.vo.MapKcdSctVo;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface MapKcdSctDao {
    List<MapKcdSctVo> selectKcdCdList(String kcdCd);
    void  insertAutoMap1(MapKcdSctVo mvo);
    
    void insertMapKcdSctInfo(MapKcdSctVo mapKcdSctVo);

    void insertMapKcdSctInfo_medi(MapKcdSctVo mapKcdSctVo);

    void deleteMapKcdSctInfo(MapKcdSctVo mapKcdSctVo);

    void deleteMapKcdSctInfo_medi(MapKcdSctVo mapKcdSctVo);

}




