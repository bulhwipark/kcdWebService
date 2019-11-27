package com.example.kcdwebservice.service;

import com.example.kcdwebservice.dao.MapKcdSctAftCatDao;
import com.example.kcdwebservice.dao.MapKcdSctDao;
import com.example.kcdwebservice.vo.MapKcdSctAftCatVo;
import com.example.kcdwebservice.vo.MapKcdSctVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class MapKcdSctService {

    @Autowired
    MapKcdSctDao mapKcdSctDao;
    @Autowired
    MapKcdSctAftCatDao mapKcdSctAftCatDao;

    public List<MapKcdSctVo> selectKcdCdList(String kcdCd) {
        return mapKcdSctDao.selectKcdCdList(kcdCd);
    }

    public void insertMapKcdSctInfo(MapKcdSctVo mapKcdSctVo) {
        List<String>sctIdList = Arrays.asList(mapKcdSctVo.getSctId().split(","));
        List<String>ruleCodeList = Arrays.asList(mapKcdSctVo.getMapMemo().split("_"));
        for(int i = 0; i<sctIdList.size(); i++){
            mapKcdSctVo.setSctId(sctIdList.get(i));
            mapKcdSctVo.setMapMemo(ruleCodeList.get(i));
            mapKcdSctDao.insertMapKcdSctInfo(mapKcdSctVo);
        }
    }

    public void insertMapKcdSctInfo_medi(MapKcdSctVo mapKcdSctVo) {
        List<String>sctIdList = Arrays.asList(mapKcdSctVo.getSctId().split(","));
        List<String>mapMemoList = Arrays.asList(mapKcdSctVo.getMapMemo().split("_"));
        for(int i = 0; i<sctIdList.size(); i++){
            mapKcdSctVo.setSctId(sctIdList.get(i));
            mapKcdSctVo.setMapMemo(
                    mapMemoList.get(i)
            );
            mapKcdSctDao.insertMapKcdSctInfo_medi(mapKcdSctVo);
        }
    }

    /**
     * kcd 삭제
     * @param mapKcdSctVo
     */
    public void deleteMapKcdSctInfo(MapKcdSctVo mapKcdSctVo) {
        List<String>sctIdList = Arrays.asList(mapKcdSctVo.getSctId().split(","));
        for(int i = 0; i<sctIdList.size(); i++){
            mapKcdSctVo.setSctId(sctIdList.get(i));
            MapKcdSctAftCatVo mapKcdSctAftCatVo = new MapKcdSctAftCatVo();
            mapKcdSctAftCatVo.setOriCd(mapKcdSctVo.getOriCd());
            mapKcdSctAftCatVo.setSctId(sctIdList.get(i));
            mapKcdSctDao.deleteMapKcdSctInfo(mapKcdSctVo);
            mapKcdSctAftCatDao.attValDelete(mapKcdSctAftCatVo);
        }
    }

    /**
     * medi 삭제
     * @param mapKcdSctVo
     */
    public void deleteMapKcdSctInfo_medi(MapKcdSctVo mapKcdSctVo) {
        List<String>sctIdList = Arrays.asList(mapKcdSctVo.getSctId().split(","));
        for(int i = 0; i<sctIdList.size(); i++){
            mapKcdSctVo.setSctId(sctIdList.get(i));
            MapKcdSctAftCatVo mapKcdSctAftCatVo = new MapKcdSctAftCatVo();
            mapKcdSctAftCatVo.setOriCd(mapKcdSctVo.getOriCd());
            mapKcdSctAftCatVo.setSctId(sctIdList.get(i));
            mapKcdSctDao.deleteMapKcdSctInfo_medi(mapKcdSctVo);
            mapKcdSctAftCatDao.attValDelete(mapKcdSctAftCatVo);
        }
    }


}
