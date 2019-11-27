package com.example.kcdwebservice.service;

import com.example.kcdwebservice.dao.MapKcdSctAftCatDao;
import com.example.kcdwebservice.vo.MapKcdSctAftCatVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MapKcdSctAftCatService {
    @Autowired
    private MapKcdSctAftCatDao mapKcdSctAftCatDao;

    public void attValInsert(MapKcdSctAftCatVo mapKcdSctAftCatVo, String attrParam, String valParam, String valGrpSctIdParam) {
        String[] attrArray = attrParam.split(",");
        String[] valArray = valParam.split(",");
        String[] valGrpSctIdArray = valGrpSctIdParam.split(",");

        for(int i = 0; i<attrArray.length; i++){
            mapKcdSctAftCatVo.setAttSctId(attrArray[i]);
            mapKcdSctAftCatVo.setValSctId(valArray[i]);
            mapKcdSctAftCatVo.setValgrpSctId(valGrpSctIdArray[i]);
            mapKcdSctAftCatDao.attValInsert(mapKcdSctAftCatVo);
        }
    }

    public List<MapKcdSctAftCatVo> getList(MapKcdSctAftCatVo mapKcdSctAftCatVo) {
        return mapKcdSctAftCatDao.getList(mapKcdSctAftCatVo);
    }

    public void attValUpdate(MapKcdSctAftCatVo mapKcdSctAftCatVo, String attrParam, String valParam, String valGrpSctIdParam) {
        String[] attrArray = attrParam.split(",");
        String[] valArray = valParam.split(",");
        String[] valGrpSctIdArray = valGrpSctIdParam.split(",");

        if(attrArray.length > 0){
            mapKcdSctAftCatDao.attValDelete(mapKcdSctAftCatVo);
        }

        for(int i = 0; i<attrArray.length; i++){
            mapKcdSctAftCatVo.setAttSctId(attrArray[i]);
            mapKcdSctAftCatVo.setValSctId(valArray[i]);
            mapKcdSctAftCatVo.setValgrpSctId(valGrpSctIdArray[i]);
            mapKcdSctAftCatDao.attValInsert(mapKcdSctAftCatVo);
        }
    }

    public void deleteAttrVal(MapKcdSctAftCatVo mapKcdSctAftCatVo) {
        mapKcdSctAftCatDao.attValDelete(mapKcdSctAftCatVo);
    }
}
