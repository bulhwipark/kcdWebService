package com.example.kcdwebservice.service;

import java.util.List;

import com.example.kcdwebservice.dao.CmKcdDao;
import com.example.kcdwebservice.vo.CmKcdVo;

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

    public String mappingStatTotalCnt(String mappingStatus, CmKcdVo cmkcdVo) {
        String totalCnt = null;
        if(mappingStatus.equals("All")){
            totalCnt = cmKcdDao.totalAllCnt(cmkcdVo);
        }else if(mappingStatus.equals("Mapping")){
            totalCnt = cmKcdDao.totalMappingCnt(cmkcdVo);
        }else if(mappingStatus.equals("NotMapping")){
            totalCnt = cmKcdDao.totalNotMappingCnt(cmkcdVo);
        }else if(mappingStatus.equals("IcdNotMapping")){
            totalCnt = cmKcdDao.totalIcdNotMappingCnt(cmkcdVo);
        }
        return totalCnt;
    }

    public String kcdTotalCnt(String mappingStatus, CmKcdVo cmkcdVo) {
        String totalCnt = null;
        if(mappingStatus.equals("All")){
            totalCnt = cmKcdDao.totalKcdAllCnt(cmkcdVo);
        }else if(mappingStatus.equals("Mapping")){
            totalCnt = cmKcdDao.totalKcdMappingCnt(cmkcdVo);
        }else if(mappingStatus.equals("NotMapping")){
            totalCnt = cmKcdDao.totalKcdNotMappingCnt(cmkcdVo);
        }else if(mappingStatus.equals("IcdNotMapping")){
            totalCnt = cmKcdDao.totalKcdIcdNotMappingCnt(cmkcdVo);
        }
        return totalCnt;
    }

    public List<CmKcdVo> selectIcdNotMapping(CmKcdVo cmKcdVo) {
        return cmKcdDao.select_icdNotMapping(cmKcdVo);
    }
}
