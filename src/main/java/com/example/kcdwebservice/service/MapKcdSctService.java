package com.example.kcdwebservice.service;

import com.example.kcdwebservice.dao.MapKcdSctDao;
import com.example.kcdwebservice.vo.MapKcdSctVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class MapKcdSctService {

    @Autowired
    MapKcdSctDao mapKcdSctDao;

    public List<MapKcdSctVo> selectKcdCdList(String kcdCd) {
        return mapKcdSctDao.selectKcdCdList(kcdCd);
    }

    public void insertMapKcdSctInfo(MapKcdSctVo mapKcdSctVo) {
        List<String>sctIdList = Arrays.asList(mapKcdSctVo.getSctId().split(","));
        for(int i = 0; i<sctIdList.size(); i++){
            mapKcdSctVo.setSctId(sctIdList.get(i));
            mapKcdSctDao.insertMapKcdSctInfo(mapKcdSctVo);
        }
    }

    public void deleteMapKcdSctInfo(MapKcdSctVo mapKcdSctVo) {
        List<String>sctIdList = Arrays.asList(mapKcdSctVo.getSctId().split(","));
        for(int i = 0; i<sctIdList.size(); i++){
            mapKcdSctVo.setSctId(sctIdList.get(i));
            mapKcdSctDao.deleteMapKcdSctInfo(mapKcdSctVo);
        }
    }
}
