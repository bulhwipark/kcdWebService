package com.example.kcdwebservice.service;

import com.example.kcdwebservice.dao.MCodeDao;
import com.example.kcdwebservice.vo.MCodeVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MCodeService {

    @Autowired
    private MCodeDao mCodeDao;

    public List<MCodeVo> getMappingTypeList() {
        return mCodeDao.getMappingTypeList();
    }
}
