package com.example.kcdwebservice.service;

import com.example.kcdwebservice.dao.DicSnomedctAttValDao;
import com.example.kcdwebservice.vo.DicSnomedctAttValVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DicSnomedctAttValService {
    @Autowired
    private DicSnomedctAttValDao dicSnomedctAttValDao;


    public List<DicSnomedctAttValVo> getAttrList(String oriTpCd) {
        return dicSnomedctAttValDao.getAttrList(oriTpCd);
    }

    public List<DicSnomedctAttValVo> getValList(DicSnomedctAttValVo av) {
        return dicSnomedctAttValDao.getValList(av);
    }
}
