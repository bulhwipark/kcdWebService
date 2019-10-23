package com.example.kcdwebservice.service;

import com.example.kcdwebservice.dao.MapKcdSctAftCatDao;
import com.example.kcdwebservice.vo.MapKcdSctAftCatVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MapKcdSctAftCatService {
    @Autowired
    private MapKcdSctAftCatDao mapKcdSctAftCatDao;

    public void attValInsert(MapKcdSctAftCatVo mapKcdSctAftCatVo) {
        mapKcdSctAftCatDao.attValInsert(mapKcdSctAftCatVo);
    }
}
