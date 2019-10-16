package com.example.kcdwebservice.dao;

import com.example.kcdwebservice.vo.MapKcdSctVo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface MapKcdSctDao {
    List<MapKcdSctVo> selectKcdCdList(String kcdCd);
    void  insertAutoMap1(MapKcdSctVo mvo);
}




