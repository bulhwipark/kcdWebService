package com.example.kcdwebservice.service;

import com.example.kcdwebservice.dao.TempKcd7Dao;
import com.example.kcdwebservice.dao.TempmapicdsctDao;
import com.example.kcdwebservice.vo.TempKcd7Vo;
import com.example.kcdwebservice.vo.TempmapicdsctVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TempmapicdsctService {

    @Autowired
    TempmapicdsctDao tempmapicdsctDao;

    public List<TempmapicdsctVo> select() {
        return tempmapicdsctDao.select();
    }
}
