package com.example.kcdwebservice.service;

import com.example.kcdwebservice.dao.TempKcd7Dao;
import com.example.kcdwebservice.vo.TempKcd7Vo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TempKcd7Service {

    @Autowired
    TempKcd7Dao tempKcd7Dao;

    public List<TempKcd7Vo> select() {
        return tempKcd7Dao.select();
    }
}
