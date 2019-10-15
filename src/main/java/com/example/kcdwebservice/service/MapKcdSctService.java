package com.example.kcdwebservice.service;

import com.example.kcdwebservice.dao.MapKcdSctDao;
import com.example.kcdwebservice.vo.MapKcdSctVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MapKcdSctService {

    @Autowired
    MapKcdSctDao mapKcdSctDao;

    public List<MapKcdSctVo> selectKcdCdList(String kcdCd) {
        return mapKcdSctDao.selectKcdCdList(kcdCd);
    }
}
