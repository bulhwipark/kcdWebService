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
    List<CmMedicineVo> selectCAll();
    
    List<CmMedicineVo> medi_selectAll(CmMedicineVo cmMedicineVo);
    
    void  insertAutoMap2(MapKcdSctVo mvo);

    List<CmMedicineVo> medi_selectMapping(CmMedicineVo cmMedicineVo);

    List<CmMedicineVo> medi_selectNoMapping(CmMedicineVo cmMedicineVo);

    String medi_totalCnt(CmMedicineVo cmMedicineVo);

    String medi_mappingTotalCnt(CmMedicineVo cmMedicineVo);

    String medi_noMappingTotalCnt(CmMedicineVo cmMedicineVo);

    String medi_kdCdTotalCnt(CmMedicineVo cmMedicineVo);

    String medi_kdCdMappingTotalCnt(CmMedicineVo cmMedicineVo);

    String medi_kdCdNoMappingTotalCnt(CmMedicineVo cmMedicineVo);

    CmMedicineVo getMediInfo(String kdCd);

    List<CmMedicineVo> mediDetailList(String kdCd);
}
