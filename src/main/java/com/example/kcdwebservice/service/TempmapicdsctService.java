package com.example.kcdwebservice.service;

import java.util.List;

import com.example.kcdwebservice.dao.TempmapicdsctDao;
import com.example.kcdwebservice.vo.TempmapicdsctVo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TempmapicdsctService {

    @Autowired
    TempmapicdsctDao tempmapicdsctDao;

    public List<TempmapicdsctVo> select() {
        return tempmapicdsctDao.select();
    }
}
