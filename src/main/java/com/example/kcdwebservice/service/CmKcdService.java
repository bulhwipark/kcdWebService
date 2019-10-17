package com.example.kcdwebservice.service;

import com.example.kcdwebservice.dao.CmKcdDao;
import com.example.kcdwebservice.vo.CmKcdVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public String mappingStatTotalCnt(String mappingStatus, String mapVer) {
        String totalCnt = null;
        if(mappingStatus.equals("All")){
            totalCnt = cmKcdDao.totalAllCnt(mapVer);
        }else if(mappingStatus.equals("Mapping")){
            totalCnt = cmKcdDao.totalMappingCnt(mapVer);
        }else{
            totalCnt = cmKcdDao.totalNotMappingCnt(mapVer);
        }
        return totalCnt;
    }

    public String kcdTotalCnt() {
        return cmKcdDao.kcdTotalAllCnt();
    }
}
