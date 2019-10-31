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

    public List<CmMedicineVo> selectAll(CmMedicineVo cmMedicineVo) {
        return cmMediDao.selectAll(cmMedicineVo);
    }


    public void  selectAll(MapKcdSctVo mv) {
        cmMediDao.insertAutoMap2(mv);
    }



}
