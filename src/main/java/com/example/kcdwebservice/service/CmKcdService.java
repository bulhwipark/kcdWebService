package com.example.kcdwebservice.service;

import java.util.List;

import com.example.kcdwebservice.dao.CmKcdDao;
import com.example.kcdwebservice.vo.CmKcdVo;
import com.example.kcdwebservice.vo.MapKcdSctVo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CmKcdService {

    @Autowired
    CmKcdDao cmKcdDao;

    public List<CmKcdVo> selectAll(CmKcdVo cmKcdVo) {
        return cmKcdDao.selectAll(cmKcdVo);
    }

    public List<CmKcdVo> selectMapping(CmKcdVo cmKcdVo) {
        return cmKcdDao.select_mapping(cmKcdVo);
    }

    public List<CmKcdVo> selectNotMapping(CmKcdVo cmKcdVo) {
        return cmKcdDao.select_not_mapping(cmKcdVo);
    }

    public CmKcdVo selectKcdCdInfo(String kcdCd) {
        return cmKcdDao.selectKcdCdInfo(kcdCd);
    }

    public String mappingStatTotalCnt(String mappingStatus, MapKcdSctVo mapKcdSctVo) {
        String totalCnt = null;
        if(mappingStatus.equals("All")){
            totalCnt = cmKcdDao.totalAllCnt(mapKcdSctVo);
        }else if(mappingStatus.equals("Mapping")){
            totalCnt = cmKcdDao.totalMappingCnt(mapKcdSctVo);
        }else if(mappingStatus.equals("NotMapping")){
            totalCnt = cmKcdDao.totalNotMappingCnt(mapKcdSctVo);
        }else if(mappingStatus.equals("IcdNotMapping")){
            totalCnt = cmKcdDao.totalIcdNotMappingCnt(mapKcdSctVo);
        }
        return totalCnt;
    }

    public String kcdTotalCnt() {
        return cmKcdDao.kcdTotalAllCnt();
    }

    public List<CmKcdVo> selectIcdNotMapping(CmKcdVo cmKcdVo) {
        return cmKcdDao.select_icdNotMapping(cmKcdVo);
    }
}
