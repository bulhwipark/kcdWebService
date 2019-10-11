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

    public List<CmKcdVo> select() {
        return cmKcdDao.select();
    }
}
