package com.example.kcdwebservice.dao;

import java.util.List;

import com.example.kcdwebservice.vo.CmKcdVo;
import com.example.kcdwebservice.vo.CmMedicineVo;
import com.example.kcdwebservice.vo.MapKcdSctVo;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.ResultHandler;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface CmMediDao {
    List<CmMedicineVo> selectAll();
    void  insertAutoMap2(MapKcdSctVo mvo);
    
}
