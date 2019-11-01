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
}
