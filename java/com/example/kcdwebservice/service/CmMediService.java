package com.example.kcdwebservice.service;

import java.util.List;


import com.example.kcdwebservice.dao.CmMediDao;
import com.example.kcdwebservice.vo.CmMedicineVo;
import com.example.kcdwebservice.vo.MapKcdSctVo;

import org.apache.ibatis.session.ResultContext;
import org.apache.ibatis.session.ResultHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CmMediService {

    @Autowired
    CmMediDao cmMediDao;

    public void  selectAll(MapKcdSctVo mv) {
        cmMediDao.insertAutoMap2(mv);
    }

    public List<CmMedicineVo> medi_selectAll(CmMedicineVo cmMedicineVo) {
        return cmMediDao.medi_selectAll(cmMedicineVo);
    }

    public List<CmMedicineVo> medi_selectMapping(CmMedicineVo cmMedicineVo) {
        return cmMediDao.medi_selectMapping(cmMedicineVo);
    }

    public List<CmMedicineVo> medi_selectNoMapping(CmMedicineVo cmMedicineVo) {
        return cmMediDao.medi_selectNoMapping(cmMedicineVo);
    }

    public String medi_totalCnt(String mappingStatus, CmMedicineVo cmMedicineVo) {
        String totalCnt = null;
        if(mappingStatus.equals("All")){
            totalCnt = cmMediDao.medi_kdCdTotalCnt(cmMedicineVo);
        }else if(mappingStatus.equals("Mapping")){
            totalCnt = cmMediDao.medi_kdCdMappingTotalCnt(cmMedicineVo);
        }else if(mappingStatus.equals("NoMapping")){
            totalCnt = cmMediDao.medi_kdCdNoMappingTotalCnt(cmMedicineVo);
        }
        return totalCnt;
    }

    public String medi_mappingStatusTotalCnt(String mappingStatus, CmMedicineVo cmMedicineVo) {
        String totalCnt = null;
        if(mappingStatus.equals("All")){
            totalCnt = cmMediDao.medi_totalCnt(cmMedicineVo);
        }else if(mappingStatus.equals("Mapping")){
            totalCnt = cmMediDao.medi_mappingTotalCnt(cmMedicineVo);
        }else if(mappingStatus.equals("NoMapping")){
            totalCnt = cmMediDao.medi_noMappingTotalCnt(cmMedicineVo);
        }
        return totalCnt;
    }

    public CmMedicineVo getMediInfo(CmMedicineVo cmMedicineVo) {
        return cmMediDao.getMediInfo(cmMedicineVo);
    }

    public List<CmMedicineVo> mediDetailList(String kdCd) {
        return cmMediDao.mediDetailList(kdCd);
    }

    public List<CmMedicineVo> getMediInfoList(String kdCd) {
        return cmMediDao.getMediInfoList(kdCd);
    }
}
